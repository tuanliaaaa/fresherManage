package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Working;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingRepository   extends JpaRepository<Working, Integer> {
    List<Working> findByMarket_WorkingId(Integer id);

    @Query(value = "SELECT w.* FROM Working as w WHERE w.market is not null LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Working> findAllCenter( @Param("limit") Integer limit, @Param("offset") Integer offset);
    @Query(value = "SELECT w.* FROM Working as w WHERE w.market_id :=id", nativeQuery = true)
    List<Working> findByMarket_MarketId(Integer id);

    @Query(value = "SELECT w.* FROM Working as w WHERE w.market is null LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Working> findAllMarket( @Param("limit") Integer limit, @Param("offset") Integer offset);

}
