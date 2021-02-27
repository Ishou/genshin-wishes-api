package com.uf.genshinwishes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.boot.Banner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum BannerType {
    ALL(-1),
    NOVICE(100),
    PERMANENT(200),
    CHARACTER_EVENT(301),
    WEAPON_EVENT(302),

    //
    ;

    private Integer type;

    public static Optional<BannerType> from(Integer gachaType) {
        return Arrays.stream(BannerType.values()).filter(banner -> banner.getType().equals(gachaType)).findFirst();
    }

    public static List<BannerType> valuesExceptAll() {
        return Arrays.stream(BannerType.values()).filter(b -> !BannerType.ALL.equals(b)).collect(Collectors.toList());
    }
}
