package com.doyun.demo.domain.covid;

import com.doyun.demo.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "covid")
public class Covid extends BaseTimeEntity {

    @EmbeddedId
    private CovidPK countryInfo;

    @Column(name = "count", columnDefinition = "INT(11) DEFAULT 0")
    private long count;

    @Builder
    public Covid(CovidPK countryInfo, long count){
        this.countryInfo = countryInfo;
        this.count = count;
    }

    public void update(long count) {
        this.count = count;
    }
}
