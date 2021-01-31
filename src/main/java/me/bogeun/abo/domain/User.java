package me.bogeun.abo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
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
    public User(String nickname, String password, String name, String email, LocalDateTime joinedAt, UserRole userRole) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt;
        this.userRole = userRole;
    }
}
