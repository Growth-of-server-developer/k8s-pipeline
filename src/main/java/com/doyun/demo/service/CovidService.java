package com.doyun.demo.service;

import com.doyun.demo.domain.covid.Covid;
import com.doyun.demo.domain.covid.CovidPK;
import com.doyun.demo.domain.covid.CovidRepository;
import com.doyun.demo.web.dto.CovidListResponseDto;
import com.doyun.demo.web.dto.CovidResponseDto;
import com.doyun.demo.web.dto.CovidSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CovidService {
    private final CovidRepository covidRepository;

    private static final String THERE_IS_NO_COUNTRY = "There is no country.";

    @Transactional
    public Covid save(CovidSaveRequestDto requestDto){
        return covidRepository.save(requestDto.toEntity());
    }

    @Transactional
    public long update(CovidPK pk, String flag) {
        Covid covid = covidRepository.findById(pk)
                .orElseThrow(() -> new IllegalArgumentException(THERE_IS_NO_COUNTRY));
        long currentCount = covid.getCount();
        int num = -1;
        if ("up".equals(flag)) num = 1;
        else if (currentCount <= 0) num = 0;
        long updateCount = currentCount + num;
        covid.update(updateCount);
        return updateCount;
    }


    @Transactional
    public List<CovidListResponseDto> findAll() {
        return covidRepository.findAll().stream()
                .map(CovidListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(CovidPK pk) {
        Covid covid = covidRepository.findById(pk)
                .orElseThrow(() -> new IllegalArgumentException(THERE_IS_NO_COUNTRY));
        covidRepository.delete(covid);
    }

    public CovidResponseDto findByCountry (String country) {
        Optional<Covid> covid = Optional.ofNullable(covidRepository.findByCountry(country)
                .orElseThrow(() -> new IllegalArgumentException(THERE_IS_NO_COUNTRY)));

        return new CovidResponseDto(covid.get());
    }


}
