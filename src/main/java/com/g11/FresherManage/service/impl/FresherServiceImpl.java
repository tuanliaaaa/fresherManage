package com.g11.FresherManage.service.impl;



import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.repository.AccountRepository;
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

    private final AccountRepository accountRepository;
    @Override
    @Cacheable(value = "freshersCache")
    public List<FresherResponse> findAllFreshers(int offset, int limit)  {
        return MapperUtils.toDTOs(accountRepository.findFreshersWithPagination(offset, limit),FresherResponse.class);
    }
    @Override
//    @Cacheable(value = "fresherCache",key = "#fresherId")
    public FresherResponse getFresherByFresherId(Principal principal,Integer fresherId)  {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        Account fresher = new Account();
        switch (userLogining.getPotition()) {
            case "ADMIN":
                 fresher=accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
            case "":
                break;
        }
        return MapperUtils.toDTO(fresher, FresherResponse.class);
    }
    @Override
    @CachePut(value = "fresherCache",key = "#fresherId")
    public Void deleteFrdesherByFresherId(Integer fresherId)  {
        Account fresher=accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        fresher.setIs_active("lock");
        accountRepository.save(fresher);
        return null;
    }
    @Override
    public FresherResponse getMyFresherInfo(Principal principal){
        Account fresher= accountRepository.findFresherByUsername(
                principal.getName()).orElseThrow(FresherNotFoundException::new);
        return MapperUtils.toDTO(fresher, FresherResponse.class);
    }

    @Override
    public List<FresherResponse> getFreshersForAnotherAdmin(Principal principal,Integer offset,Integer limit){
        List<Account> freshers= accountRepository.findAll();
        return MapperUtils.toDTOs(freshers, FresherResponse.class);
    }

    @Override
    public List<FresherResponse> findFresherByCenterId(Principal principal,Integer centerId){
        List<Account> freshers= accountRepository.findAll();
        return MapperUtils.toDTOs(freshers, FresherResponse.class);
    }

}
