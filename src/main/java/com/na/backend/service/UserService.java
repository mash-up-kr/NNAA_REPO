package com.na.backend.service;

import com.na.backend.dto.EmailDto;
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

    public void addFriendById(String myId, String id) {

        //List<String> myFriends = userRepository.findById(myId);
        //userRepository.update(myFriends.add(id));
    }

    private boolean isEmailUser(String email) {

        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isSocialUser(String uid) {

        return userRepository.findByUid(uid).isPresent();
    }

}
