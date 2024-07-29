package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class HistoryWorking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyWorkingId;
    @ManyToOne
    @JoinColumn(name = "working_id")
    private Working working;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private boolean is_status;
    private LocalDate created_at;
    private LocalDate end_at;
    public HistoryWorking(Working working, Account account)
    {
        this.working=working;
        this.account=account;
        this.is_status=true;
        this.created_at=LocalDate.now();
    }
}
