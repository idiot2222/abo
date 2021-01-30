package me.bogeun.abo.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String nickname;

    private String password;

    private String name;

    @Column(unique = true)
    private String email;

    @CreationTimestamp
    private LocalDateTime joinedAt;

    private boolean agreeReceiveEmail;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String image;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public User(String nickname, String password, String name, String email, LocalDateTime joinedAt, boolean agreeReceiveEmail, String image, UserRole userRole) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt;
        this.agreeReceiveEmail = agreeReceiveEmail;
        this.image = image;
        this.userRole = userRole;
    }
}
