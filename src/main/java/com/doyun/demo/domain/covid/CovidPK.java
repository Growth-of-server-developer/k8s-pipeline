package com.doyun.demo.domain.covid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class CovidPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id", nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", country=" + country +
                "}";
    }
}
