package me.bogeun.abo.valid;

import lombok.RequiredArgsConstructor;
import me.bogeun.abo.domain.dto.UserJoinForm;
import me.bogeun.abo.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class UserJoinValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserJoinForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserJoinForm joinForm = (UserJoinForm) target;

        if (userRepository.existsByNickname(joinForm.getNickname())) {
            errors.rejectValue("nickname", "duplicate.nickname", "이미 사용중인 닉네임입니다.");
        }
        if (userRepository.existsByEmail(joinForm.getEmail())) {
            errors.rejectValue("email", "duplicate.email", "이미 가입된 이메일입니다.");
        }
        if (!joinForm.getPassword().equals(joinForm.getPasswordRepeat())) {
            errors.rejectValue("password", "invalid.password", "비밀번호 확인이 틀렸습니다.");
        }
    }
}
