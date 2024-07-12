package com.g11.FresherManage.repository;

import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
    @Query("SELECT ur.role.roleName FROM AccountRole ur WHERE ur.account.username = :username")
    List<String> findRolesByUsername(@Param("username") String username);
    @Query("SELECT ur.role FROM AccountRole ur WHERE ur.account.username = :username")
    List<Role> findRolesAllByUsername(@Param("username") String username);
    @Query("SELECT ac FROM Account as ac WHERE ac.username= :username AND ac.potition = 'FRESHER'" )
    Optional<Account> findFresherByUsername(@Param("username") String username);
    @Query(value = "SELECT * FROM Account WHERE potition = 'FRESHER' LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Account> findFreshersWithPagination(@Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT ac FROM Account as ac WHERE ac.idUser=:fresherId AND ac.potition = 'FRESHER'" )
    Optional<Account> getByFresherId(Integer fresherId);

    @Query(value = "SELECT * FROM Account WHERE potition = 'FRESHER' LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Account> findFreshersForAnotherAdmin(@Param("offset") int offset, @Param("limit") int limit);

}