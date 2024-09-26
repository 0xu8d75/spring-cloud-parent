package com.u8d75.core.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.u8d75.core.common.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * GlobalTokenHolder
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalTokenHolder {

    public static final String DEFAULT_TOKEN_SECRET = "0123456789abcdefghijklmnopqrstuvwsyz"; // default token secret

    public static final Integer DEFAULT_TOKEN_AVAILABLE_TIME_SECOND = 60 * 60 * 24 * 7; // default token valid time，seconds

    /**
     * getAccessToken
     *
     * @param secret   secret
     * @param userId   userId
     * @param userName userName
     * @return token
     */
    public static String getAccessToken(String secret, Long userId, String userName) {
        Date issuedAt = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(issuedAt);
        calendar.add(Calendar.SECOND, DEFAULT_TOKEN_AVAILABLE_TIME_SECOND);
        Date expiresAt = calendar.getTime();
        return JWT.create()
                .withExpiresAt(expiresAt)
                .withIssuedAt(issuedAt)
                .withClaim(Constants.X_USER_ID, userId)
                .withClaim(Constants.X_USER_NAME, userName == null ? "" : userName)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * decodeToken
     *
     * @param token token
     * @return DecodedJWT
     */
    public static DecodedJWT decodeToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            return JWT.decode(token);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * verifyAccessToken
     *
     * @param secret secret
     * @param token  token
     * @return boolean
     */
    public static boolean verifyAccessToken(String secret, DecodedJWT token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}
