package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class CenterHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pooledHistoryId;
    @ManyToOne
    @JoinColumn(name = "centerEnd_id")
    private  Working centerEnd;
    @ManyToOne
    @JoinColumn(name = "centerPoll_id")
    private  Working centerPoll;
    private LocalDateTime createAt;
}
