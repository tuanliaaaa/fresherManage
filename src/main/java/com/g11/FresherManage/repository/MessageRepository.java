package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.HistoryWorking;
import com.g11.FresherManage.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT * FROM Message WHERE conservation_id = :conservationId ORDER BY create_at DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Message> findMessagesByConservationIdOrderByMessageIdDesc(@Param("conservationId") int conservationId,
                                                                   @Param("offset") int offset,
                                                                   @Param("limit") int limit);
}
