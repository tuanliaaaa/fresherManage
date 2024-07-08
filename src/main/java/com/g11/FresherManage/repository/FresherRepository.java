package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.AccountRole;
import com.g11.FresherManage.entity.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FresherRepository extends JpaRepository<Fresher, Integer> {
    @Query(value = "SELECT * FROM employee WHERE dtype = 'Fresher' LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Fresher> findFreshersWithPagination(@Param("offset") int offset, @Param("limit") int limit);
    @Query(value = "SELECT * FROM employee WHERE dtype = 'Fresher' and employee_id=:fresherId", nativeQuery = true)
    Optional<Fresher> getByFresherId(@Param("fresherId") Integer fresherId);
    @Query(value = "SELECT * FROM employee WHERE dtype = 'Fresher' and employee_username=:username", nativeQuery = true)
    Optional<Fresher> findByUsername(String username);
}
