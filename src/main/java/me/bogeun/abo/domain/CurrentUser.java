package me.bogeun.abo.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getNickname(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }
}
