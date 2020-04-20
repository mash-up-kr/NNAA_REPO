package com.na.backend.service;

import com.na.backend.dto.*;
import com.na.backend.entity.Question;
import com.na.backend.entity.User;
import com.na.backend.exception.AlreadyExistsException;
import com.na.backend.exception.EntityNotFoundException;
import com.na.backend.exception.MismatchException;
import com.na.backend.exception.UnauthorizedException;
import com.na.backend.mapper.UserMapper;
import com.na.backend.repository.QuestionRepository;
import com.na.backend.repository.UserRepository;
import com.na.backend.util.EncryptManager;
import com.na.backend.util.MailManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final QuestionRepository questionRepository;
    private final JavaMailSender mailSender;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       QuestionRepository questionRepository,
                       JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.questionRepository = questionRepository;
        this.mailSender = javaMailSender;
    }

    public Boolean isUser(String id, String token) {

        User user = userRepository.findById(id).orElseThrow(() -> new UnauthorizedException("invalid id"));
        return token.equals(user.getToken());
    }

    @Transactional
    public UserAuthDto addUserByEmail(SignUpDto signUpDto) {
        String userEmail = signUpDto.getEmail();
        String userPassword = signUpDto.getPassword();
        String userName = signUpDto.getName();

        String salt = EncryptManager.generateSalt();
        String encryptPassword = EncryptManager.encryptPlainString(userPassword, salt);
        String token = EncryptManager.createToken(userEmail, LocalDateTime.now(), salt);

        User newUser = User.builder()
                .email(userEmail)
                .name(userName)
                .password(encryptPassword)
                .salt(salt)
                .bookmarks(new HashMap<>())
                .token(token)
                .build();

        return userMapper.toUserAuthDto(userRepository.insert(newUser));
    }

    @Transactional
    public UserAuthDto getUserByEmail(LogInDto loginDto) {
        String userEmail = loginDto.getEmail();
        String userPassword = loginDto.getPassword();

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UnauthorizedException("invalid email"));

        String userSalt = user.getSalt();
        String encryptPassword = EncryptManager.encryptPlainString(userPassword, userSalt);

        if (encryptPassword.equals(user.getPassword())) {
            return userMapper.toUserAuthDto(user);
        } else {
            throw new UnauthorizedException("invalid password");
        }
    }

    @Transactional
    public User getUserBySocialService(String uid) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new UnauthorizedException("invalid uid"));

        if (isSocialUser(uid)) {
            return user;
        } else {
            String salt = EncryptManager.generateSalt();
            String token = EncryptManager.createToken(uid, LocalDateTime.now(), salt);

            User newUser = User.builder()
                    .uid(uid)
                    .name("")
                    .salt(salt)
                    .bookmarks(new HashMap<>())
                    .token(token).build();

            return userRepository.insert(newUser);
        }
    }

    // TODO: 카테고리 목록 보여주는 api ( 카테고리와 관계에 대한 명칭을 정확히 한 후에 )
//    public List<CategoryRelationshipDto> getRelationshipList() {
//        return userMapper.toCategoryRelationshipDto();
//    }

    public List<UserInfoDto> findUsers(String name) {
        List<User> users = userRepository.findUsersByName(name);

        if (users.size() == 0) {
            throw new EntityNotFoundException("no user for name(" + name + ")");
        }

        return userMapper.toUserInfoDtos(users);
    }

    @Transactional
    public User addFriendById(String myId, String id) {

        User user = userRepository.findById(myId).orElseThrow(() -> new UnauthorizedException("invalid id"));
        List<String> userFriends = user.getFriends();

        if (!userFriends.contains(id)) {
            userFriends.add(id);
            return userRepository.save(user);
        } else {
            throw new AlreadyExistsException("Already added friend");
        }
    }

    @Transactional
    public void sendResetPasswordEmail(String email) throws MessagingException, UnsupportedEncodingException {
        // TODO: 유효기한있는 이메일 설정
        //String timeLimit = LocalDateTime.now().toString(); // 유효시간 몇분으로 설정할지..?
        //String resetLink = "" + userId + timeLimit;

        MailManager.sendResetMail(email, userRepository.findByEmail(email).get());
    }

    public boolean isInvalidNamePattern(String name) {
        if (name == null) return true;

        String regexPattern = "^[가-힣a-zA-Z]+$";
        return !Pattern.matches(regexPattern, name.trim());
    }

    public boolean isInvalidEmailPattern(String email) {
        if (email == null) return true;

        String regexPattern = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
        return !Pattern.matches(regexPattern, email.trim());
    }

    public boolean isEmailUser(String email) {

        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isSocialUser(String uid) {

        return userRepository.findByUid(uid).isPresent();
    }

    public List<Question> getUserBookmark(String myId) {
        User user = userRepository.findById(myId).orElseThrow(() -> new EntityNotFoundException("invalid id"));
        Map<String, BookmarkQuestionDto> userBookmarks = user.getBookmarks();

        if (userBookmarks.size() == 0) {
            throw new EntityNotFoundException("There's nothing on your list of bookmark!");
        }

        return userMapper.toQuestions(userBookmarks);
    }

    public String addBookmark(String myId, BookmarkQuestionDto bookmarkQuestionDto) {
        User user = userRepository.findById(myId).orElseThrow(() -> new UnauthorizedException("invalid id"));

        Map<String, BookmarkQuestionDto> userBookmarks = user.getBookmarks();

        // TODO: 질문, 보기 앞뒤 공백제거
        StringBuilder questionString = new StringBuilder(bookmarkQuestionDto.getContent());
        if(bookmarkQuestionDto.getChoices() != null) {
            for(String choiceString : bookmarkQuestionDto.getChoices().values()) {
                questionString.append(choiceString);
            }
        }

        // 즐겨찾기등록 시 생성되는 id = 질문내용과 보기들을 이어붙인 문자열의 해시값
        String nextId = String.valueOf(questionString.toString().hashCode());

        userBookmarks.put(nextId,bookmarkQuestionDto);
        user.setBookmarks(userBookmarks);
        userRepository.save(user);
        return nextId;
    }

    public User dropBookmark(String myId, String bookmarkQuestionId) {
        User user = userRepository.findById(myId).orElseThrow(() -> new UnauthorizedException("invalid id"));

        Map<String, BookmarkQuestionDto> userBookmarks = user.getBookmarks();

        if (userBookmarks.containsKey(bookmarkQuestionId)) {
            userBookmarks.remove(bookmarkQuestionId);
            user.setBookmarks(userBookmarks);
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException("the question(id:" + bookmarkQuestionId + ") is not on your list of bookmark!");
        }
    }

    public void resetPasswordForLoginUser(String myId, String newPassword) {

        User user = userRepository.findById(myId).orElseThrow(() -> new UnauthorizedException("invalid id"));

        String password = user.getPassword();

        if(!password.equals(newPassword)){
                user.setPassword(newPassword);
                userRepository.save(user);
        }
        else{
            throw new AlreadyExistsException("This new password is the same as the existing password");
        }

    }

    public boolean isMatchedUserPassword(String myId, String currentPassword) {

        User user = userRepository.findById(myId).orElseThrow(() -> new UnauthorizedException("invalid id"));

        String password =user.getPassword();

        if(!password.equals(currentPassword)){
            throw new MismatchException("Current password does not match existing password");
        }

        return true;
    }
}
