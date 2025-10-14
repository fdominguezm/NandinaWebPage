package com.nandina.api.forms;

import com.nandina.api.forms.validations.FieldsMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@FieldsMatch(field = "newPassword", secondField = "repeatNewPassword")
public class UserEditForm {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    public String name;

    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    public String password;

    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    public String newPassword;

    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    public String repeatNewPassword;
}

