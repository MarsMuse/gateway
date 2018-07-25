package com.cttc.auth.util;

import com.cttc.auth.constant.TokenConstant;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author xz man
 * @date 2018/7/16 下午2:40
 *
 *
 */
public final class TokenHandler {


    /**
     * 终态类，无对象
     */
    private TokenHandler(){
        throw new IllegalStateException("No instances!");
    }

    public static Map<String, Object> parseToken(String token, String base64Secret)throws Exception{

        return Jwts.parser().setSigningKey(DatatypeConverter.
                        parseBase64Binary(base64Secret)).parseClaimsJws(token).getBody();

    }
    /**
     *
     * @author xz man
     * @date 2018/7/16 下午3:18
     * 构建一个令牌
     *
     */
    public static String buildToken(String algName, String base64Secret,  Map<String, Object> payload)throws Exception{
        //转换签名算法
        SignatureAlgorithm algorithm = convertAlgorithm(algName);

        //Token 构造器
        JwtBuilder  tokenBuilder = Jwts.builder().
                setHeaderParam("typ",TokenConstant.JWT.name()).
                signWith(algorithm,DatatypeConverter.parseBase64Binary(base64Secret)).
                setNotBefore(new Date(System.currentTimeMillis()));
        //如果存在自定义的负载，则在此配置
        if(payload != null && !payload.isEmpty()){
            tokenBuilder.setClaims(payload);
        }

        return tokenBuilder.compact();
    }

    public static String buildToken(String algName, String base64Secret)throws Exception{
        return buildToken(algName, base64Secret, null);
    }

    /**
     *
     * @author zou yao
     * @date 2018/7/16 下午3:21
     * @return io.jsonwebtoken.SignatureAlgorithm
     * 根据算法签名名称获取到算法
     *
     */
    private static SignatureAlgorithm convertAlgorithm(String algName){
        if(algName == null || "".equals(algName)){
            throw new IllegalArgumentException("转换q=签名算法参数不可为空");
        }
        //声明算法
        SignatureAlgorithm algorithm;
        //根据名称构建算法
        if(TokenConstant.HS256.name().equals(algName)){
            algorithm = SignatureAlgorithm.HS256;

        }else if(TokenConstant.HS384.name().equals(algName)){
            algorithm = SignatureAlgorithm.HS384;

        }else if(TokenConstant.HS512.name().equals(algName)){
            algorithm = SignatureAlgorithm.HS512;

        }else {
            throw new IllegalArgumentException("转换签名算法时算法名称不合法");
        }

        return algorithm;
    }
}
