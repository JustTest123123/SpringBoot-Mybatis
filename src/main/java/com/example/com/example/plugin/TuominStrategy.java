package com.example.com.example.plugin;

import lombok.Getter;

/**
 * @Author didi
 * @Date 2021/2/21
 */
@Getter
public enum  TuominStrategy {
    USERNAME(s ->
        s.replaceAll("(\\S)\\S(\\S*)","$1*$2")
    );
    private final Desenitize desenitize;
    TuominStrategy(Desenitize d) {
        this.desenitize = d;
    }


}
    