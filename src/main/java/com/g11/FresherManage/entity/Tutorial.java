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
    private Mentor account;
    @ManyToOne
    @JoinColumn(name = "fresher_id")
    private Fresher fresher;
}
