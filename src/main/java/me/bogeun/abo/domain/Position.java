package me.bogeun.abo.domain;

import lombok.Getter;

@Getter
public enum Position {
    PITCHER(1, "투수"),
    CATCHER(2, "포수"),
    FIRST_BASE(3, "1루수"),
    SECOND_BASE(4, "2루수"),
    THIRD_BASE(5, "3루수"),
    SHOT_STOP(6, "유격수"),
    LEFT_FIELDER(7, "좌익수"),
    CENTER_FIELDER(8, "중견수"),
    RIGHT_FIELDER(9, "우익수");

    private int positionNumber;
    private String kor;

    Position(int positionNumber, String kor) {
        this.positionNumber = positionNumber;
        this.kor = kor;
    }
}
