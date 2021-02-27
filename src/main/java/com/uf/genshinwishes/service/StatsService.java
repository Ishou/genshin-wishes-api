package com.uf.genshinwishes.service;

import com.google.common.collect.Maps;
import com.uf.genshinwishes.dto.*;
import com.uf.genshinwishes.dto.mapper.WishMapper;
import com.uf.genshinwishes.model.BannerType;
import com.uf.genshinwishes.model.User;
import com.uf.genshinwishes.model.Wish;
import com.uf.genshinwishes.repository.wish.WishRepository;
import com.uf.genshinwishes.repository.wish.WishSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatsService {

    private EntityManager em;
    private WishRepository wishRepository;
    private WishMapper wishMapper;

    public StatsDTO getStatsFor(User user, BannerType bannerType) {
        WishFilterDTO filters = new WishFilterDTO();
        filters.setRanks(Arrays.asList(4, 5));
        WishSpecification specification = new WishSpecification(user, bannerType, filters);

        StatsDTO stats = new StatsDTO();

//        stats.setNumberOfWishes(this.getNumberOfWishCounter(user));
//        stats.setNumberOf4Stars(this.getNumberOf4Stars(user));
//        stats.setNumberOf5Stars(this.getNumberOf5Stars(user));

        stats.setNumberOfWishesFiltered(this.getNumberOfWishCounterByFilters(specification));

        stats.setCountByItem(getCountPerItem(specification).stream()
            .collect(Collectors.toMap(CountPerItem::getItemId, CountPerItem::getCount)));

        Map<BannerType, CountsPerBanner> countsPerBanner = Maps.newHashMap();

        BannerType.valuesExceptAll().stream().forEach(b -> {
            WishDTO last4Stars = wishMapper.toDto(wishRepository.findFirstByUserAndGachaTypeAndItem_RankTypeOrderByIndexDesc(user, b.getType(), 4));
            WishDTO last5Stars = wishMapper.toDto(wishRepository.findFirstByUserAndGachaTypeAndItem_RankTypeOrderByIndexDesc(user, b.getType(), 5));
            Long count = wishRepository.countByUserAndGachaType(user, b.getType());
            Long count4Stars = wishRepository.countByUserAndGachaTypeAndItem_RankType(user, b.getType(), 4);
            Long count5Stars = wishRepository.countByUserAndGachaTypeAndItem_RankType(user, b.getType(), 5);

            countsPerBanner.put(b, new CountsPerBanner(b, last4Stars != null ? last4Stars.getIndex() : 0, last5Stars != null ? last5Stars.getIndex() : 0, count, count4Stars, count5Stars));
        });

        CountsPerBanner countsForAll = new CountsPerBanner(BannerType.ALL,
            countsPerBanner.keySet().stream().collect(Collectors.summingLong(key -> countsPerBanner.get(key).getLast4Stars())),
            countsPerBanner.keySet().stream().collect(Collectors.summingLong(key -> countsPerBanner.get(key).getLast5Stars())),
            this.getNumberOfWishCounter(user),
            this.getNumberOf4Stars(user),
            this.getNumberOf5Stars(user));

        countsPerBanner.put(BannerType.ALL, countsForAll);

        stats.setCountByBanner(countsPerBanner);

        return stats;
    }

    public List<CountPerItem> getCountPerBanner(WishSpecification specification) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<CountPerItem> query = criteriaBuilder.createQuery(CountPerItem.class);
        Root<Wish> root = query.from(Wish.class);

        query.where(specification.toPredicate(root, query, criteriaBuilder));
        query.groupBy(root.get("gachaType"));

        return em.createQuery(query.multiselect(root.get("gachaType"), criteriaBuilder.count(root.get("gachaType")))).getResultList();
    }

    public List<CountPerItem> getCountPerItem(WishSpecification specification) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<CountPerItem> query = criteriaBuilder.createQuery(CountPerItem.class);
        Root<Wish> root = query.from(Wish.class);

        query.where(specification.toPredicate(root, query, criteriaBuilder));
        query.groupBy(root.get("item").get("itemId"));

        return em.createQuery(query.multiselect(root.get("item").get("itemId"), criteriaBuilder.count(root.get("item")))).getResultList();
    }

    public Long getNumberOfWishCounter(User user) {
        return wishRepository.count(new WishSpecification(user));
    }

    public Long getNumberOf4Stars(User user) {
        WishFilterDTO filters = new WishFilterDTO();
        filters.setRanks(Arrays.asList(4));
        return wishRepository.count(new WishSpecification(user, BannerType.ALL, filters));
    }

    public Long getNumberOf5Stars(User user) {
        WishFilterDTO filters = new WishFilterDTO();
        filters.setRanks(Arrays.asList(5));
        return wishRepository.count(new WishSpecification(user, BannerType.ALL, filters));
    }

    public Long getNumberOfWishCounterByFilters(WishSpecification specification) {
        return wishRepository.count(specification);
    }
}
