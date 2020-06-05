package com.epik.evm.endpoint;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.catalogs.ElectricVehicleType;
import com.epik.evm.dto.BaseElectricVehicle;
import com.epik.evm.dto.ReturnElectricVehicle;
import com.epik.evm.services.ElectricVehicleService;
import com.epik.evm.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ElectricVehicleController.class)
public class ElectricVehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElectricVehicleService service;

    @Test
    public void getListOfEvs() throws Exception {
        List<ReturnElectricVehicle> vehicleList = new ArrayList<>();
        vehicleList.add(this.createReturnElectricVehicle(1L));
        when(service.getElectricVehiclesByUserId(1L)).thenReturn(vehicleList);

        this.mockMvc.perform(get("/user/1/ev")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].model").value("Leaf"));
    }

    @Test
    public void getListOfEvsUserNotFound() throws Exception {
        List<ReturnElectricVehicle> vehicleList = new ArrayList<>();
        vehicleList.add(this.createReturnElectricVehicle(1L));
        when(service.getElectricVehiclesByUserId(1L)).thenReturn(vehicleList);
        when(service.getElectricVehiclesByUserId(2L)).thenReturn(null);

        this.mockMvc.perform(get("/user/2/ev")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createEv() throws Exception {
        when(service.createEV(anyLong(),any())).thenReturn(this.createReturnElectricVehicle(1L));

        this.mockMvc.perform(post("/user/1/ev").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(this.createBaseElectricVehicle())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.model").value("Leaf"));
    }

    @Test
    public void createEvWithErrors() throws Exception {
        when(service.createEV(anyLong(),any())).thenReturn(this.createReturnElectricVehicle(1L));
        final BaseElectricVehicle baseElectricVehicle = this.createBaseElectricVehicle();
        baseElectricVehicle.setModel(null);
        this.mockMvc.perform(post("/user/1/ev").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(baseElectricVehicle)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    private ReturnElectricVehicle createReturnElectricVehicle(Long id){
        ReturnElectricVehicle rev= new ReturnElectricVehicle();
        rev.setBatteryCapacity(500000);
        rev.setBrand(Brands.NISSAN);
        rev.setElectricVehicleType(ElectricVehicleType.HYBRID_PLUG_IN);
        rev.setModel("Leaf");
        rev.setYear(2017);
        rev.setId(id);
        return rev;
    }

    private BaseElectricVehicle createBaseElectricVehicle(){
        BaseElectricVehicle baseElectricVehicle = new BaseElectricVehicle();
        baseElectricVehicle.setBatteryCapacity(500000);
        baseElectricVehicle.setBrand(Brands.NISSAN);
        baseElectricVehicle.setElectricVehicleType(ElectricVehicleType.HYBRID_PLUG_IN);
        baseElectricVehicle.setModel("Leaf");
        baseElectricVehicle.setYear(2017);
        return baseElectricVehicle;
    }
}