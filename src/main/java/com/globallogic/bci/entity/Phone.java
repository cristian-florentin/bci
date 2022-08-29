package com.globallogic.bci.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @Column(name = "number")
    private Long number;

    @Column(name = "cityCode")
    private int cityCode;

    @Column(name = "countryCode")
    private String countryCode;

    @ManyToMany(mappedBy = "phones")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

}
