package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.HistoryWorking;
import com.g11.FresherManage.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByConservation_IdConservation(Integer conservationId);
}
