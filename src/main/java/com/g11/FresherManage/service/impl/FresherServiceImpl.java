package com.g11.FresherManage.service.impl;



import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.service.FresherService;
import com.g11.FresherManage.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FresherServiceImpl implements FresherService {

//    private final FresherRepository fresherRepository;
//    @Override
//    @Cacheable(value = "freshersCache")
//    public List<FresherResponse> findAllFreshers(int offset, int limit)  {
//        return MapperUtils.toDTOs(fresherRepository.findFreshersWithPagination(offset, limit),FresherResponse.class);
//    }
//    @Override
//    @Cacheable(value = "fresherCache",key = "#fresherId")
//    public FresherResponse getFresherByFresherId(Integer fresherId)  {
//        Fresher fresher=fresherRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
//        return MapperUtils.toDTO(fresher, FresherResponse.class);
//    }
//    @Override
//    @CachePut(value = "fresherCache",key = "#fresherId")
//    public Void deleteFrdesherByFresherId(Integer fresherId)  {
//        Fresher fresher=fresherRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
//        fresher.setStatus("lock");
//        fresherRepository.save(fresher);
//        return null;
//    }
//    @Override
//    public FresherResponse getMyFresherInfo(Principal principal){
//        Fresher fresher= fresherRepository.findByUsername(principal.getName()).orElseThrow(FresherNotFoundException::new);
//
//        return MapperUtils.toDTO(fresher, FresherResponse.class);
//    }
//
//    @Override
//    public List<FresherResponse> getFreshersForMentor(String a,String b){
//        List<Fresher> freshers= fresherRepository.findAll();
//        freshers.removeIf(fresher -> fresher.getPhone()!="09");return MapperUtils.toDTOs(freshers, FresherResponse.class);
//
//    }

}
