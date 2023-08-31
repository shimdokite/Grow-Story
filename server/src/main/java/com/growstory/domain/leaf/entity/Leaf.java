package com.growstory.domain.leaf.entity;

import com.growstory.domain.account.entity.Account;
import com.growstory.domain.board.entity.Board;
import com.growstory.domain.journal.entity.Journal;
import com.growstory.domain.plant_object.entity.PlantObject;
import com.growstory.global.audit.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Leaf extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leafId;

    @Column(nullable = false)
    private String leafName;

    @Column(nullable = false)
    private String leafNickName;

    @Lob
    @Column(nullable = false)
    private String content;

    private LocalDate startDate;

    private LocalDate waterDate;

    private String place;

    @Column(nullable = false)
    private String leafImageUrl;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @OneToOne(mappedBy = "leaf")
    private Board board;

    @OneToOne(mappedBy = "leaf")
    private PlantObject plantObject;

    @OneToMany(mappedBy = "leaf")
    private List<Journal> journals;
}