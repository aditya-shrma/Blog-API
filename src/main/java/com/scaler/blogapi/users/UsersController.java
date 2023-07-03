package com.scaler.blogapi.users;

import com.scaler.blogapi.users.dtos.CreateUserDTO;
import com.scaler.blogapi.users.dtos.LoginUserDTO;
import com.scaler.blogapi.users.dtos.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService)
    {
        this.usersService = usersService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        var savedUser = usersService.createUser(createUserDTO);
        return ResponseEntity
                .created(URI.create("/users/"+savedUser.getId()))
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser
            (@RequestBody LoginUserDTO loginUserDTO,
             @RequestParam(name = "token", defaultValue = "jwt") String token
    ){
        // if token==jwt(default) then generate jwt token , if token==auth_token then generate auth token

        var tokenType = UsersService.TokenType.JWT;
        if(token!=null && token.equals("auth_token")) {
            tokenType = UsersService.TokenType.AUTH_TOKEN;
        }
        var savedUser = usersService.loginUser(loginUserDTO,tokenType);
        return ResponseEntity.ok(savedUser);

    }

    @ExceptionHandler(UsersService.UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UsersService.UserNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UsersService.InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(UsersService.InvalidPasswordException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }



}
