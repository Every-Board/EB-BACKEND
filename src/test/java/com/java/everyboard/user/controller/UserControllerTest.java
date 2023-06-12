//package com.java.everyboard.user.controller;
//
//import com.java.everyboard.constant.ActiveStatus;
//import com.java.everyboard.constant.LoginType;
//import com.java.everyboard.user.entity.User;
//import com.java.everyboard.user.repository.UserRepository;
//import com.java.everyboard.user.service.UserService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//
//@Transactional
//@SpringBootTest
//class UserControllerTest {
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    void 회원가입() {
//        // given
//        User user = new User();
//        user.setUserId(1L);
//        user.setEmail("chaoo40@gmail.com");
//        user.setNickname("chaoo");
//        user.setPassword("abc123!@");
//        user.setLoginType(LoginType.BASIC);
//        user.setActiveStatus(ActiveStatus.ACTIVE);
//
//        userRepository.save(user);
//
//        // when
//        String findUser = userRepository.findById(user.getUserId()).get().getNickname();
//
//        // then
//        Assertions.assertEquals(user.getNickname(), findUser);
//
//    }
//
//
//
//}