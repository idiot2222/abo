package me.bogeun.abo.domain;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public CurrentUser(User user) {
        super(user.getNickname(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().toString())));
        this.user = user;
    }
}
