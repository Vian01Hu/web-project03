package com.usermanagement.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtils {

   //private static String signKey = "c2VjcmV0S2V5";//签名密钥："secretKey" BASE64编码
    // 生成安全的 HS256 密钥
    private static final SecretKey SIGN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 获取 BASE64 编码的密钥字符串（用于保存或配置）
    private static final String BASE64_KEY = java.util.Base64.getEncoder().encodeToString(SIGN_KEY.getEncoded());
    private static Long expire = 43200000L; // 12小时  令牌有效期（毫秒）12*3600*1000

    /**
     * 生成JWT令牌
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire)) //设置令牌有效期
                .signWith( SIGN_KEY) //指定签名算法和签名密钥
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     */
    public static Claims parseJwt(String jwt) {
        try {
            log.info("BASE64 编码的密钥字符串={}", BASE64_KEY);
            return Jwts.parserBuilder().setSigningKey(SIGN_KEY).build().parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT令牌已过期: {}", e.getMessage());
            throw new RuntimeException("令牌已过期");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的JWT令牌: {}", e.getMessage());
            throw new RuntimeException("不支持的令牌格式");
        } catch (MalformedJwtException e) {
            log.error("JWT令牌格式错误: {}", e.getMessage());
            throw new RuntimeException("令牌格式错误");
        } catch (SignatureException e) {
            log.error("JWT签名验证失败: {}", e.getMessage());
            throw new RuntimeException("令牌签名无效");
        } catch (IllegalArgumentException e) {
            log.error("JWT令牌参数错误: {}", e.getMessage());
            throw new RuntimeException("令牌参数错误");
        }
    }

    /**
     * 验证JWT令牌
     */
    public static boolean validateJwt(String jwt) {
        try {
            parseJwt(jwt);
            return true;
        } catch (Exception e) {
            log.error("JWT令牌验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取 BASE64 编码的密钥（用于保存到配置文件）
     *
     * @return BASE64 编码的密钥字符串
     */
    public static String getBase64Key() {
        return BASE64_KEY;
    }

    /**
     * 从 BASE64 字符串恢复密钥（用于从配置文件加载）
     *
     * @param base64Key BASE64 编码的密钥字符串
     * @return SecretKey 对象
     */
    public static SecretKey getKeyFromBase64(String base64Key) {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(base64Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}