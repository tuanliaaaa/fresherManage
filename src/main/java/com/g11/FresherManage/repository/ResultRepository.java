package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    @Query("SELECT rl FROM Result rl where  rl.fresher.idUser=:fresherId")
    List<Result> findByFresher_Id(@Param("fresherId") Integer fresherId);
}
