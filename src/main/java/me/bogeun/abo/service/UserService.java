package me.bogeun.abo.service;

import lombok.RequiredArgsConstructor;
import me.bogeun.abo.domain.User;
import me.bogeun.abo.domain.UserRole;
import me.bogeun.abo.domain.dto.UserJoinForm;
import me.bogeun.abo.domain.dto.UserUpdateForm;
import me.bogeun.abo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User joinNewUser(UserJoinForm userJoinForm) {
        User user = User.builder()
                .nickname(userJoinForm.getNickname())
                .password(passwordEncoder.encode(userJoinForm.getPassword()))
                .name(userJoinForm.getName())
                .email(userJoinForm.getEmail())
                .joinedAt(LocalDateTime.now())
                .userRole(UserRole.USER)
                .build();

        return userRepository.save(user);
    }

    public User updateUser(User foundUser, UserUpdateForm updateForm) {
        if(!updateForm.getPassword().equals("")) {
            updateForm.setPassword(passwordEncoder.encode(updateForm.getPassword()));
        }

        foundUser.update(updateForm);
        return foundUser;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
