package com.epik.evm.services;

import com.epik.evm.domain.ElectricVehicle;
import com.epik.evm.domain.User;
import com.epik.evm.dto.BaseElectricVehicle;
import com.epik.evm.dto.ReturnElectricVehicle;
import com.epik.evm.repositories.ElectricVehicleRepository;
import com.epik.evm.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectricVehicleService {

    private final ElectricVehicleRepository electricVehicleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper= new ModelMapper();

    public ElectricVehicleService(ElectricVehicleRepository electricVehicleRepository, UserRepository userRepository) {
        this.electricVehicleRepository = electricVehicleRepository;
        this.userRepository = userRepository;
    }

    public ReturnElectricVehicle createEV(long userId,BaseElectricVehicle evDto){
        final ElectricVehicle ev = modelMapper.map(evDto, ElectricVehicle.class);
        final Optional<User> user = this.userRepository.findById(userId);
        if(user.isEmpty()){
            return null;
        }
        ev.setUser(user.get());
        return modelMapper.map(
        this.electricVehicleRepository.save(ev),ReturnElectricVehicle.class);
    }

    public List<ReturnElectricVehicle> getElectricVehiclesByUserId(long id){
        final Optional<User> user = this.userRepository.findById(id);
        if(user.isEmpty()){
            return null;
        }

        return this.electricVehicleRepository.getAllByUserId(id).
                stream()
                .map(ve -> modelMapper.map(ve, ReturnElectricVehicle.class))
                .collect(Collectors.toList());
    }
}
