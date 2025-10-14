package com.nandina.api.controllers;

import com.nandina.api.config.auth.JwtService;
import com.nandina.api.dtos.UserDTO;
import com.nandina.api.exceptions.BadRequest;
import com.nandina.api.exceptions.specifics.UserNotFoundException;
import com.nandina.api.forms.UserLoginForm;
import com.nandina.api.forms.UserRegisterForm;
import com.nandina.api.models.User;
import com.nandina.api.services.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @ModelAttribute @Valid UserRegisterForm registerForm
//            ,
//            @RequestParam(value = "image") MultipartFile image
    ) throws IOException {

//        if (image.isEmpty()) {
//            throw new BadRequest("Image is empty");
//        }

        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        user.setName(registerForm.getName());
//        user.setProfilePicture(image.getBytes());
        userService.createUser(user);

        return ResponseEntity.ok(jwtService.generateToken(registerForm.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginForm loginForm, HttpServletRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword())
        );

        return ResponseEntity.ok(jwtService.generateToken(loginForm.getEmail()));
    }



}
