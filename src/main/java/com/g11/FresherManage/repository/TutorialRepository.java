package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Result;
import com.g11.FresherManage.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer>{
    @Query("SELECT tr FROM Tutorial tr WHERE tr.status = false AND tr.fresher.idUser = :fresherId AND tr.mentor.idUser=:mentorId")
    List<Tutorial> findAllByStatusIsAAndFresherIdUserAndMentorIdUser(@Param("fresherId") Integer fresherId, @Param("mentorId") Integer mentorId);

    @Query("SELECT tr FROM Tutorial tr WHERE tr.status = false AND tr.fresher.idUser = :fresherId ")
    List<Tutorial> findAllByStatusIsAAndFresherIdUser(@Param("fresherId") Integer fresherId);

}
