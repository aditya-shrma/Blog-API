package com.scaler.blogapi.users;

import com.scaler.blogapi.security.authtoken.AuthTokenService;
import com.scaler.blogapi.security.jwt.JWTService;
import com.scaler.blogapi.users.dtos.CreateUserDTO;
import com.scaler.blogapi.users.dtos.LoginUserDTO;
import com.scaler.blogapi.users.dtos.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsersService {



    private final UsersRepository usersRepository; // made it final so that it can be initialized in constructor
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    private final AuthTokenService authTokenService;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JWTService jwtService, AuthTokenService authTokenService) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authTokenService = authTokenService;
    }


    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        //TODO: check if username already exists
        //TODO: check if email already exists
        //TODO: Validate email
        var newUserEntity =modelMapper.map(createUserDTO,UserEntity.class);
        newUserEntity.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        var savedUser = usersRepository.save(newUserEntity);
        var userResponseDTO =modelMapper.map(savedUser,UserResponseDTO.class);
        userResponseDTO.setToken(jwtService.createJWT(savedUser.getId()));
        return userResponseDTO;
    }

    public UserResponseDTO loginUser(@RequestBody LoginUserDTO loginUserDTO,TokenType tokenType) {
        var userEntity = usersRepository.findByUsername(loginUserDTO.getUsername());

        if(userEntity==null) {
            throw new UserNotFoundException(loginUserDTO.getUsername());
        }

        var passwordMatches = passwordEncoder.matches(loginUserDTO.getPassword(),userEntity.getPassword());

        if(!passwordMatches) {
            throw new InvalidPasswordException();
        }

        var userResponseDTO =modelMapper.map(userEntity,UserResponseDTO.class);
        switch(tokenType) {
            case JWT:
                userResponseDTO.setToken(jwtService.createJWT(userEntity.getId()));
                break;
            case AUTH_TOKEN:
                userResponseDTO.setToken(authTokenService.createAuthToken(userEntity).toString());
                break;
        }
        return userResponseDTO;
    }

    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("user with username "+username+" not found");
        }

        public UserNotFoundException(Integer id) {
            super("user with ID "+id+" not found");
        }
    }

    public static class InvalidPasswordException extends IllegalArgumentException {
        public InvalidPasswordException() {
            super("Invalid Password");
        }
    }

    static enum TokenType {
        JWT,
        AUTH_TOKEN
    }
}
