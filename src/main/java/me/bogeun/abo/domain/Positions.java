package me.bogeun.abo.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Positions {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne
    private Player player;

    @Builder
    public Positions(Position position, Player player) {
        this.position = position;
        this.player = player;
    }
}
