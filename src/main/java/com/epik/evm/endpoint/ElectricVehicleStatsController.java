package com.epik.evm.endpoint;

import com.epik.evm.domain.TopBrands;
import com.epik.evm.domain.TopCountries;
import com.epik.evm.domain.TopYears;
import com.epik.evm.repositories.TopBrandsRepository;
import com.epik.evm.repositories.TopCountriesRepository;
import com.epik.evm.repositories.TopYearsRepository;
import io.swagger.annotations.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/ev/stats")
@Api(value = "ElectricVehicleStatsController", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@SwaggerDefinition(tags = {
        @Tag(name = "Electric Vehicle Statistics", description = "Endpoints that help to retrieve some statistics for the electic vehicle")
})
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
    @ApiOperation(value = "Retrieve the top brands (brands more used by the users)")
    public ResponseEntity<List<TopBrands>> getTopBrands(
           @ApiParam(value = "The number of returned results", defaultValue = "10", name = "Number Of Top Results", required = false) @RequestParam(defaultValue = "10") int top){
        return ResponseEntity.ok(this.topBrandsRepository.findAll(PageRequest.of(0,top)));
    }

    @GetMapping("topcountries")
    @ApiOperation(value = "Retrieve the top countries (Countries with more electric vehicles)")
    public ResponseEntity<List<TopCountries>> getTopCountries(
            @ApiParam(value = "The number of returned results", defaultValue = "10", name = "Number Of Top Results", required = false) @RequestParam(defaultValue = "10") int top){
        return ResponseEntity.ok(this.topCountriesRepository.findAll(PageRequest.of(0,top)));
    }

    @GetMapping("topyears")
    @ApiOperation(value = "Retrieve the top years (Return the years of the cars more used in the app)")
    public ResponseEntity<List<TopYears>> getTopYears(
            @ApiParam(value = "The number of returned results", defaultValue = "10", name = "Number Of Top Results", required = false) @RequestParam(defaultValue = "10") int top){
        return ResponseEntity.ok(this.topYearsRepository.findAll(PageRequest.of(0,top)));
    }
}
