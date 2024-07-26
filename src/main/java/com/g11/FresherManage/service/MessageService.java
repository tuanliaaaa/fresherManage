package com.g11.FresherManage.service;


import com.g11.FresherManage.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> findMessageByConservationId(String username, Integer conservationId, Integer page);
}
