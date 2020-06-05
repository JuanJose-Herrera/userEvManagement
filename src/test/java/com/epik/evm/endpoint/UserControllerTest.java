package com.epik.evm.endpoint;

import com.epik.evm.catalogs.Countries;
import com.epik.evm.dto.BaseUser;
import com.epik.evm.dto.ReturnUser;
import com.epik.evm.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void getUser() throws Exception {
        when(service.getUserById(1)).thenReturn(this.createUser(1L));

        this.mockMvc.perform(get("/user/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.lastName").value("Herrera"));

    }

    @Test
    public void getUserNotFound() throws Exception {
        when(service.getUserById(1)).thenReturn(null);

        this.mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void createUser() throws Exception{
        when(service.createUser(any())).thenReturn(this.createUser(1L));

        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(this.createBaseUser())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.lastName").value("Herrera"));
    }

    @Test
    public void createUserValidationError() throws Exception{
        when(service.createUser(any())).thenReturn(this.createUser(1L));
        final BaseUser baseUser = this.createBaseUser();
        baseUser.setName(null);

        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(baseUser)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(""));

    }

    private BaseUser createBaseUser(){
        BaseUser baseUser = new BaseUser();
        baseUser.setCountry(Countries.MEXICO);
        baseUser.setLastName("Herrera");
        baseUser.setName("Juan Jose");
        baseUser.setState("Aguascalientes");
        return  baseUser;
    }

    private ReturnUser createUser(Long id){
        ReturnUser baseUser = new ReturnUser();
        baseUser.setCountry(Countries.MEXICO);
        baseUser.setLastName("Herrera");
        baseUser.setName("Juan Jose");
        baseUser.setState("Aguascalientes");
        baseUser.setId(id);
        return  baseUser;
    }
}