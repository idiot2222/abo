package me.bogeun.abo.service;

import lombok.RequiredArgsConstructor;
import me.bogeun.abo.domain.CurrentUser;
import me.bogeun.abo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        me.bogeun.abo.domain.User user = userRepository.findByNickname(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CurrentUser(user);
    }
}
