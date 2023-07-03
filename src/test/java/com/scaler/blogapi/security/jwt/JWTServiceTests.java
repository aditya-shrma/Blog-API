package com.scaler.blogapi.security.jwt;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JWTServiceTests {

    private JWTService jwtService=new JWTService();

    @Test
    void canCreateJWT()
    {
        var userId=1122;
        var jwt = jwtService.createJWT(userId, new Date(1677082), new Date(1677687));
        assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTIyIiwiZXhwIjoxNjc3LCJpYXQiOjE2Nzd9.NgL2N7vysBGtPqVSmWABBidY5fiEPKHW0ApzC4OINlc", jwt);
    }

    @Test
    void canGetUserIdFromJWT()
    {
        var jwt="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTIyIiwiZXhwIjoxNjc3LCJpYXQiOjE2Nzd9.NgL2N7vysBGtPqVSmWABBidY5fiEPKHW0ApzC4OINlc";
        var userId = jwtService.getUserIdFromJWT(jwt);
        assertEquals(userId,1122);
    }
}
