package com.epik.evm.endpoint;

import com.epik.evm.domain.TopBrands;
import com.epik.evm.domain.TopCountries;
import com.epik.evm.domain.TopYears;
import com.epik.evm.repositories.TopBrandsRepository;
import com.epik.evm.repositories.TopCountriesRepository;
import com.epik.evm.repositories.TopYearsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/ev/stats")
public class ElectricVehicleStatsController {

    private TopBrandsRepository topBrandsRepository;
    private TopCountriesRepository topCountriesRepository;
    private TopYearsRepository topYearsRepository;


    public ElectricVehicleStatsController(TopBrandsRepository topBrandsRepository, TopCountriesRepository topCountriesRepository, TopYearsRepository topYearsRepository) {
        this.topBrandsRepository = topBrandsRepository;
        this.topCountriesRepository = topCountriesRepository;
        this.topYearsRepository = topYearsRepository;
    }

    @GetMapping("topbrands")
    public ResponseEntity<List<TopBrands>> getTopBrands(@RequestParam(defaultValue = "10") int top){
        return ResponseEntity.ok(this.topBrandsRepository.findAll(PageRequest.of(0,top)));
    }

    @GetMapping("topcountries")
    public ResponseEntity<List<TopCountries>> getTopCountries(@RequestParam(defaultValue = "10") int top){
        return ResponseEntity.ok(this.topCountriesRepository.findAll(PageRequest.of(0,top)));
    }

    @GetMapping("topyears")
    public ResponseEntity<List<TopYears>> getTopYears(@RequestParam(defaultValue = "10") int top){
        return ResponseEntity.ok(this.topYearsRepository.findAll(PageRequest.of(0,top)));
    }
}
