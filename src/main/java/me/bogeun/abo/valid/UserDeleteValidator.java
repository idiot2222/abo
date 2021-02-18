package me.bogeun.abo.valid;

import me.bogeun.abo.domain.dto.UserDeleteForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDeleteValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserDeleteValidator.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDeleteForm userDeleteForm = (UserDeleteForm) target;

        if(!userDeleteForm.getPassword().equals(userDeleteForm.getPasswordRepeat())) {
            errors.rejectValue("passwordRepeat", "invalid.passwordRepeat", "비밀번호와 다릅니다.");
        }
    }
}
