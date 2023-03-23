package com.example.db.model;

import lombok.Data;

@Data
public class Country
{
    public String code;
    public String name;

    public Country(final String code, final String name)
    {
        this.code = code;
        this.name = name;
    }
}
