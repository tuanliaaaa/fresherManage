package com.g11.FresherManage.service.impl;

import com.g11.FresherManage.dto.response.conservation.ConservationResponse;
import com.g11.FresherManage.dto.response.message.MessageResponse;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Conservation;
import com.g11.FresherManage.entity.Message;
import com.g11.FresherManage.exception.account.UsernameNotFoundException;
import com.g11.FresherManage.exception.base.UnauthorizedException;
import com.g11.FresherManage.exception.conservation.ConservationNotOfUser;
import com.g11.FresherManage.repository.AccountConservationRepository;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.ConservationRepository;
import com.g11.FresherManage.repository.MessageRepository;
import com.g11.FresherManage.service.AccountRoleService;
import com.g11.FresherManage.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSeviceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final AccountConservationRepository accountConservationRepository;
    private final AccountRoleService accountRoleService;
    private final AccountRepository accountRepository;
    @Override
    public List<MessageResponse> findMessageByConservationId(Integer conservationId, Integer page) {
        String username = accountRoleService.getUsername();
        if (username == null) throw new UnauthorizedException();

        List<Conservation> conservations = accountConservationRepository.findConservationsByAccount_Username(username);
        if (conservations.size() == 0) throw new ConservationNotOfUser();
        List<Message> messages = messageRepository.findMessagesByConservationIdOrderByMessageIdDesc(conservationId, page * 10, (page + 1) * 10);
        List<MessageResponse> messageResponses = new ArrayList<>();
        for (Message message : messages) messageResponses.add(new MessageResponse(message));
        return messageResponses;
    }
}
