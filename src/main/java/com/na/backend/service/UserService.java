package com.na.backend.service;

import com.na.backend.dto.EmailDto;
import com.na.backend.dto.ProfileDto;
import com.na.backend.entity.User;
import com.na.backend.exception.UnauthorizedException;
import com.na.backend.mapper.UserMapper;
import com.na.backend.repository.UserRepository;
import com.na.backend.util.EncryptManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
    public User getUserByEmail(EmailDto emailDto) {
        String userEmail = emailDto.getEmail();
        String userPassword = emailDto.getPassword();

        if ( isEmailUser(userEmail) ) {
            User user = userRepository.findByEmail(userEmail).get();
            String userSalt = user.getSalt();
            String encryptPassword = EncryptManager.encryptPlainString(userPassword, userSalt);

            if (encryptPassword.equals(user.getPassword())) {
                return user;
            } else {
                throw new UnauthorizedException("invalid password");
            }
        } else {
            String salt = EncryptManager.generateSalt();
            String encryptPassword = EncryptManager.encryptPlainString(userPassword, salt);
            String token = EncryptManager.createToken(userEmail, LocalDateTime.now(), salt);

            User newUser = User.builder()
                               .email(userEmail)
                               .password(encryptPassword)
                               .salt(salt)
                               .token(token).build();

            return userRepository.insert(newUser);
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
                               .salt(salt)
                               .token(token).build();

            return userRepository.insert(newUser);
        }
    }

    public String getIdByNickname(String nickname) {
        Optional<User> user= userRepository.findByNickname(nickname);

        if ( user.isPresent() ) {
            return user.get().getId();
        } else {
            throw new RuntimeException();
        }
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

    @Transactional
    public User updateNickname(String myId, ProfileDto profileDto) {
        Optional<User> user= userRepository.findById(myId);
        String newNickname;

        if ( user.isPresent() ) {
            newNickname = profileDto.getNickname();
            //이미 사용되는 닉네임일 경우
            if(getIdByNickname(newNickname)!= null){
                throw new RuntimeException();
            }
            else {
                user.get().setNickname(newNickname);
                return userRepository.save(user.get());
            }

        } else {
            throw new RuntimeException();
        }

    }

    private boolean isEmailUser(String email) {

        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isSocialUser(String uid) {

        return userRepository.findByUid(uid).isPresent();
    }

    public List<String> getUserBookmark(String myId) {
        Optional<User> user= userRepository.findById(myId);
        List<String> userBookmark;

        if ( user.isPresent() ) {
            userBookmark = user.get().getBookmarks();
            return userBookmark;
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
