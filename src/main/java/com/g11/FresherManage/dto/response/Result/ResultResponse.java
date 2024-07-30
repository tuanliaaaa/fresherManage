package com.g11.FresherManage.dto.response.Result;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Result;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {
    private int resultId;
    private Integer testPoint;
    private LocalDateTime createTime;
    private Integer fresher;
    private Integer mentor;
    public ResultResponse(Result result) {
        this.resultId = result.getResultId();
        this.testPoint = result.getTestPoint();
        this.createTime = result.getCreateTime();
        this.fresher= result.getFresher().getIdUser();
        this.mentor= result.getMentor().getIdUser();
    }
}
