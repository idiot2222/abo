package me.bogeun.abo.domain.dto;

import lombok.Getter;
import lombok.Setter;
import me.bogeun.abo.domain.PlayHand;
import me.bogeun.abo.domain.Position;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class PlayerCreateForm {

    @Min(100)
    @Max(250)
    private int height;

    @Min(30)
    @Max(200)
    private int weight;

    private PlayHand playHand;

    private Position position;
}
