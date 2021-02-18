package me.bogeun.abo.valid;

import lombok.RequiredArgsConstructor;
import me.bogeun.abo.domain.dto.UserUpdateForm;
import me.bogeun.abo.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class UserUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserUpdateValidator.class);
    }

    private final UserRepository userRepository;

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateForm updateForm = (UserUpdateForm) target;

        if(!updateForm.getPassword().equals(updateForm.getPasswordRepeat())){
            errors.rejectValue("passwordRepeat", "invalid.passwordRepeat", "비밀번호와 다릅니다.");
        }

        int length = updateForm.getPassword().length();
        if(length > 0 && length < 8 || length > 20) {
            errors.rejectValue("password", "invalid.password", "패스워드는 3~20자이어야 합니다.");
        }

        if(userRepository.existsByEmail(updateForm.getEmail())) {
            errors.rejectValue("email" , "duplicated.email", "이미 사용중인 이메일입니다.");
        }
    }
}
