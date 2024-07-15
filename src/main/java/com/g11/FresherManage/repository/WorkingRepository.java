package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Working;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingRepository   extends JpaRepository<Working, Integer> {
}
