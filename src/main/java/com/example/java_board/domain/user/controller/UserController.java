package com.example.java_board.domain.user.controller;


import com.example.java_board.domain.user.service.UserService;
import com.example.java_board.domain.user.entity.User;
import com.example.java_board.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    //     회원가입
//     @Valid: Jakarta Bean Validation API를 사용하여 필드의 유효성을 검사
//     BindingResult: 윺효성 검사 결과를 저장. 오류 발생시 해당 객체에 저장
//    @PostMapping("/sign-up")
//    public ResponseEntity<ApiResponse<User>> signUp(
//            @Valid SignUpRequest signUpForm,
//            BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            ApiResponse<User> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, "Form data is invalid", null);
//            return new ResponseEntity<>(response, response.getStatus());
//        }
//        ApiResponse<User> response = userService.signUp(signUpForm.getEmail(), signUpForm.getPassword());
//        return new ResponseEntity<>(response, response.getStatus());
//    }
}
