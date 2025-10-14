package com.nandina.api.controllers;


import com.nandina.api.dtos.UserDTO;
import com.nandina.api.exceptions.BadRequest;
import com.nandina.api.exceptions.specifics.UserNotFoundException;
import com.nandina.api.forms.PageForm;
import com.nandina.api.forms.UserEditForm;
import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;
import com.nandina.api.services.interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @PutMapping("/me")
    public ResponseEntity<?> editUser(@RequestBody @Valid UserEditForm editForm) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (editForm.getPassword() != null) {
            if (!passwordEncoder.matches(editForm.password, user.getPassword())){
                throw new BadRequest("Incorrect password");
            }
        }
        String encodedPassword = editForm.getNewPassword() != null
                ? passwordEncoder.encode(editForm.getNewPassword())
                : user.getPassword();

        user = userService.editUser(user, editForm.getName(), encodedPassword);

        Collection<GrantedAuthority> authorities = new HashSet<>();
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authToken);

        return ResponseEntity.ok(UserDTO.fromUser(user));

    }


    @PostMapping("/picture")
    public ResponseEntity<?> uploadUserPicture(@RequestParam("image") MultipartFile image) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (image.isEmpty()) {
            throw new BadRequest("Image is empty");
        }
        user = userService.saveProfilePicture(user, image.getBytes());
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }


    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable @Min(1) Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByEmail(@RequestParam(name = "email") String email) {
        User user = userService.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchUsersByName(@RequestParam(name = "name") String name,
                                               @Valid @ModelAttribute PageForm pageForm) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PagedContent<User> users = userService.searchUsersByName(user, name, pageForm.getPageOrDefault(), pageForm.getSizeOrDefault());
        List<UserDTO> userDTOs = users.getContent().stream().map(UserDTO::fromUser).toList();
        PagedContent<UserDTO> toReturn = new PagedContent<>(userDTOs, users.getCurrentPage(), users.getPageSize(), users.getTotalCount());
        return ResponseEntity.ok(toReturn);
    }


}

