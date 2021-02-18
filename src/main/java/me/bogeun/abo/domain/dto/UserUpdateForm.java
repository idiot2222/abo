package me.bogeun.abo.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UserUpdateForm {

    private String password;

    private String passwordRepeat;

    @Email
    private String email;

    private boolean agreeReceiveEmail;
}
