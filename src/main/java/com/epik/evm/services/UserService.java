package com.epik.evm.services;

import com.epik.evm.domain.User;
import com.epik.evm.dto.BaseUser;
import com.epik.evm.dto.ReturnUser;
import com.epik.evm.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ReturnUser createUser(BaseUser userDto){
        final User user = modelMapper.map(userDto, User.class);
        //add validation logic if needed

         return modelMapper.map(
                 this.userRepository.save(user),ReturnUser.class);

    }

    public ReturnUser getUserById(long id){
        final Optional<User> user = this.userRepository.findById(id);
        if(user.isEmpty()){
            return null;
        }
        return this.modelMapper.map(user.get(), ReturnUser.class);
    }

}
