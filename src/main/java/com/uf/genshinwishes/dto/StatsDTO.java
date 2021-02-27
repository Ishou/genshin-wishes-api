package com.uf.genshinwishes.dto;

import com.uf.genshinwishes.model.BannerType;
import com.uf.genshinwishes.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatsDTO {
//    private Long numberOfWishes;
//    private Long numberOf4Stars;
//    private Long numberOf5Stars;

    private Long numberOfWishesFiltered;

    private Map<Long, Long> countByItem;
    private Map<BannerType, CountsPerBanner> countByBanner;
}
