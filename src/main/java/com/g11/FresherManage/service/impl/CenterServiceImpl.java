package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.CenterResponse;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.repository.WorkingRepository;
import com.g11.FresherManage.service.CenterService;
import com.g11.FresherManage.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CenterServiceImpl implements CenterService {
    private final WorkingRepository workingRepository;

    @Override
    public List<CenterResponse> findMyCenter(Principal principal)
    {
        List<Working> workingList = workingRepository.findAll();
        return MapperUtils.toDTOs(workingList,CenterResponse.class);
    }


    @Override
    public CenterResponse getCenterByCenterId(Integer centerId)
    {
        CenterResponse centerResponse = new CenterResponse();
        return  centerResponse;
    }
}
