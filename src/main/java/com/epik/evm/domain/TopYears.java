package com.epik.evm.domain;

import com.epik.evm.catalogs.Countries;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("select year, count(1) from electric_vehicle " +
        "group by year " +
        "order by count(1) desc")
public class TopYears {

    @Id
    private Integer year;

    private int count;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
