package com.myProject.blogApis.security;

import com.auth0.jwt.impl.ClaimsHolder;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.stereotype.Component;

@Component
public class JWTHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private String secret = "jwtsecretkey";

//    public String getUsernameFromToken(String token){
//        return getClaimFromToken(token, Claim::getClass);
//    }

    
}
