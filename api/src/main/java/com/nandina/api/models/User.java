package com.nandina.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "email", nullable = false)
    private String email;

    @Column (name = "password")
    private String password;

    @Column(name = "profile_picture")
    private byte[] profilePicture;  // Storing image as BLOB

}

