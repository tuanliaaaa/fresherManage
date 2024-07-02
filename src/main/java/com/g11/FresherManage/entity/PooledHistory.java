package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class PooledHistory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pooledHistoryId;
    @ManyToOne
    @JoinColumn(name = "centerEnd_id")
    private  Center centerEnd;
    @ManyToOne
    @JoinColumn(name = "centerPoll_id")
    private  Center centerPoll;
    private LocalDateTime createAt;
}
