package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.entity.Message;
import com.g11.FresherManage.repository.MessageRepository;
import com.g11.FresherManage.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSeviceImpl implements MessageService {
    private final MessageRepository messageRepository;
    @Override
    public List<Message> findMessageByConservationId(String username, Integer conservationId, Integer page)
    {
        return messageRepository.findAllByConservation_IdConservation(conservationId);
    }

}
