package com.epik.evm.services;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.catalogs.ElectricVehicleType;
import com.epik.evm.domain.ElectricVehicle;
import com.epik.evm.domain.User;
import com.epik.evm.dto.BaseElectricVehicle;
import com.epik.evm.dto.ReturnElectricVehicle;
import com.epik.evm.repositories.ElectricVehicleRepository;
import com.epik.evm.repositories.UserRepository;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElectricVehicleServiceTest {



    @MockBean
    private ElectricVehicleRepository electricVehicleRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ElectricVehicleService electricVehicleService;

    @Test
    public void creatWithUserIdIncorrect(){

        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        final ReturnElectricVehicle ev = this.electricVehicleService.createEV(1, this.createBaseElectricVehicle());
        assertThat(ev, is(IsNull.nullValue()));
    }

    @Test
    public void createWithoutErrors(){
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(this.createUser(1L)));
        Mockito.when(electricVehicleRepository.save(any())).thenReturn(this.createElectricVehicle(1L));

        final ReturnElectricVehicle ev = this.electricVehicleService.createEV(1L, this.createBaseElectricVehicle());
        assertThat(ev, is(IsNull.notNullValue()));
        assertThat(ev.getId(), is(1L));
        assertThat(ev.getBrand(), is(Brands.NISSAN));

    }
    @Test
    public void getWithWrongUser(){
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        final List<ReturnElectricVehicle> electricVehiclesByUserId = this.electricVehicleService.getElectricVehiclesByUserId(1);

        assertThat(electricVehiclesByUserId, is(IsNull.nullValue()));
    }

    @Test
    public void getWithEmptyVehicles(){
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(this.createUser(1L)));
        Mockito.when(electricVehicleRepository.getAllByUserId(anyLong())).thenReturn(new ArrayList<>());
        final List<ReturnElectricVehicle> electricVehiclesByUserId = this.electricVehicleService.getElectricVehiclesByUserId(1);

        assertThat(electricVehiclesByUserId, is(IsNull.notNullValue()));
        assertThat(electricVehiclesByUserId.isEmpty(), is(true));
    }

    @Test
    public void getWithNotEmptyVehicles(){
        List<ElectricVehicle> list =  new ArrayList<>();
        list.add(this.createElectricVehicle(1L));
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(this.createUser(1L)));
        Mockito.when(electricVehicleRepository.getAllByUserId(anyLong())).thenReturn(list);
        final List<ReturnElectricVehicle> electricVehiclesByUserId = this.electricVehicleService.getElectricVehiclesByUserId(1);

        assertThat(electricVehiclesByUserId, is(IsNull.notNullValue()));
        assertThat(electricVehiclesByUserId.isEmpty(), is(false));
        assertThat(electricVehiclesByUserId.size(), is(1));
        assertThat(electricVehiclesByUserId.get(0).getBrand(), is(Brands.NISSAN));
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

    private ElectricVehicle createElectricVehicle(Long id){
        ElectricVehicle ev= new ElectricVehicle();
        ev.setBatteryCapacity(500000);
        ev.setBrand(Brands.NISSAN);
        ev.setElectricVehicleType(ElectricVehicleType.HYBRID_PLUG_IN);
        ev.setModel("Leaf");
        ev.setYear(2017);
        ev.setId(id);
        return ev;
    }

    public User createUser(Long userId){
        User user= new User();
        user.setId(userId);
        return user;
    }

}