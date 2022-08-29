package com.globallogic.bci.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomErrorList {

    private List<CustomErrorResponse> error = new ArrayList<>();

}
