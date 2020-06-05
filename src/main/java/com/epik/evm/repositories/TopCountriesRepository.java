package com.epik.evm.repositories;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.domain.TopBrands;
import com.epik.evm.domain.TopCountries;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TopCountriesRepository extends Repository<TopCountries, Brands> {

    List<TopCountries> findAll(Pageable pageable);

}
