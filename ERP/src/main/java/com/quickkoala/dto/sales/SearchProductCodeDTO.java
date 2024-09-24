package com.quickkoala.dto.sales;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProductCodeDTO {
    private String code;
    private String name;

    public SearchProductCodeDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters and Setters
}
