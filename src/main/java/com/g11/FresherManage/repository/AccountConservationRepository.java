package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.AccountConservation;
import com.g11.FresherManage.entity.Conservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountConservationRepository extends JpaRepository<AccountConservation, Integer> {
    @Query("SELECT ac.conservation FROM AccountConservation ac WHERE ac.account = :account")
    List<Conservation> findConservationsByAccount(@Param("account") Account account);

}
