package com.epik.evm.endpoint;

import com.epik.evm.dto.BaseElectricVehicle;
import com.epik.evm.dto.ReturnElectricVehicle;
import com.epik.evm.dto.ReturnUser;
import com.epik.evm.services.ElectricVehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping(value = "/user/{userId}/ve")
public class ElectricVehicleController {

    private ElectricVehicleService electricVehicleService;

    public ElectricVehicleController(ElectricVehicleService electricVehicleService) {
        this.electricVehicleService = electricVehicleService;
    }

    @GetMapping("")
    public ResponseEntity<List<ReturnElectricVehicle>> getListOfEvs(@PathVariable int userId){

        final List<ReturnElectricVehicle> electricVehiclesByUserId = this.electricVehicleService.getElectricVehiclesByUserId(userId);
        if(electricVehiclesByUserId==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(electricVehiclesByUserId);
    }

    @PostMapping
    public ResponseEntity<ReturnElectricVehicle> createEv(@PathVariable Long userId
            , @Valid @RequestBody BaseElectricVehicle evDto
            , UriComponentsBuilder uriBuilder){

        try {

            final ReturnElectricVehicle ev = this.electricVehicleService.createEV(userId, evDto);
            if(ev==null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity
                    .created(
                            uriBuilder
                                    .path("/user/{id}/ve/{evId}")
                                    .buildAndExpand(userId,ev.getId())
                                    .toUri())
                    .body(ev);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
