package com.g11.FresherManage.dto.response.centerHistory;

import com.g11.FresherManage.entity.Working;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterHistoryResponse {
    private int pooledHistoryId;
    private Working centerEnd;
    private  Working centerPoll;
    private LocalDateTime createAt;

}
