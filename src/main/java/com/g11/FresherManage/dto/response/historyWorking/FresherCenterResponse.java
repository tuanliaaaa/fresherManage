package com.g11.FresherManage.dto.response.historyWorking;

import com.g11.FresherManage.entity.HistoryWorking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FresherCenterResponse {
    private Integer historyWorkingId;
    private Integer fresherId;
    private Integer centerID;
    public FresherCenterResponse(HistoryWorking historyWorking)
    {
        this.centerID=historyWorking.getWorking().getWorkingId();
        this.historyWorkingId=historyWorking.getHistoryWorkingId();
        this.fresherId=historyWorking.getAccount().getIdUser();
    }
}
