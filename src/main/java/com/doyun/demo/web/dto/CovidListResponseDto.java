package com.doyun.demo.web.dto;

import com.doyun.demo.domain.covid.Covid;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CovidListResponseDto {
    private String country;
    private long count;
    private LocalDateTime modifiedDate;

    public CovidListResponseDto(Covid entity) {
        this.country = entity.getCountryInfo().getCountry();
        this.count = entity.getCount();
        this.modifiedDate = entity.getModifiedDate();
    }

    @Override
    public String toString() {
        return "{" +
                "country=" + country +
                ", count=" + count +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
