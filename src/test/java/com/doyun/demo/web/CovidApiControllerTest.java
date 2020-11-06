package com.doyun.demo.web;

import com.doyun.demo.domain.covid.Covid;
import com.doyun.demo.domain.covid.CovidPK;
import com.doyun.demo.domain.covid.CovidRepository;
import com.doyun.demo.web.dto.CovidListResponseDto;
import com.doyun.demo.web.dto.CovidResponseDto;
import com.doyun.demo.web.dto.CovidSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CovidApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CovidRepository covidRepository;

    @Test
    public void Register_Covid_Country() {
        CovidPK countryInfo = new CovidPK(1L, "Test");
        CovidSaveRequestDto requestDto = CovidSaveRequestDto.builder()
                .countryInfo(countryInfo)
                .build();

        final String url = "http://localhost:" + port + "/api/v1/covid";

        ResponseEntity<Covid> responseEntity = restTemplate.postForEntity(url, requestDto, Covid.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Test", responseEntity.getBody().getCountryInfo().getCountry());
        assertEquals(1, responseEntity.getBody().getCount());

        covidRepository.deleteById(countryInfo);
    }

    @Test
    public void Update_Covid_Count() {

        Covid saveCovid = covidRepository.save(Covid.builder()
                .countryInfo(new CovidPK(1L, "Test"))
                .count(1)
                .build());

        CovidPK requestCountryInfo = saveCovid.getCountryInfo();

        long expectedCount = 2;
        final String sickUrl = "http://localhost:" + port + "/api/v1/covid/sick";

        HttpEntity<CovidPK> requestUpEntity = new HttpEntity<>(requestCountryInfo);
        ResponseEntity<Long> responseUpEntity = restTemplate.exchange(sickUrl, HttpMethod.POST, requestUpEntity, Long.class);

        assertEquals(HttpStatus.OK, responseUpEntity.getStatusCode());
        assertEquals(expectedCount, responseUpEntity.getBody().longValue());

        expectedCount = 1;
        final String treatUrl = "http://localhost:" + port + "/api/v1/covid/treat";

        HttpEntity<CovidPK> requestDownEntity = new HttpEntity<>(requestCountryInfo);
        ResponseEntity<Long> responseDownEntity = restTemplate.exchange(treatUrl, HttpMethod.POST, requestDownEntity, Long.class);

        assertEquals(HttpStatus.OK, responseDownEntity.getStatusCode());
        assertEquals(expectedCount, responseDownEntity.getBody().longValue());

        covidRepository.deleteById(saveCovid.getCountryInfo());
    }

    @Test
    public void Find_Covid_Country_Info() {
        Covid saveCovid1 = covidRepository.save(Covid.builder()
                .countryInfo(new CovidPK(1L, "Test1"))
                .count(1)
                .build());
        Covid saveCovid2 = covidRepository.save(Covid.builder()
                .countryInfo(new CovidPK(2L, "Test2"))
                .count(1)
                .build());

        CovidPK requestCountryInfo = saveCovid1.getCountryInfo();

        Covid expected = covidRepository.findByCountry(saveCovid1.getCountryInfo().getCountry()).get();
        CovidResponseDto expectedCountryInfo = new CovidResponseDto(expected);


        final String countryInfoUrl = "http://localhost:" + port + "/api/v1/covid/" + saveCovid1.getCountryInfo().getCountry();

        ResponseEntity<CovidResponseDto> responseEntity = restTemplate.getForEntity(countryInfoUrl, CovidResponseDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCountryInfo.getCountry(), responseEntity.getBody().getCountry());
        assertEquals(expectedCountryInfo.getCount(), responseEntity.getBody().getCount());

        final String allCountryInfoUrl = "http://localhost:" + port + "/api/v1/covid";

        ResponseEntity<List> responseEntity2 = restTemplate.getForEntity(allCountryInfoUrl, List.class);

        List<CovidListResponseDto> all = covidRepository.findAll().stream()
                                                        .map(CovidListResponseDto::new)
                                                        .collect(Collectors.toList());

        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
        assertEquals(all.get(0).toString(), responseEntity2.getBody().get(0).toString());

        covidRepository.deleteById(saveCovid1.getCountryInfo());
        covidRepository.deleteById(saveCovid2.getCountryInfo());

    }
}