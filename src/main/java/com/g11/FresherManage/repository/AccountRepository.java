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

    @Query("SELECT ac FROM Account as ac WHERE ac.username= :username AND ac.position = 'FRESHER'")
    Optional<Account> findFresherByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM Account WHERE position = 'FRESHER' LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Account> findFreshersWithPagination(@Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT ac FROM Account as ac WHERE ac.idUser=:fresherId AND ac.position = 'FRESHER'")
    Optional<Account> getByFresherId(Integer fresherId);

    @Query(value = "SELECT * FROM Account WHERE position = 'FRESHER' LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Account> findFreshersForAnotherAdmin(@Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "SELECT ac.* FROM Account ac JOIN History_Working hw ON ac.id_User = hw.account_id WHERE ac.position = 'FRESHER' AND hw.working_id = :workingId LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Account> findFresherByWorkingId(@Param("workingId") Integer workingId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query(value = "SELECT fresher.* " +
            "FROM Account fresher " +
            "WHERE fresher.position = 'FRESHER' " +
            "AND fresher.curent_Working Like CONCAT('%', :workingId, '%') LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<Account> findFreshersByWorkingIds(@Param("workingId") String workingId, @Param("offset") Integer offset,@Param("limit") Integer limit);

    @Query("SELECT ac FROM Account ac WHERE ac.position = 'FRESHER' AND" +
            "(:firstName IS NULL OR ac.firstName LIKE %:name%) AND " +
            "(:lastName IS NULL OR ac.lastName LIKE %:name%) AND " +
            "(:phone IS NULL OR ac.phone = :phone) AND " +
            "(:email IS NULL OR ac.email = :email)")
    List<Account> searchFreshers(@Param("firstName") String firstName,
                                 @Param("lastName") String lastName,
                                 @Param("phone") String phone,
                                 @Param("email") String email);
    @Query(value = "SELECT a.id_User,a.username,a.avatar,a.first_Name,a.last_Name,a.email,a.phone,a.position,a.is_active,a.curent_Working,r.id_Role, r.role_Name " +
            "FROM account a " +
            "JOIN account_role ar ON a.id_User = ar.user_id " +
            "JOIN role r ON ar.role_id = r.id_Role " +
            "WHERE a.username = :username ", nativeQuery = true)
    List<Object[]> findInforByUsernameWithRoles(@Param("username") String username);
}