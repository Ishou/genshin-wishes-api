package com.uf.genshinwishes.dto;

import com.uf.genshinwishes.model.BannerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountsPerBanner {
    private BannerType bannerType;

    private Long last4Stars;
    private Long last5Stars;

    private Long count;
    private Long count4Stars;
    private Long count5Stars;
}
