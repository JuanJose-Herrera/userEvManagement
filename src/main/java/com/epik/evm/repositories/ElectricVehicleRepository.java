package com.epik.evm.repositories;

import com.epik.evm.domain.ElectricVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectricVehicleRepository extends JpaRepository<ElectricVehicle,Long> {

    List<ElectricVehicle> getAllByUserId(long userId);

}
