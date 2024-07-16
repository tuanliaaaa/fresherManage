package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.HistoryWorking;
import com.g11.FresherManage.entity.Working;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryWorkingRepository extends JpaRepository<HistoryWorking, Integer> {
//    @Query("SELECT hw FROM HistoryWorking hw where hw.account=:account and hw.working=:working and hw.is_status=true")
//    List<HistoryWorking> findHistoryWorkingByAccountAndWorkingAndIs_status(@Param("account") Account account,@Param("working") Working working);
}
