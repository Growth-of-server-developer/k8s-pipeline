package com.doyun.demo.web.dto;

import com.doyun.demo.domain.covid.Covid;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CovidResponseDto {
    private final String country;
    private final long count;

    public CovidResponseDto(Covid entity) {
        this.country = entity.getCountryInfo().getCountry();
        this.count = entity.getCount();
    }
}
