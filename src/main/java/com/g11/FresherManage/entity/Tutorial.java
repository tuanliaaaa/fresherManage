package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tutorialId;
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Account mentor;
    @ManyToOne
    @JoinColumn(name = "fresher_id")
    private Account fresher;
}
