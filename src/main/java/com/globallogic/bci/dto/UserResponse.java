package com.globallogic.bci.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;
    private Timestamp created;
    private Timestamp lastLogin;
    private String token;
    private boolean isActive;

}
