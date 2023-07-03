package com.scaler.blogapi.security.authtoken;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AuthTokenAuthenticationFilter extends AuthenticationFilter {
    public AuthTokenAuthenticationFilter(AuthTokenService authTokenService) {
        super(
                new AuthtokenAuthenticationManager(authTokenService),
                new AuthtokenAuthenticationConverter()
        );

        setSuccessHandler((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    }

    static class AuthtokenAuthenticationConverter implements AuthenticationConverter {
        @Override
        public Authentication convert(HttpServletRequest request) {
            if(request.getHeader("X-Auth-Token") != null)
            {
                String token = request.getHeader("X-Auth-Token");
                return new AuthTokenAuthentication(token);
            }
            return null;
        }
    }

    static class AuthtokenAuthenticationManager implements AuthenticationManager {

        private final AuthTokenService authTokenService;

        public AuthtokenAuthenticationManager(AuthTokenService authTokenService) {
            this.authTokenService = authTokenService;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            if(authentication instanceof AuthTokenAuthentication)
            {
                AuthTokenAuthentication authTokenAuthentication =(AuthTokenAuthentication) authentication;
                UUID authToken=UUID.fromString(authTokenAuthentication.getCredentials());

                    var userId=authTokenService.getUserIdFromAuthToken(authToken);
                    authTokenAuthentication.setUserId(userId);

                    return authTokenAuthentication;
            }
            return null;
        }
    }
}
