package me.bogeun.abo.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    private int weight;
    private int height;

    @Enumerated(EnumType.STRING)
    private PlayHand playHand;

    @OneToOne(mappedBy = "player")
    private User user;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private List<Positions> positions = new ArrayList<>();




    @Builder
    public Player(int weight, int height, PlayHand playHand) {
        this.weight = weight;
        this.height = height;
        this.playHand = playHand;
    }

    public void addPosition(Positions position) {
        positions.add(position);
    }

    public void enrollUser(User user) {
        this.user = user;
    }
}
