package com.na.backend.service;

import com.na.backend.dto.LogInDto;
import com.na.backend.dto.SignUpDto;
import com.na.backend.dto.UserAuthDto;
import com.na.backend.dto.UserInfoDto;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
                .bookmarks(new ArrayList<>())
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
                    .bookmarks(new ArrayList<>())
                    .token(token).build();

            return userRepository.insert(newUser);
        }
    }

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
    public void sendResetPasswordEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        //String timeLimit = LocalDateTime.now().toString(); // 유효시간 몇분으로 설정할지..?
        //String resetLink = "" + userId + timeLimit;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("비밀번호 변경 확인 메일");
        mailMessage.setText(user.getId() + " 님 비밀번호를 변경하시겠습니까?");

        mailSender.send(mailMessage);
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

        List<String> userBookmarkIds = user.getBookmarks();

        if (userBookmarkIds.size() == 0) {
            throw new EntityNotFoundException("There's nothing on your list of bookmark!");
        }

        return questionRepository.findQuestionsByIdIn(userBookmarkIds);
    }

    public User addBookmark(String myId, String questionId) {

        User user = userRepository.findById(myId).orElseThrow(() -> new UnauthorizedException("invalid id"));

        List<String> userBookmark = user.getBookmarks();

        if (userBookmark.contains(questionId)) {
            throw new AlreadyExistsException("the question(id:" + questionId + ") is already on your list of bookmark!");
        } else {
            userBookmark.add(questionId);
            return userRepository.save(user);
        }
    }

    public User dropBookmark(String myId, String questionId) {

        User user = userRepository.findById(myId).orElseThrow(() -> new UnauthorizedException("invalid id"));

        List<String> userBookmark = user.getBookmarks();

        if (userBookmark.contains(questionId)) {
            userBookmark.remove(questionId);
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException("the question(id:" + questionId + ") is not on your list of bookmark!");
        }
    }

    public void resetPasswordForLoginUser(String myId, String newPassword, String newPasswordAgain) {

        User user = userRepository.findById(myId).orElseThrow(() -> new UnauthorizedException("invalid id"));

        String password =user.getPassword();

        if(!password.equals(newPassword)){
                user.setPassword(newPassword);
                userRepository.save(user);
        }
        else{
            throw new AlreadyExistsException("This new password is the same as the existing password");
        }

    }
}
