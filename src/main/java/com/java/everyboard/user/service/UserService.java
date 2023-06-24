package com.java.everyboard.user.service;

import com.java.everyboard.awsS3.AwsS3Service;
import com.java.everyboard.constant.LoginType;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.security.utils.CustomAuthorityUtils;
import com.java.everyboard.user.entity.User;
import com.java.everyboard.user.entity.UserImage;
import com.java.everyboard.user.repository.UserImageRepository;
import com.java.everyboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserImageRepository userImageRepository;
    private final AwsS3Service awsS3Service;
    // 회원가입
    public User createUser(User user) {

        verifiedUser(user.getEmail());
        List<String> roles = authorityUtils.createRoles(user.getEmail());
        user.setRoles(roles);

        String encryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptPassword);

        userRepository.save(user);

        return user;
    }

    // 프로필 업로드
    public User uploadProfile(User user, List<String> profileImgPath) {
        checkJwtAndUser(user.getUserId());
        User findUSer = existUser(user.getUserId());

        // 소셜 회원일 경우 수정 불가
        isSocialUser(findUSer);

         List<String> UserFileNameList = new ArrayList<>();
        for (String userProfileUrl : profileImgPath) {
            UserImage img = new UserImage(user.getUserId(),userProfileUrl);
            img.setUserId(user.getUserId());
            userImageRepository.save(img);
            UserFileNameList.add(img.getUserImgUrl());
        }

        Optional.ofNullable(user.getProfileUrl())
                .ifPresent(findUSer::setProfileUrl);

        return userRepository.save(findUSer);
    }

    // 회원정보 수정
    public User updateUser(User user) {
        checkJwtAndUser(user.getUserId());
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
    public User findUser(Long userId) {
        return existUser(userId);
    }

    // 회원탈퇴
    public void deleteUser(Long userId) {
        checkJwtAndUser(userId);
        User findUSer = existUser(userId);
        userRepository.delete(findUSer);

        List<UserImage> userImages = userImageRepository.findByUserId(userId);
        for (UserImage userImage: userImages) {
            awsS3Service.deleteFile(userImage.getUserImgUrl());
        }
        userImageRepository.deleteAllByUserId(userId);
    }

    // 프로필 삭제
    public void deleteProfile(Long userId) {
        checkJwtAndUser(userId);
        User findUSer = existUser(userId);

        List<UserImage> userImages = userImageRepository.findByUserId(userId);
        for (UserImage userImage: userImages) {
            awsS3Service.deleteFile(userImage.getUserImgUrl());
        }
        userImageRepository.deleteAllByUserId(userId);
    }

    // 회원 존재 유무
    private User existUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 이메일 중복검사 (최초가입 / 이메일 정보 수정 시 사용)
    public void verifiedUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.EMAIL_EXISTS);
        }
    }

    public User getLoginUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 비밀글 설정 시 로그인 유저 확인
    public String getLoginUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // 본인만 접근 허용
    public void checkJwtAndUser(Long userId) {
        if (!getLoginUser().getUserId().equals(userId)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_FORBIDDEN);
        }
    }

    // 소셜 회원 확인
    public void isSocialUser(User user) {
        if (user.getLoginType().equals(LoginType.SOCIAL)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_FORBIDDEN);
        }
    }
}
