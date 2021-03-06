package me.bogeun.abo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class UserJoinForm {

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
    private String nickname;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

    @NotBlank
    private String passwordRepeat;

    @NotBlank
    @Length(min = 2, max = 10)
    @Pattern(regexp = "^[가-힣]{2,10}$")
    private String name;

    @NotBlank
    @Email
    private String email;

    @Builder
    public UserJoinForm(@NotBlank @Length(min = 3, max = 20) @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$") String nickname, @NotBlank @Length(min = 8, max = 20) String password, @NotBlank String passwordRepeat, @NotBlank @Length(min = 2, max = 10) @Pattern(regexp = "^[가-힣]{2,10}$") String name, @NotBlank @Email String email) {
        this.nickname = nickname;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.name = name;
        this.email = email;
    }
}
