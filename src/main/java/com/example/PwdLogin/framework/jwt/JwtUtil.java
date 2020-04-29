package com.example.PwdLogin.framework.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.PwdLogin.common.constant.JwtConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    public static String sign(String email) {
        Date date = new Date(System.currentTimeMillis() + JwtConstant.TOKEN_EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(JwtConstant.JWT_SECRET);
        return JWT.create()
                .withClaim("email", email)
                .withExpiresAt(date)
                .sign(algorithm);
    }
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtConstant.JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("email", getEmail())
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
    public static String getEmail() {
        String token = getRequest().getHeader(JwtConstant.TOKEN_HEADER_NAME);
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("email").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
