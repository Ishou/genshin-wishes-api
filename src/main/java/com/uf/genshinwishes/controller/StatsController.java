package com.uf.genshinwishes.controller;

import com.uf.genshinwishes.dto.ItemType;
import com.uf.genshinwishes.dto.StatsDTO;
import com.uf.genshinwishes.dto.WishDTO;
import com.uf.genshinwishes.dto.WishFilterDTO;
import com.uf.genshinwishes.model.BannerType;
import com.uf.genshinwishes.model.User;
import com.uf.genshinwishes.service.StatsService;
import com.uf.genshinwishes.service.WishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final Logger logger = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    private StatsService statsService;

    @GetMapping("")
    public StatsDTO getStats(User user,
                             @PathVariable("bannerType") Optional<BannerType> bannerType) {
        return statsService.getStatsFor(user, bannerType.orElse(BannerType.ALL));
    }
}
