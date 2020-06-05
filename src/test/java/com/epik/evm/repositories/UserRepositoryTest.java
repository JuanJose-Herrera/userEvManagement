package com.epik.evm.repositories;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.catalogs.Countries;
import com.epik.evm.catalogs.ElectricVehicleType;
import com.epik.evm.domain.ElectricVehicle;
import com.epik.evm.domain.User;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;


import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElectricVehicleRepository electricVehicleRepository;



    @Test
    @Rollback(value = false)
    public void testCrud(){
        final User user= new User();
        user.setCountry(Countries.ENGLAND);
        user.setLastName("Herrera");
        user.setName("Juan Jose");
        user.setState("england state");

        final User savedUser = this.userRepository.save(user);
        assertThat(savedUser, is(IsNull.notNullValue()));
        assertThat(savedUser.getId(), is(greaterThan(0L)));

        ElectricVehicle ev= new ElectricVehicle();

        ev.setBatteryCapacity(145);
        ev.setBrand(Brands.HONDA);
        ev.setElectricVehicleType(ElectricVehicleType.HYBRID_NOT_PLUG_IN);
        ev.setYear(2017);
        ev.setModel("volt");
        ev.setUser(savedUser);

        electricVehicleRepository.save(ev);





        savedUser.setState("another england state");
        savedUser.setName("Juan José");
        ev= new ElectricVehicle();

        ev.setBatteryCapacity(4445);
        ev.setBrand(Brands.RENAULT);
        ev.setElectricVehicleType(ElectricVehicleType.ELECTRIC);
        ev.setYear(2019);
        ev.setModel("Model 3");
        ev.setUser(savedUser);
        electricVehicleRepository.save(ev);
        final User updatedUser = this.userRepository.save(savedUser);

        assertThat(updatedUser, is(IsNull.notNullValue()));
        assertThat(updatedUser.getId(), is(savedUser.getId()));
        assertThat(updatedUser.getState(), is("another england state"));
        assertThat(updatedUser.getName(), is("Juan José"));

    }
}
