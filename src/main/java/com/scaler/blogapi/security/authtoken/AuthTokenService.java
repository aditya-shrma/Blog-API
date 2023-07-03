package com.scaler.blogapi.security.authtoken;

import com.scaler.blogapi.users.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    public AuthTokenService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    public UUID createAuthToken(UserEntity userEntity)
    {
        AuthTokenEntity authTokenEntity = new AuthTokenEntity();
        authTokenEntity.setUser(userEntity);
        var savedAuthToken = authTokenRepository.save(authTokenEntity);
        return savedAuthToken.getId();
    }

    public Integer getUserIdFromAuthToken(UUID authTokenId)
    {
        var savedAuthToken = authTokenRepository.findById(authTokenId)
                             .orElseThrow(() -> new BadCredentialsException("Invalid Auth Token"));
        Integer userId = savedAuthToken.getUser().getId();
        return userId;
    }
}
