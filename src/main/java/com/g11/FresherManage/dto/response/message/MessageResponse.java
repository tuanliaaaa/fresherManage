package com.g11.FresherManage.dto.response.message;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Conservation;
import com.g11.FresherManage.entity.Message;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private int idMessage;
    private String message;
    private Integer sender;
    private Integer conservation;
    private String is_view;
    private String status;
    private LocalDateTime createAt;

    public MessageResponse(Message message)
    {
        this.conservation=message.getConservation().getIdConservation();
        this.message=message.getMessage();
        this.status=message.getStatus();
        this.sender =message.getSender().getIdUser();
        this.idMessage=message.getIdMessage();
        this.is_view=message.getIs_view();
        this.createAt=message.getCreateAt();
    }
}
