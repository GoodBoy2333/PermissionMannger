package com.fy.PermissionMannger.Uitls.SecurityUtils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.util.StringUtils;

/**
 * 〈密码加盐处理〉
 *
 * @author fangyan
 * @create 2019/5/8
 * @since 1.0.0
 */
public class ShiroSaltUtil {

    /**
     *
     * @param source 所需加密的参数即密码
     * @param salt [盐] 一般为用户名 或 随机数
     * @param hashIterations 加密次数
     * @return
     */
    public static String getPasswordSalt(String source,String salt,int hashIterations){
        if(StringUtils.isEmpty(source)||StringUtils.isEmpty(salt)){
            return null;
        }
        //调用 org.apache.shiro.crypto.hash.Md5Hash.Md5Hash(Object source, Object salt, int hashIterations)
        //构造方法实现MD5盐值加密
        //Md5Hash mh = new Md5Hash(source, salt, hashIterations);
        //mh.toString()

        /*调用org.apache.shiro.crypto.hash.SimpleHash.SimpleHash(String algorithmName, Object source, Object salt, int hashIterations)
          构造方法实现盐值加密  String algorithmName 为加密算法 支持md5 base64 等*/
        SimpleHash sh = new SimpleHash("MD5", source, salt, hashIterations);
        return sh.toString();
    }
}
