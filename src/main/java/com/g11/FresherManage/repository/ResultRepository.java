package com.g11.FresherManage.repository;

import com.g11.FresherManage.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    @Query("SELECT rl FROM Result rl where  rl.fresher.idUser=:fresherId AND rl.testPoint is not null AND rl.status =true")
    List<Result> findByFresherIdAndStatusIs(@Param("fresherId") Integer fresherId);
    Optional<Result> findById(Integer id);
    @Query("SELECT rl FROM Result rl where  rl.fresher.idUser=:fresherId AND rl.testPoint is null AND rl.status =false AND rl.numberTest=:numberTest")
    List<Result> findByFresherIdAndStatusIsFalseAndNumberTest(@Param("fresherId") Integer fresherId, @Param("numberTest") Integer numberTest);

    @Query(value = "SELECT COUNT(*) FROM (" +
            "SELECT r.fresher_id AS accountId " +
            "FROM Result r " +
            "GROUP BY r.fresher_id " +
            "HAVING " +
            "MAX(CASE WHEN r.number_test = 1 THEN r.test_point END) IS NOT NULL AND " +
            "MAX(CASE WHEN r.number_test = 2 THEN r.test_point END) IS NOT NULL AND " +
            "MAX(CASE WHEN r.number_test = 3 THEN r.test_point END) IS NOT NULL AND " +
            "(MAX(CASE WHEN r.number_test = 1 THEN r.test_point END) + " +
            " MAX(CASE WHEN r.number_test = 2 THEN r.test_point END) + " +
            " MAX(CASE WHEN r.number_test = 3 THEN r.test_point END)) / 3.0 > :fromPoint AND " +
            "(MAX(CASE WHEN r.number_test = 1 THEN r.test_point END) + " +
            " MAX(CASE WHEN r.number_test = 2 THEN r.test_point END) + " +
            " MAX(CASE WHEN r.number_test = 3 THEN r.test_point END)) / 3.0 <= :toPoint" +
            ") AS ListPointOfFresher", nativeQuery = true)
    Integer countAccountsWithAvgInRange(@Param("fromPoint") Double fromPoint, @Param("toPoint") Double toPoint);

    @Query(value = "SELECT COUNT(*) FROM (" +
            "SELECT r.fresher_id AS accountId " +
            "FROM Result r " +
            "WHERE r.number_test=:numberTest AND r.test_point > :fromPoint And r.test_point <=:toPoint "+
            "GROUP BY r.fresher_id " +
            "HAVING " +
            "MAX(CASE WHEN r.number_test =:numberTest THEN r.test_point END) IS NOT NULL) as pointTestOfFresher  " , nativeQuery = true)
            Integer countAccountsWithTestInRange(@Param("numberTest") Integer numberTest,@Param("fromPoint") Double fromPoint, @Param("toPoint") Double toPoint);
    @Query(value = "SELECT " +
            "MAX(CASE WHEN r.number_test = 1 THEN r.test_point END) AS max_test1, " +
            "MAX(CASE WHEN r.number_test = 2 THEN r.test_point END) AS max_test2, " +
            "MAX(CASE WHEN r.number_test = 3 THEN r.test_point END) AS max_test3, " +
            "(MAX(CASE WHEN r.number_test = 1 THEN r.test_point END) + " +
            " MAX(CASE WHEN r.number_test = 2 THEN r.test_point END) + " +
            " MAX(CASE WHEN r.number_test = 3 THEN r.test_point END)) / 3.0 AS avg_point " +
            "FROM Result r " +
            "WHERE r.fresher_id = :fresherId " +
            "GROUP BY r.fresher_id", nativeQuery = true)
    Object[] getMaxScoresAndAverageForAccount(@Param("fresherId") Integer fresherId);
}
