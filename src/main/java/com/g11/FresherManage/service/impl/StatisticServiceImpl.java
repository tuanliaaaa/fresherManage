package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.statistic.FresherAmountResponse;
import com.g11.FresherManage.dto.response.statistic.FresherPointResponse;
import com.g11.FresherManage.dto.response.statistic.TableFresherResultResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.HistoryWorkingRepository;
import com.g11.FresherManage.repository.ResultRepository;
import com.g11.FresherManage.service.StatistisService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StatisticServiceImpl implements StatistisService {
    private final AccountRepository accountRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final StatistisService statistisService;
    private final HistoryWorkingRepository historyWorkingRepository;
    private final ResultRepository resultRepository;

    @Override
    public List<FresherPointResponse> findStatisticFresherPoint(List<Double> rankPointList,String typePoint)
    {
        List<FresherPointResponse> fresherPointResponseList = new ArrayList<>();
        if(typePoint.equals("avg"))
        {
            // Get Rank Point from to with avg point
            for(int i=0;i<rankPointList.size();i++)
            {
                if(i==0)fresherPointResponseList.add(
                        statistisService.countAccountsWithAvgInRange(0.0
                                ,rankPointList.get(i)));
                else {
                    fresherPointResponseList.add(
                            statistisService.countAccountsWithAvgInRange(
                                    rankPointList.get(i-1)
                                    ,rankPointList.get(i)));
                }
            }
            fresherPointResponseList.add(
                    statistisService.countAccountsWithAvgInRange(
                            rankPointList.get(rankPointList.size()-1)
                            ,10.0));
        } else {
            // Get Rank Point from to with Test1 or tesst2 or test3
            for(int i=0;i<rankPointList.size();i++)
            {
                if(i==0)fresherPointResponseList.add(countAccountsWithTestInRange(Integer.parseInt(typePoint),0.0,rankPointList.get(0)));
                else{
                    fresherPointResponseList.add(countAccountsWithTestInRange(Integer.parseInt(typePoint),rankPointList.get(i-1),rankPointList.get(i)));
                }
            }
            fresherPointResponseList.add(countAccountsWithTestInRange(Integer.parseInt(typePoint),rankPointList.get(rankPointList.size()-1),10.0));
        }
        return fresherPointResponseList;
    }

    @Override
    public FresherPointResponse countAccountsWithAvgInRange(Double fromPoint, Double toPoint)
    {
        return new FresherPointResponse(toPoint,resultRepository.countAccountsWithAvgInRange(fromPoint,toPoint));
    }
    @Override
    public FresherPointResponse countAccountsWithTestInRange(Integer numberTest,Double fromPoint, Double toPoint)
    {
        return new FresherPointResponse(toPoint,resultRepository.countAccountsWithTestInRange(numberTest,fromPoint,toPoint));
    }
    @Override
    public List<FresherAmountResponse> findStatisticFresherAmount(LocalDate startDate, LocalDate endDate, String workingType, Integer workingId) {
        List<FresherAmountResponse> fresherAmountResponses = new ArrayList<>();
        List<LocalDate> dates = getDatesBetween(startDate, endDate);

        if (workingType == null) {
            for (LocalDate date : dates) {
                fresherAmountResponses.add(statistisService.getAmountCountFresherToDay(date));
            }
        } else if (workingType.equals("CENTER") && workingId!=null) {
            for (LocalDate date : dates) {
                fresherAmountResponses.add(statistisService.getAmountCountFresherByCenterToDay(date,workingId));
            }
        }else if (workingType.equals("MARKET") && workingId!=null) {
            for (LocalDate date : dates) {
                fresherAmountResponses.add(statistisService.getAmountCountFresherByMarketToDay(date,workingId));

            }
        }
        return fresherAmountResponses;
    }
    @Cacheable(value = "fresherAmountCache",key = "#daySearch.toString()")
    public FresherAmountResponse getAmountCountFresherToDay(LocalDate daySearch) {
        return new FresherAmountResponse(daySearch,accountRepository.countFreshersActiveOnDate(daySearch));
    }
    @Override
    @Cacheable(value = "fresherAmountCacheByCenter",key = "#daySearch.toString()")
    public FresherAmountResponse getAmountCountFresherByCenterToDay(LocalDate daySearch,Integer centerId) {
        return new FresherAmountResponse(daySearch,historyWorkingRepository.countFreshersActiveinCenterOnDate(daySearch,centerId));
    }
    @Override
    @Cacheable(value = "fresherAmountCacheByMarket",key = "#daySearch.toString()")
    public FresherAmountResponse getAmountCountFresherByMarketToDay(LocalDate daySearch,Integer centerId) {
        return new FresherAmountResponse(daySearch,historyWorkingRepository.countFreshersActiveinMarketOnDate(daySearch,centerId));
    }


    private static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());
    }


    @Override
    public List<TableFresherResultResponse> findStatisticTableDashboad(
            Double test1,
            Double test2,
            Double test3,
            Double avg,
            Integer idCenter,
            Integer idmarket,
            List<Map<String,Integer>> sort
    )
    {
        String queryStr = "SELECT " +
                "MAX(CASE WHEN r.numberTest = 1 THEN r.testPoint END) AS max_test1, " +
                "MAX(CASE WHEN r.numberTest = 2 THEN r.testPoint END) AS max_test2, " +
                "MAX(CASE WHEN r.numberTest = 3 THEN r.testPoint END) AS max_test3, " +
                "(MAX(CASE WHEN r.numberTest = 1 THEN r.testPoint END) + " +
                "MAX(CASE WHEN r.numberTest = 2 THEN r.testPoint END) + " +
                "MAX(CASE WHEN r.numberTest = 3 THEN r.testPoint END)) / 3.0 AS avg_point, " +
                "  a.username, w.workingName ,w.market.workingName " +
                "FROM Result r " +
                "JOIN r.fresher a " +
                "JOIN HistoryWorking hw ON hw.account = a " +
                "JOIN hw.working w on hw.working=w " +
                "WHERE (:centerID is null or w.id=:centerID) " +
                "AND (:marketID is null or w.market.id=:marketID) " +
                "GROUP BY a.idUser, a.username, w.workingName, w.market.workingName ,w.market.id ,w.id " +
                "HAVING (:test1 IS NULL OR MAX(CASE WHEN r.numberTest = 1 THEN r.testPoint END) = :test1) " +
                "AND (:test2 IS NULL OR MAX(CASE WHEN r.numberTest = 2 THEN r.testPoint END) = :test2) " +
                "AND (:test3 IS NULL OR MAX(CASE WHEN r.numberTest = 3 THEN r.testPoint END) = :test3) " +
                "AND (:avg IS NULL OR (MAX(CASE WHEN r.numberTest = 1 THEN r.testPoint END) + " +
                "MAX(CASE WHEN r.numberTest = 2 THEN r.testPoint END) + " +
                "MAX(CASE WHEN r.numberTest = 3 THEN r.testPoint END)) / 3.0 = :avg) ";
        StringBuilder orderByClause = new StringBuilder();
        if(sort!=null)
            for (Map<String, Integer> sortStr : sort) {
                if (sortStr.containsKey("test1")) {
                    Integer sortOrder = sortStr.get("test1");
                    if (sortOrder == 0) {
                        orderByClause.append(", MAX(CASE WHEN r.numberTest = 1 THEN r.testPoint END) ASC");
                    } else {
                        orderByClause.append(", MAX(CASE WHEN r.numberTest = 1 THEN r.testPoint END) DESC");
                    }
                }
                if (sortStr.containsKey("test2")) {
                    Integer sortOrder = sortStr.get("test2");
                    if (sortOrder == 0) {
                        orderByClause.append(", MAX(CASE WHEN r.numberTest = 2 THEN r.testPoint END) ASC");
                    } else {
                        orderByClause.append(", MAX(CASE WHEN r.numberTest = 2 THEN r.testPoint END) DESC");
                    }
                }
                if (sortStr.containsKey("test3")) {
                    Integer sortOrder = sortStr.get("test3");
                    if (sortOrder == 0) {
                        orderByClause.append(", MAX(CASE WHEN r.numberTest = 3 THEN r.testPoint END) ASC");
                    } else {
                        orderByClause.append(", MAX(CASE WHEN r.numberTest = 3 THEN r.testPoint END) DESC");
                    }
                }
                if (sortStr.containsKey("avg")) {
                    Integer sortOrder = sortStr.get("avg");
                    if (sortOrder == 0) {
                        orderByClause.append("((MAX(CASE WHEN r.numberTest = 1 THEN r.testPoint END) + " +
                                "MAX(CASE WHEN r.numberTest = 2 THEN r.testPoint END) + " +
                                "MAX(CASE WHEN r.numberTest = 3 THEN r.testPoint END)) / 3.0) ASC");
                    } else {
                        orderByClause.append("((MAX(CASE WHEN r.numberTest = 1 THEN r.testPoint END) + " +
                                "MAX(CASE WHEN r.numberTest = 2 THEN r.testPoint END) + " +
                                "MAX(CASE WHEN r.numberTest = 3 THEN r.testPoint END)) / 3.0) DESC");
                    }
                }
                if (sortStr.containsKey("centerID")) {
                    Integer sortOrder = sortStr.get("centerID");
                    if (sortOrder == 0) {
                        orderByClause.append(", w.id ASC");
                    } else {
                        orderByClause.append(", w.id DESC");
                    }
                }
                if (sortStr.containsKey("marketID")) {
                    Integer sortOrder = sortStr.get("marketID");
                    if (sortOrder == 0) {
                        orderByClause.append(", w.market.id ASC");
                    } else {
                        orderByClause.append(", w.market.id DESC");
                    }
                }
            }

        if (orderByClause.length() > 0) {
            queryStr += "ORDER BY " + orderByClause.substring(1); // Xóa ký tự ',' đầu tiên
        }
        TypedQuery<Object[]> query = entityManager.createQuery(queryStr, Object[].class);

        query.setParameter("test1", test1);
        query.setParameter("test2", test2);
        query.setParameter("test3", test3);
        query.setParameter("avg", avg);
        query.setParameter("marketID",idmarket);
        query.setParameter("centerID",idCenter);

        List<Object[]> results = query.getResultList();
        List<TableFresherResultResponse> tableFresherResultResponses = new ArrayList<>();
        for (Object[] result : results) {
            TableFresherResultResponse tableFresherResultResponse = new TableFresherResultResponse();
            if(result[0] != null) {
                tableFresherResultResponse.setTest1(Double.valueOf(result[0].toString()));
            }
            if(result[1] != null) {
                tableFresherResultResponse.setTest2(Double.valueOf(result[1].toString()));
            }
            if(result[2] != null) {
                tableFresherResultResponse.setTest3(Double.valueOf(result[2].toString()));
            }
            if(result[3] != null) {
                tableFresherResultResponse.setAvg(Double.valueOf(result[3].toString()));
            }
            if(result[4] != null) {
                tableFresherResultResponse.setUsername(result[4].toString());
            }if(result[5] != null) {
                tableFresherResultResponse.setCentername(result[5].toString());
            }if(result[6] != null) {
                tableFresherResultResponse.setMarketname(result[6].toString());
            }
            tableFresherResultResponses.add(tableFresherResultResponse);
        }
        return tableFresherResultResponses;
    }

}
