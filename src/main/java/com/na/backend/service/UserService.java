package com.na.backend.service;

import com.na.backend.dto.LogInDto;
import com.na.backend.dto.SignUpDto;
import com.na.backend.dto.UserAuthDto;
import com.na.backend.dto.UserInfoDto;
import com.na.backend.entity.Question;
import com.na.backend.entity.User;
import com.na.backend.exception.EntityNotFoundException;
import com.na.backend.exception.UnauthorizedException;
import com.na.backend.mapper.UserMapper;
import com.na.backend.repository.QuestionRepository;
import com.na.backend.repository.UserRepository;
import com.na.backend.util.EncryptManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final QuestionRepository questionRepository;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.questionRepository = questionRepository;
    }

    public Boolean isUser(String id, String token) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            return token.equals(user.get().getToken());
        } else {
            throw new RuntimeException("invalid id");
        }
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

        User user = userRepository.findByEmail(userEmail).get();
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
        Optional<User> user = userRepository.findByUid(uid);

        if ( isSocialUser(uid) ) {

            return userRepository.findByUid(uid).get();
        } else {
            String salt = EncryptManager.generateSalt();
            String token = EncryptManager.createToken(uid, LocalDateTime.now(), salt);

            User newUser = User.builder()
                               .uid(uid)
                               .name("")
                               .salt(salt)
                               .bookmarks(new ArrayList<String>())
                               .token(token).build();

            return userRepository.insert(newUser);
        }
    }

    public List<UserInfoDto> findUsers(String name) {
        List<User> users = userRepository.findUsersByName(name);

        if (users.size() == 0) {
            throw new EntityNotFoundException("no user for name("+name+")");
        }

        return userMapper.toUserInfoDtos(users);
    }

    @Transactional
    public User addFriendById(String myId, String id) {
        Optional<User> user= userRepository.findById(myId);
        List<String> userFriends;

        if ( user.isPresent() ) {
            userFriends = user.get().getFriends();

            if(!userFriends.contains(id)) {
                userFriends.add(id);
                return userRepository.save(user.get());
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }

    }

    public boolean isInvalidNamePattern(String name) {
        if (name == null) return true;

        String regexPattern = "^[가-힣a-zA-Z]+$";
        if ( Pattern.matches(regexPattern, name.trim()) ) {
            return false;
        }

        return true;
    }

    public boolean isInvalidEmailPattern(String email) {
        if (email == null) return true;

        String regexPattern = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
        if ( Pattern.matches(regexPattern, email.trim()) ) {
            return false;
        }

        return true;
    }

    public boolean isEmailUser(String email) {

        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isSocialUser(String uid) {

        return userRepository.findByUid(uid).isPresent();
    }

    public List<String> getUserBookmark(String myId) {
        Optional<User> user= userRepository.findById(myId);
        List<String> userBookmarkIds;
        List<String> userBookmarkContents = null;
        if ( user.isPresent() ) {

            userBookmarkIds = user.get().getBookmarks();

            if(userBookmarkIds != null){
                for(int i=0;i<userBookmarkIds.size();i++){
                    String questionId = userBookmarkIds.get(i);
                    Optional<Question> userBookmarkQuestion = questionRepository.findById(questionId);

                    userBookmarkContents.add(userBookmarkQuestion.get().getContent());
                }
                return userBookmarkContents;
            }
            else{
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public User addBookmark(String myId, String questionId) {
        Optional<User> user= userRepository.findById(myId);
        List<String> userBookmark;

        if ( user.isPresent() ) {
            userBookmark = user.get().getBookmarks();

            if(!userBookmark.contains(questionId)) {
                userBookmark.add(questionId);
                return userRepository.save(user.get());
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public User dropBookmark(String myId, String questionId) {
        Optional<User> user= userRepository.findById(myId);
        List<String> userBookmark;

        if ( user.isPresent() ) {
            userBookmark = user.get().getBookmarks();

            if(userBookmark.contains(questionId)){
                userBookmark.remove(questionId);
                return userRepository.save(user.get());

            } else {
                throw new RuntimeException();
            }

        } else {
            throw new RuntimeException();
        }
    }
}
