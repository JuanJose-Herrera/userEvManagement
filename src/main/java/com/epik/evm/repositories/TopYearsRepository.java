package com.epik.evm.repositories;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.domain.TopCountries;
import com.epik.evm.domain.TopYears;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TopYearsRepository extends Repository<TopYears, Integer> {

    List<TopYears> findAll(Pageable pageable);

}
