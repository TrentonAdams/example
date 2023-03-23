package com.example.db.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class Continent
{
    private String code;
    private String name;
    private List<Country> countries = new ArrayList<>();
    private boolean done;

    public Continent(final String code, final String name){
        this.code = code;
        this.name = name;
    }

    public Continent addCountry(Country country){
        if (!done)
        {
            countries.add(country);
            return this;
        }
        throw new IllegalStateException("this is an immutable object");
    }

    public Continent done()
    {
        done = true;
        return this;
    }

}
