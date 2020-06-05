package com.epik.evm.services;

import com.epik.evm.catalogs.Countries;
import com.epik.evm.domain.User;
import com.epik.evm.dto.BaseUser;
import com.epik.evm.dto.ReturnUser;
import com.epik.evm.repositories.UserRepository;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        Mockito.when(this.userRepository.save(any())).thenReturn(this.createUserObj(1L));
        final ReturnUser user = userService.createUser(this.createBaseUser());

        assertThat(user, is(IsNull.notNullValue()));
        assertThat(user.getId(), is(1L));
        assertThat(user.getCountry(), is(Countries.MEXICO));

    }

    @Test
    public void getUserById() {
        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(this.createUserObj(1L)));
        final ReturnUser user = this.userService.getUserById(1);

        assertThat(user, is(IsNull.notNullValue()));
        assertThat(user.getId(), is(1L));
        assertThat(user.getLastName(), is("Herrera"));
    }

    @Test
    public void getUserByIdNotExist() {
        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.empty());
        final ReturnUser user = this.userService.getUserById(1);

        assertThat(user, is(IsNull.nullValue()));
    }

    private BaseUser createBaseUser(){
        BaseUser baseUser = new BaseUser();
        baseUser.setCountry(Countries.MEXICO);
        baseUser.setLastName("Herrera");
        baseUser.setName("Juan Jose");
        baseUser.setState("Aguascalientes");
        return  baseUser;
    }

    private User createUserObj(Long id){
        User baseUser = new User();
        baseUser.setCountry(Countries.MEXICO);
        baseUser.setLastName("Herrera");
        baseUser.setName("Juan Jose");
        baseUser.setState("Aguascalientes");
        baseUser.setId(id);
        return  baseUser;
    }
}