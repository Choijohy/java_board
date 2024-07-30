package com.example.java_board.domain.user.service;

import com.example.java_board.domain.user.entity.User;
import com.example.java_board.domain.user.repository.UserRepository;
import com.example.java_board.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    // 회원가입
//    public ApiResponse<User> signUp(String email, String password) {
//        User user = new User();
//        user.setEmail(email);
//        // 추후 암호화 필요
//        user.setPassword(password);
//        userRepository.save(user);
//
//        return new ApiResponse<>(HttpStatus.CREATED,"Sign up successfully", user);
//    }


}
