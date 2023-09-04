package com.growstory.domain.likes.entity;

import com.growstory.domain.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountLikeId;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
}
