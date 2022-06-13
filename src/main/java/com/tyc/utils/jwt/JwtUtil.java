package com.tyc.utils.jwt;

import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-06-13 11:44:15
 */
public class JwtUtil {

    public static final long EXPIRE = 1000*60*60*24;
    public static final String SECRET = "dwqdwqaqwd12s12";


    public static String getJwtToken(String id,String nickname){
        String JwtToken = Jwts.builder()
                //JWT头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS2256")
                //设置分类；设置过期时间 一个当前时间，一个加上设置的过期时间常量
                .setSubject("user-c")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //存储用户信息
                .claim("id", id)
                .claim("nickname", nickname)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return JwtToken;
    }


    public static boolean checkToken(String jwtToken){
        if (StringUtils.isEmpty(jwtToken)){
            return false;
        }
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean checkToken(HttpServletRequest request){
        try {
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)){
                return false;
            }
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static Claims getClaimsByJwtToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body;
    }
}
