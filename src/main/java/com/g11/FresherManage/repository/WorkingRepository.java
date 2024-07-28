package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Working;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingRepository   extends JpaRepository<Working, Integer> {
    List<Working> findByMarket_WorkingId(Integer id);

    @Query(value = "SELECT w.* FROM Working as w WHERE w.working_Type= 'CENTER' LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Working> findAllCenter(  @Param("offset") Integer offset,@Param("limit") Integer limit);
    @Query(value = "SELECT w.* FROM Working as w WHERE w.market_id :=id", nativeQuery = true)
    List<Working> findByMarket_MarketId(Integer id);

    @Query(value = "SELECT w.* FROM Working as w WHERE  w.working_Type= 'MARKET' LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Working> findAllMarket(  @Param("offset") Integer offset,@Param("limit") Integer limit);
    @Query(value = "SELECT w.* FROM Working as w WHERE w.working_Type= 'CENTER' AND w.working_Id=:centerId", nativeQuery = true)
    Optional<Working> getCenterByCenterId(@Param("centerId") Integer centerId);

    @Query(value = "SELECT w.* FROM Working as w WHERE w.working_Type= 'MARKET' AND w.working_Id=:marketId", nativeQuery = true)
    Optional<Working> getMarketByMarketId(@Param("marketId") Integer marketId);

}
