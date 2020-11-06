package com.doyun.demo.web.dto;

import com.doyun.demo.domain.covid.Covid;
import com.doyun.demo.domain.covid.CovidPK;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CovidSaveRequestDto {
    private CovidPK countryInfo;
    private long count = 1;

    @Builder
    public CovidSaveRequestDto(CovidPK countryInfo) {
        this.countryInfo = countryInfo;
    }

    public Covid toEntity() {
        return Covid.builder()
                .countryInfo(countryInfo)
                .count(count)
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "countryInfo=" +
                    "{" +
                        "id=" + countryInfo.getId() +
                        ", country=" + countryInfo.getCountry() +
                    "}" +
                ", count=" + count +
                '}';
    }
}
