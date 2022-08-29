package com.globallogic.bci.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_phone",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "phone_id") }
    )
    private List<Phone> phones = new ArrayList<>();

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "is_active")
    private boolean isActive;

}
