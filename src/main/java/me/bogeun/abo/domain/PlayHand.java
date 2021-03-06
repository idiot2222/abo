package me.bogeun.abo.domain;

import lombok.Getter;

@Getter
public enum PlayHand {
    RPRB("RPRB", "우투우타"),
    RPLB("RPLB", "우투좌타"),
    RPSB("RPSB", "우투양타"),
    LPRB("LPRB", "좌투우타"),
    LPLB("LPLB", "좌투좌타"),
    LPSB("LPSB", "좌투양타");

    private String eng;
    private String kor;

    PlayHand(String eng, String kor) {
        this.eng = eng;
        this.kor = kor;
    }
}
