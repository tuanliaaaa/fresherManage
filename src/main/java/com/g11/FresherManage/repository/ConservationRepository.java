package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.AccountConservation;
import com.g11.FresherManage.entity.Conservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConservationRepository  extends JpaRepository<Conservation, Integer> {
}
