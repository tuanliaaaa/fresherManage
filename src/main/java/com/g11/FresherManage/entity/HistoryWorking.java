package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class HistoryWorking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyWorkingId;
    @ManyToOne
    @JoinColumn(name = "working_id")
    private Working working;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
