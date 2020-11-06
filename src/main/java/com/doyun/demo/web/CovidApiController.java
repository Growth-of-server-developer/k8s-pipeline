package com.doyun.demo.web;

import com.doyun.demo.domain.covid.Covid;
import com.doyun.demo.domain.covid.CovidPK;
import com.doyun.demo.service.CovidService;
import com.doyun.demo.web.dto.CovidListResponseDto;
import com.doyun.demo.web.dto.CovidResponseDto;
import com.doyun.demo.web.dto.CovidSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CovidApiController {

    private Logger logger = LoggerFactory.getLogger(CovidApiController.class);

    private final CovidService covidService;

    @PostMapping("/api/v1/covid")
    public Covid save(@RequestBody CovidSaveRequestDto requestDto) {
        logger.info("Request to save: " + requestDto.toString());
        return covidService.save(requestDto);
    }

    @PostMapping("/api/v1/covid/treat")
    public long treat(@RequestBody CovidPK pk) {
        logger.info("Request to treat: " + pk.toString());
        return covidService.update(pk, "down");
    }

    @PostMapping("/api/v1/covid/sick")
    public long sick(@RequestBody CovidPK pk) {
        logger.info("Request to sick: " + pk.toString());
        return covidService.update(pk, "up");
    }

    @GetMapping("/api/v1/covid/{country}")
    public CovidResponseDto findByCountry(@PathVariable String country) {
        logger.info("Request to find country info: " + country);
        return covidService.findByCountry(country);
    }

    @GetMapping("/api/v1/covid")
    public List<CovidListResponseDto> findAll() {
        return covidService.findAll();
    }

    @DeleteMapping("/api/v1/covid")
    public void delete(@RequestBody CovidPK pk) {
        logger.info("Request to delete: " + pk.toString());
        covidService.delete(pk);
    }
}
