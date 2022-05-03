package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.dto.UserDto.*;
import Wooaham.wooaham_server.utils.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Service
public class JwtService {

    /*
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(Long userIdx, UserType role) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("ACCESS-TOKEN");
    }

    /*
    JWT에서 userIdx, role 추출
    @return UserInfo
    @throws BaseException
     */
    public UserInfo getUserInfo() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(ErrorCode.EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(ErrorCode.INVALID_JWT);
        }

        // 3. userIdx 추출
        return new UserInfo(
                claims.getBody().get("userIdx", Long.class),
                claims.getBody().get("role", String.class)
        );
    }
}
