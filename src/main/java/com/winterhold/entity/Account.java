package com.winterhold.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "Account")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;
}
