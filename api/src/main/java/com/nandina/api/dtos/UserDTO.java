package com.nandina.api.dtos;


import com.nandina.api.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private byte[] profilePicture;
    private String provider;

    public static UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setProfilePicture(user.getProfilePicture());

        return userDTO;
    }
}

