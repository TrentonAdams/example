package com.example.takehome.model;

import lombok.Data;

@Data
public class Country
{
    private String code;
    private String description;

    public Country(final String code, final String description)
    {
        this.code = code;
        this.description = description;
    }

}
