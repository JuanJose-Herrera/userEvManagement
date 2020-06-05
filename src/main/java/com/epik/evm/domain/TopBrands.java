package com.epik.evm.domain;

import com.epik.evm.catalogs.Brands;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("select brand, count(1) from electric_vehicle " +
        "group by brand " +
        "order by count(1) desc")
public class TopBrands {

    @Id
    private Brands brand;

    private int count;

    public Brands getBrand() {
        return brand;
    }

    public void setBrand(Brands brand) {
        this.brand = brand;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
