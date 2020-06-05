package com.epik.evm.repositories;


import com.epik.evm.catalogs.Brands;
import com.epik.evm.catalogs.Countries;
import com.epik.evm.domain.ElectricVehicle;
import com.epik.evm.domain.User;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ElectricVehicleTest {

    @Autowired
    private ElectricVehicleRepository electricVehicleRepository;

    @Autowired
    private UserRepository userRepository;

    private  User savedUser;
    @Before
    public  void Before(){
        final User user= new User();
        user.setCountry( Countries.ENGLAND);
        user.setLastName("Herrera");
        user.setName("Juan Jose");
        user.setState("england state");

        this.savedUser = userRepository.save(user);
    }


    @Test
    public void testCrud(){

        ElectricVehicle ev= new ElectricVehicle();
        ev.setUser(this.savedUser);
        ev.setBatteryCapacity(145);
        ev.setBrand(Brands.HONDA);
        ev.setElectricVehicleType(com.epik.evm.catalogs.ElectricVehicleType.HYBRID_NOT_PLUG_IN);
        ev.setYear(2018);
        ev.setModel("volt");

        final ElectricVehicle savedEv = this.electricVehicleRepository.save(ev);

        assertThat(savedEv, is (IsNull.notNullValue()));
        assertThat(savedEv.getId(), is(greaterThan(0L)));
        assertThat(savedEv.getBrand(), is(Brands.HONDA));

        savedEv.setYear(2008);
        final ElectricVehicle updatedEV = this.electricVehicleRepository.save(savedEv);

        assertThat(updatedEV, is (IsNull.notNullValue()));
        assertThat(updatedEV.getId(), is(savedEv.getId()));
        assertThat(updatedEV.getYear(), is(2008));

        this.electricVehicleRepository.deleteById(savedEv.getId());

        final Optional<ElectricVehicle> deletedEv = this.electricVehicleRepository.findById(savedEv.getId());

        assertThat(deletedEv.isEmpty(), is(true));

    }

    @Test(expected = ConstraintViolationException.class)
    public void addWithNulls() {

        ElectricVehicle ev = new ElectricVehicle();
        ev.setUser(this.savedUser);
        ev.setBatteryCapacity(145);
        ev.setBrand(null);
        ev.setElectricVehicleType(com.epik.evm.catalogs.ElectricVehicleType.HYBRID_NOT_PLUG_IN);
        ev.setYear(2018);
        ev.setModel(null);

        final ElectricVehicle savedEv = this.electricVehicleRepository.save(ev);
    }
}
