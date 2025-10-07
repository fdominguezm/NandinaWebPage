package com.nandina.api.forms;


import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Setter
@Getter
//@FieldsMatch(field = "password", secondField = "repeatPassword")
public class UserRegisterForm {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
//    @UniqueEmail
    public String email;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    public String name;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    public String password;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    public String repeatPassword;
}


