package com.g11.FresherManage.service.impl;



import com.g11.FresherManage.dto.response.FresherResponse;
import com.g11.FresherManage.entity.Fresher;
import com.g11.FresherManage.exception.Fresher.FresherNotFoundException;
import com.g11.FresherManage.exception.base.NotFoundException;
import com.g11.FresherManage.repository.FresherRepository;
import com.g11.FresherManage.service.FresherService;
import com.g11.FresherManage.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class FresherServiceImpl implements FresherService {

    private final FresherRepository fresherRepository;
    @Override
    @Cacheable(value = "freshersCache")
    public List<FresherResponse> findAllFreshers(int offset, int limit)  {
        return MapperUtils.toDTOs(fresherRepository.findFreshersWithPagination(offset, limit),FresherResponse.class);
    }
    @Override
    @Cacheable(value = "fresherCache",key = "#fresherId")
    public FresherResponse getFresherByFresherId(Integer fresherId)  {
        Fresher fresher=fresherRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        return MapperUtils.toDTO(fresher, FresherResponse.class);
    }
    @Override
    @CachePut(value = "fresherCache",key = "#fresherId")
    public Void deleteFrdesherByFresherId(Integer fresherId)  {
        Fresher fresher=fresherRepository.getByFresherId(fresherId).orElseThrow(FresherNotFoundException::new);
        fresher.setStatus("lock");
        fresherRepository.save(fresher);
        return null;
    }
}