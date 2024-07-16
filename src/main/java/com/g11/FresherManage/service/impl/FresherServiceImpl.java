package com.g11.FresherManage.service.impl;



import com.g11.FresherManage.dto.request.fresher.FresherRequest;
import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.AccountRole;
import com.g11.FresherManage.entity.HistoryWorking;
import com.g11.FresherManage.entity.Role;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.AccountRoleRepository;
import com.g11.FresherManage.repository.HistoryWorkingRepository;
import com.g11.FresherManage.repository.RoleRepository;
import com.g11.FresherManage.service.FresherService;
import com.g11.FresherManage.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FresherServiceImpl implements FresherService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final RoleRepository roleRepository;
    private final HistoryWorkingRepository historyWorkingRepository;
    @Override
    @Cacheable(value = "freshersCache")
    public List<FresherResponse> findAllFreshers(Principal principal,Integer page)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        List<Account> fresherList = new ArrayList<>();
        switch (userLogining.getPotition()) {
            case "ADMIN":
                break;
            default:
                List<String>workingIdsLoging = List.of(userLogining.getCurentWorking().split(","));
                if(workingIdsLoging.size()==0) new EmployeeNotWorkinWorkingException();
                fresherList=accountRepository.findFreshersByWorkingIds(workingIdsLoging,page*10,(page+1)*10);
        }
        return MapperUtils.toDTOs(fresherList,FresherResponse.class);
    }
    @Override
//    @Cacheable(value = "fresherCache",key = "#fresherId")
    public FresherResponse getFresherByFresherId(Principal principal,Integer fresherId)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
                orElseThrow(
                        () -> new UsernameNotFoundException()
                );
        Account fresher = accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        switch (userLogining.getPotition()) {
            case "ADMIN":
                break;
            default:
                String curentWorkingLogining = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();
                String curentWorkingFresher = fresher.getCurentWorking()==null?"":fresher.getCurentWorking();

                if((curentWorkingFresher.equals("")&&curentWorkingLogining.equals(""))||!curentWorkingLogining.contains(curentWorkingFresher))
                    throw new EmployeeNotWorkinWorkingException();
        }
        return MapperUtils.toDTO(fresher, FresherResponse.class);
    }


    @Override
    @CachePut(value = "fresherCache",key = "#fresherId")
    public Void deleteFrdesherByFresherId(Integer fresherId)
    {
        Account fresher=accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        fresher.setIs_active("lock");
        accountRepository.save(fresher);
        return null;
    }
    @Override
    public FresherResponse getMyFresherInfo(Principal principal)
    {
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
    public List<FresherResponse> findFresherByWorkingId(Principal principal,Integer workingId,Integer page)
    {
        Account userLogining = accountRepository.findByUsername(principal.getName()).
            orElseThrow(
                    () -> new UsernameNotFoundException()
            );
        switch (userLogining.getPotition()) {
            case "ADMIN":
                break;
            default:
                String curentWorkingLogining = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();

                if(!curentWorkingLogining.contains(String.valueOf(workingId)+","))
                    throw new EmployeeNotWorkinWorkingException();
        }
        List<Account> freshers= accountRepository.findFresherByWorkingId(workingId,page*10,(page+1)*10);
        return MapperUtils.toDTOs(freshers, FresherResponse.class);
    }

    @Override
    public FresherResponse createFresher(FresherRequest fresherRequest){
        Account fresher = new Account();
        fresher.setPotition("FRESHER");
        fresher.setEmail(fresherRequest.getEmail());
        fresher.setAvatar(fresherRequest.getAvatar());
        fresher.setPhone(fresherRequest.getPhone());
        fresher.setPassword("$2a$10$pDpHbyz8RfcI6P3TVZG3dOrXvfdG9PpkGk7zzeFL5qctKKpU39T/G");
        fresher.setUsername(fresherRequest.getEmail());
        fresher.setFirstName(fresherRequest.getFirstName());
        fresher.setLastName(fresherRequest.getLastName());
        fresher=accountRepository.save(fresher);
        Role fresherRole=roleRepository.getById(6);
        AccountRole accountRole=new AccountRole();
        accountRole.setRole(fresherRole);
        accountRole.setAccount(fresher);
        accountRoleRepository.save(accountRole);
        return MapperUtils.toDTO(fresher, FresherResponse.class);
    }
}
