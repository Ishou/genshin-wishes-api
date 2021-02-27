package com.uf.genshinwishes.repository.wish;

import com.uf.genshinwishes.model.User;
import com.uf.genshinwishes.model.Wish;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends PagingAndSortingRepository<Wish, Long>, JpaSpecificationExecutor {

    Optional<Wish> findFirstByUserOrderByTimeDescIdDesc(User user);

    List<Wish> findFirst100ByUserAndGachaTypeOrderByIdDesc(User user, Integer gachaType);

    Wish findFirstByUserAndGachaTypeAndItem_RankTypeOrderByIndexDesc(User user, Integer gachaType, Integer rankType);

    void deleteByUser(User user);

    Long countByUserAndGachaType(User user, Integer gachaType);

    Long countByUserAndGachaTypeAndItem_RankType(User user, Integer gachaType, Integer rankType);
}
