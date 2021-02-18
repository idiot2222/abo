package me.bogeun.abo.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteForm {

    private String password;

    private String passwordRepeat;
}
