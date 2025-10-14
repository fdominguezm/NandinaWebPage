package com.nandina.api.config.auth;


import com.nandina.api.models.User;
import com.nandina.api.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
//
//@Component
//@RequiredArgsConstructor
//public class AuthUserDetailsService implements UserDetailsService {
//
//    private final UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userService.getUserByEmail(email)
//                .orElseThrow(() ->  new UsernameNotFoundException(email));
//        return new AuthUser(email, user.getPassword(), getAuthorities(user));
//    }
//
//    public static Collection<GrantedAuthority> getAuthorities (User user) {
////        Collection<GrantedAuthority> authorities = new HashSet<>();
////        return authorities;
//        return new HashSet<GrantedAuthority>();
//    }
//}

