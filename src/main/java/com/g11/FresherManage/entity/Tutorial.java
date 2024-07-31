package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tutorialId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Account mentor;
    @ManyToOne
    @JoinColumn(name = "fresher_id")
    private Account fresher;

}
