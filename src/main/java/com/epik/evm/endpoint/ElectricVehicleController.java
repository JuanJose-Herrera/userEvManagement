package com.epik.evm.endpoint;

import com.epik.evm.dto.BaseElectricVehicle;
import com.epik.evm.dto.ReturnElectricVehicle;
import com.epik.evm.services.ElectricVehicleService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping(value = "/user/{userId}/ev", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "ElectricVehicleController", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@SwaggerDefinition(tags = {
        @Tag(name = "Electric Vehicle", description = "Endpoints that help to add an retrieve electric vehicles from an specific user")
})
public class ElectricVehicleController {

    private final  ElectricVehicleService electricVehicleService;

    public ElectricVehicleController(ElectricVehicleService electricVehicleService) {
        this.electricVehicleService = electricVehicleService;
    }

    @GetMapping("")
    @ApiOperation(value = "Retrieve the list of electric vehicles for an specific user")
    @ApiResponses( value ={
            @ApiResponse(code = 404, message = "The user id was not found")
    })
    public ResponseEntity<List<ReturnElectricVehicle>> getListOfEvs(
            @ApiParam(value = "The user id", required = true, name = "User Id", example = "1") @PathVariable int userId){

        final List<ReturnElectricVehicle> electricVehiclesByUserId = this.electricVehicleService.getElectricVehiclesByUserId(userId);
        if(electricVehiclesByUserId==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(electricVehiclesByUserId);
    }

    @PostMapping
    @ApiOperation(value = "Add an electric vehicle to a user")
    @ApiResponses( value ={
            @ApiResponse(code = 404, message = "The user id was not found")
    })
    public ResponseEntity<ReturnElectricVehicle> createEv(
            @ApiParam(value = "The user id", required = true, name = "User Id", example = "1") @PathVariable Long userId
            ,@ApiParam(value = "The Electric Vehicle", required = true, name = "Electric Vehicle") @Valid @RequestBody BaseElectricVehicle evDto
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
