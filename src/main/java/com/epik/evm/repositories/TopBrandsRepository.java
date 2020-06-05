package com.epik.evm.repositories;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.domain.TopBrands;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;


import java.util.List;

public interface TopBrandsRepository extends Repository<TopBrands, Brands> {

    List<TopBrands> findAll(Pageable pageable);

}
