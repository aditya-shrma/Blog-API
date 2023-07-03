package com.scaler.blogapi.users;

import com.scaler.blogapi.security.authtoken.AuthTokenRepository;
import com.scaler.blogapi.security.authtoken.AuthTokenService;
import com.scaler.blogapi.security.jwt.JWTService;
import com.scaler.blogapi.users.dtos.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UsersServiceTests {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    private UsersService usersService;

    private UsersService getUserService() {
        if (usersService == null) {
            var modelMapper = new ModelMapper();
            var passwordEncoder = new BCryptPasswordEncoder();
            var jwtService = new JWTService();
            var authTokenService = new AuthTokenService(authTokenRepository);
            usersService = new UsersService(usersRepository, modelMapper,passwordEncoder,jwtService, authTokenService);
        }
        return usersService;
    }

    @Test
    public void testCreateUser() {
        var newUserDTO = new CreateUserDTO();

        newUserDTO.setUsername("testuser");
        newUserDTO.setPassword("testpassword");
        newUserDTO.setEmail("test@gmail.com");
        var savedUser = getUserService().createUser(newUserDTO);

        assertNotNull(savedUser);

    }

}
