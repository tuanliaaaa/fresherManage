package com.g11.FresherManage.service;


import com.g11.FresherManage.dto.response.message.MessageResponse;
import com.g11.FresherManage.entity.Message;

import java.util.List;

public interface MessageService {
    List<MessageResponse> findMessageByConservationId(Integer conservationId, Integer page);
}
