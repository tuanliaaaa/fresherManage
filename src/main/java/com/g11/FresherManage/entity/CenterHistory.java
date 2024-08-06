package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
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
    public CenterHistory(Working centerEnd, Working centerPoll) {
        this.createAt = LocalDateTime.now();
        this.centerEnd = centerEnd;
        this.centerPoll = centerPoll;
    }
}
