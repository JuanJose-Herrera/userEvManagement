package com.epik.evm.endpoint;

import com.epik.evm.dto.BaseUser;
import com.epik.evm.dto.ReturnUser;
import com.epik.evm.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController()
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<ReturnUser> getUser(@PathVariable int userId){

        final ReturnUser userById = this.userService.getUserById(userId);
        if(userById==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userById);
    }

    @PostMapping()
    public ResponseEntity<ReturnUser> createUser(@Valid @RequestBody BaseUser userDto,
                                                 UriComponentsBuilder uriBuilder){
        try{

            if(null == userDto){
                return ResponseEntity.badRequest().build();
            }
            final ReturnUser user = this.userService.createUser(userDto);
            return ResponseEntity
                    .created(
                            uriBuilder
                                    .path("/user/{id}")
                                    .buildAndExpand(user.getId())
                                    .toUri())
                    .body(user);


        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
