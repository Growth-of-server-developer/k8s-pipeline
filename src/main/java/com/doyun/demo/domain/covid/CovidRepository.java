package com.doyun.demo.domain.covid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CovidRepository extends JpaRepository<Covid, CovidPK> {

    @Query("SELECT c FROM Covid c WHERE c.countryInfo.country = ?1")
    Optional<Covid> findByCountry(String country);
}
