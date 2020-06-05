package com.epik.evm.repositories;


import com.epik.evm.catalogs.Brands;
import com.epik.evm.catalogs.Countries;
import com.epik.evm.catalogs.ElectricVehicleType;
import com.epik.evm.domain.ElectricVehicle;
import com.epik.evm.domain.User;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class DataSeedingTest {

    @Autowired
    private ElectricVehicleRepository electricVehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void DataSeeder(){

        Faker faker= new Faker();

        for (int i = 0; i < 600; i++) {
            List<ElectricVehicle> evList= new ArrayList<>();
            User user= new User();
            user.setState(faker.address().state());
            user.setName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setCountry(Countries.values()[faker.random().nextInt(0,9)]);
            this.userRepository.save(user);
            for (int j = 0; j < faker.random().nextInt(0, 20); j++) {
                ElectricVehicle ev= new ElectricVehicle();
                ev.setYear(faker.date().birthday(0,18).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
                ev.setBrand(Brands.values()[faker.random().nextInt(0,12)]);
                ev.setBatteryCapacity(faker.random().nextInt(1,4785544));
                ev.setElectricVehicleType(ElectricVehicleType.values()[faker.random().nextInt(0,2)]);
                ev.setModel(faker.commerce().productName());
                ev.setUser(user);
                this.electricVehicleRepository.save(ev);
            }

        }


    }
}
