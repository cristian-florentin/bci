package com.globallogic.bci.dto;

import com.globallogic.bci.entity.Phone;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
public class LoggedInUserResponse {

    private UUID id;

    private Timestamp created;

    private Timestamp lastLogin;

    private String token;

    private boolean isActive;

    private String name;

    private String email;

    private String password;

    private List<Phone> phones;

}
