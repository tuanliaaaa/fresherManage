package com.g11.FresherManage.service.impl;



import com.g11.FresherManage.dto.request.fresher.FresherRequest;
import com.g11.FresherManage.dto.request.fresher.FresherUpdateRequest;
import com.g11.FresherManage.dto.response.fresher.FresherResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.AccountRole;
import com.g11.FresherManage.entity.HistoryWorking;
import com.g11.FresherManage.entity.Role;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.base.UnauthorizedException;
import com.g11.FresherManage.exception.fresher.FresherNotFoundException;
import com.g11.FresherManage.exception.role.RoleNotFoundException;
import com.g11.FresherManage.exception.workinghistory.EmployeeNotWorkinWorkingException;
import com.g11.FresherManage.repository.*;
import com.g11.FresherManage.service.AccountRoleService;
import com.g11.FresherManage.service.FresherService;
import com.g11.FresherManage.utils.MapperUtils;
import com.g11.FresherManage.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FresherServiceImpl implements FresherService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final RoleRepository roleRepository;
    private final HistoryWorkingRepository historyWorkingRepository;
    private final AccountRoleService accountRoleService;
    private final WorkingRepository workingRepository;
    @Override
//    @Cacheable(value = "freshersCache")
    @CacheEvict(value = "freshersCache")

    public List<FresherResponse> findAllFreshers(Integer page)
    {
        String username = accountRoleService.getUsername();
        if(username==null) throw new UnauthorizedException();
        List<String> roleList=accountRoleService.findRolesByUserLoging();
        List<Account> fresherList = new ArrayList<>();
        if(!roleList.contains("ROLE_ADMIN"))
        {
            Account userLogining = accountRepository.findByUsername(username).
                    orElseThrow(
                            () -> new UsernameNotFoundException()
                    );
            List<String> workingIdsLoging = List.of(userLogining.getCurentWorking().split(","))
                    .stream()
                    .filter(id -> id.endsWith("*"))
                    .collect(Collectors.toList());

            if(workingIdsLoging.size()==0) new EmployeeNotWorkinWorkingException();
            fresherList=accountRepository.findFreshersByWorkingIds(workingIdsLoging.get(0),page*10,(page+1)*10);
        }else fresherList=accountRepository.findFreshersWithPagination(page*10,(page+1)*10);
        List<FresherResponse> fresherResponseList = MapperUtils.toDTOs(fresherList,FresherResponse.class);
        log.info("findAllFreshers success: {} ",fresherResponseList);
        return fresherResponseList;
    }
    @Override
