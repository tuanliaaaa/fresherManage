package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.HistoryWorking;
import com.g11.FresherManage.entity.Working;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryWorkingRepository extends JpaRepository<HistoryWorking, Integer>
{
    @Query("SELECT hw FROM HistoryWorking hw where hw.account=:account and hw.is_status=true")
    List<HistoryWorking> findHistoryWorkingByAccountIs_status(@Param("account") Account account);
    @Query("SELECT count (hw) FROM HistoryWorking hw where hw.working.workingId=:centerId AND hw.account.position = 'FRESHER' AND hw.created_at <= :searchDate  and  (hw.end_at IS NULL OR hw.end_at >= :searchDate)")
    Long countFreshersActiveinCenterOnDate(@Param("searchDate") LocalDate searchDate,@Param("centerId") Integer centerId);
    @Query("SELECT count (hw) FROM HistoryWorking hw where hw.working.market.workingId=:marketId AND hw.account.position = 'FRESHER' AND hw.created_at <= :searchDate  and  (hw.end_at IS NULL OR hw.end_at >= :searchDate)")
    Long countFreshersActiveinMarketOnDate(@Param("searchDate") LocalDate searchDate,@Param("marketId") Integer marketId);

}
