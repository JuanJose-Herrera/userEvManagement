package com.epik.evm.endpoint;

import com.epik.evm.dto.BaseUser;
import com.epik.evm.dto.ReturnUser;
import com.epik.evm.services.UserService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController()
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "UserController", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@SwaggerDefinition(tags = {
        @Tag(name = "User", description = "Endpoints that help to create and retrieve users from the system")
})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    @ApiOperation(value = "Get User by userId")
    public ResponseEntity<ReturnUser> getUser(
            @ApiParam(value = "The user id retrieved when is created", name = "User Id", required = true, example = "1")
            @PathVariable int userId){

        final ReturnUser userById = this.userService.getUserById(userId);
        if(userById==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userById);
    }

    @PostMapping()
    @ApiOperation(value = "Create a new user resource")
    public ResponseEntity<ReturnUser> createUser(
            @ApiParam(required = true, name = "User", value = "The user resource to be created") @Valid @RequestBody BaseUser userDto,
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
