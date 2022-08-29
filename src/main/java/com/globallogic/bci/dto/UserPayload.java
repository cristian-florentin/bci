package com.globallogic.bci.dto;

import com.globallogic.bci.entity.Phone;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserPayload {

    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=(?:.*[A-Z].*){1})(?!(?:.*[A-Z].*){2,})(?=(?:.*\\d.*){2})(?!(?:.*\\d.*){3,}).*$")
    private String password;

    private List<Phone> phones = new ArrayList<>();

}