//    @Cacheable(value = "fresherCache",key = "#fresherId")
    public FresherResponse getFresherByFresherId(Integer fresherId)
    {
        String username = accountRoleService.getUsername();
        if(username==null) throw new UnauthorizedException();
        List<String> roleList=accountRoleService.findRolesByUserLoging();
        Account fresher = accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        if(!roleList.contains("ROLE_ADMIN"))
        {
            Account userLogining = accountRepository.findByUsername(username).
                    orElseThrow(
                            () -> new UsernameNotFoundException()
                    );

            String curentWorkingLogining = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();
            String curentWorkingFresher = fresher.getCurentWorking()==null?"":fresher.getCurentWorking();
            if((curentWorkingFresher.equals("")&&curentWorkingLogining.equals("")))
                throw new EmployeeNotWorkinWorkingException();
            List<String> workingIdsLoging = List.of(curentWorkingFresher.split(","))
                    .stream()
                    .filter(id -> id.endsWith("*"))
                    .collect(Collectors.toList());
            if(!curentWorkingLogining.contains(workingIdsLoging.get(0)))
                throw new EmployeeNotWorkinWorkingException();
        }
        FresherResponse fresherResponse = MapperUtils.toDTO(fresher, FresherResponse.class);
        log.info("fresherResponse: {}", fresherResponse);
        return fresherResponse;
    }




    @Override
    public FresherResponse getFresherByUsername(String username)
    {
        Account fresher= accountRepository.findFresherByUsername(
                username).orElseThrow(FresherNotFoundException::new);
        FresherResponse fresherResponse = MapperUtils.toDTO(fresher, FresherResponse.class);
        log.info("fresher response:{}",fresherResponse);
        return fresherResponse;
    }

    @Override
    @CachePut(value = "fresherCache",key = "#fresherId")
    public Void deleteFrdesherByFresherId(Integer fresherId)
    {
        Account fresher=accountRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        fresher.setIs_active("lock");
        accountRepository.save(fresher);
        log.info("delete Frdesher success: {}", fresherId);
        return null;
    }


    @Override
    public List<FresherResponse> getFreshersForAnotherAdmin(Principal principal,Integer offset,Integer limit){
        List<Account> freshers= accountRepository.findAll();
        return MapperUtils.toDTOs(freshers, FresherResponse.class);
    }

    @Override
    public List<FresherResponse> findFresherByCenterId(Integer centerId,Integer page)
    {
        List<String> roleList=accountRoleService.findRolesByUserLoging();
        List<Account> fresher =accountRepository.findFreshersByWorkingIds(String.valueOf(centerId)+",",page*10,(page+1)*10);
        if(!roleList.contains("ROLE_ADMIN"))
        {
            String username = accountRoleService.getUsername();
            if(username==null) throw new UnauthorizedException();
            Account userLogining = accountRepository.findByUsername(username).
                    orElseThrow(
                            () -> new UsernameNotFoundException()
                    );

            String curentWorkingLogining = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();

            if(!curentWorkingLogining.contains(String.valueOf(centerId)+","))
                throw new EmployeeNotWorkinWorkingException();
        }
        List<FresherResponse> fresherResponseList= MapperUtils.toDTOs(fresher,FresherResponse.class);
        log.info("findFreshers success: {} ",fresherResponseList);
        return fresherResponseList;
    }

    @Override
    public List<FresherResponse> findFresherByMarketId(Integer marketId,Integer page)
    {
        List<String> roleList=accountRoleService.findRolesByUserLoging();
        List<Account> fresher =accountRepository.findFreshersByWorkingIds(String.valueOf(marketId)+"*",page*10,(page+1)*10);
        if(!roleList.contains("ROLE_ADMIN"))
        {
            String username = accountRoleService.getUsername();
            if(username==null) throw new UnauthorizedException();
            Account userLogining = accountRepository.findByUsername(username).
                    orElseThrow(
                            () -> new UsernameNotFoundException()
                    );

            String curentWorkingLogining = userLogining.getCurentWorking()==null?"":userLogining.getCurentWorking();

            if(!curentWorkingLogining.contains(String.valueOf(marketId)+"*,"))
                throw new EmployeeNotWorkinWorkingException();
        }
        List<FresherResponse> fresherResponseList= MapperUtils.toDTOs(fresher,FresherResponse.class);
        log.info("find Freshers by market id success: {} ",fresherResponseList);
        return fresherResponseList;
    }

    @Override
    public FresherResponse createFresher(FresherRequest fresherRequest)
    {
        Account fresher = new Account(
                fresherRequest.getPassword(),
                fresherRequest.getAvatar(),
                fresherRequest.getFirstName(),
                fresherRequest.getLastName(),
                fresherRequest.getEmail(),
                fresherRequest.getPhone(),
                "FRESHER"
        );
        fresher=accountRepository.save(fresher);
        Role fresherRole=roleRepository.findByRoleName("ROLE_FRESHER").orElseThrow(
                ()-> new RoleNotFoundException());
        AccountRole accountRole=new AccountRole();
        accountRole.setRole(fresherRole);
        accountRole.setAccount(fresher);
        accountRoleRepository.save(accountRole);
        FresherResponse fresherResponse =MapperUtils.toDTO(fresher, FresherResponse.class);
        log.info("createFresher success: {} ",fresherResponse);
        return fresherResponse;
    }

    @Override
    public FresherResponse updateFresher(Integer fresherId, FresherUpdateRequest fresherUpdateRequest)
    {
        Account fresher = accountRepository.getByFresherId(fresherId)
                .orElseThrow(() -> new FresherNotFoundException());

        UpdateUtils.updateEntityFromDTO(fresher, fresherUpdateRequest);
        fresher=accountRepository.save(fresher);
        FresherResponse fresherResponse = MapperUtils.toDTO(fresher, FresherResponse.class);
        log.info("fresher update success: {}", fresherResponse);
        return  fresherResponse;
    }

    @Override
    public List<FresherResponse> searchFreshers(String firstName,String lastName,String phone,String email) {
        return MapperUtils.toDTOs(accountRepository.searchFreshers(firstName,lastName,phone,email),FresherResponse.class);
    }

}
