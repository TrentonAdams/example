package com.example.db.components;

import com.example.db.model.Continent;

import java.util.List;

public interface CountryProvider
{
    List<Continent> queryContinentsByCountryCode(List<String> codes);
}
