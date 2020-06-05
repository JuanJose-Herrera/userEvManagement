package com.epik.evm.domain;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.catalogs.Countries;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("select country, count(1) from \"user\" " +
        "inner join electric_vehicle ev on \"user\".id = ev.user_id " +
        "group by country " +
        "order by count(1) desc")
public class TopCountries {

    @Id
    private Countries country;

    private int count;

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
