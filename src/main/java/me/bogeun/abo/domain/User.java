package me.bogeun.abo.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Length(min = 3, max = 20)
    private String nickname;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

    @NotBlank
    @Email
    private String email;

    private LocalDateTime joinedAt;

    private LocalDateTime birthDate;

    private boolean agreeReceiveEmail;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String image;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
