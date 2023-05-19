package com.java.everyboard.user.service;

import com.java.everyboard.constant.LoginType;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // TODO: [전체] JWT 검증로직 추가

    // 회원가입
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // 회원정보 수정
    public User updateUser(User user) {
        User findUSer = existUser(user.getUserId());

        // 소셜 회원일 경우 수정 불가
        isSocialUser(findUSer);

        // 새로 변경할 이메일 존재 유무 확인
        Optional.ofNullable(user.getEmail())
                .ifPresent(newEmail -> {
                    verifiedUser(findUSer.getEmail());
                    findUSer.setEmail(newEmail);
                });

        Optional.ofNullable(user.getPassword())
                .ifPresent(findUSer::setPassword);

        Optional.ofNullable(user.getNickname())
                .ifPresent(findUSer::setNickname);

        Optional.ofNullable(user.getProfileUrl())
                .ifPresent(findUSer::setProfileUrl);

        return userRepository.save(findUSer);
    }

    // 회원정보 조회
    public User getUser(Long userId) {
        return existUser(userId);
    }

    // 회원탈퇴
    public void deleteUser(Long userId) {
        User findUSer = existUser(userId);
        userRepository.delete(findUSer);
    }

    // 회원 존재 유무
    private User existUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 이메일 중복검사 (이메일 정보 수정 시 사용)
    public void verifiedUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            // TODO: EMAIL_EXISTS ExceptionCode 변경
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    // 소셜 회원 확인
    public void isSocialUser(User user) {
        if (user.getLoginType().equals(LoginType.SOCIAL)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_FORBIDDEN);
        }
    }
}
