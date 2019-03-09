package com.lishen.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.lishen.pojo.SysUser;

public class PasswordHelper {

 

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "md5";

    private int hashIterations = 2;

 

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {

        this.randomNumberGenerator = randomNumberGenerator;

    }

 

    public void setAlgorithmName(String algorithmName) {

        this.algorithmName = algorithmName;

    }

 

    public void setHashIterations(int hashIterations) {

        this.hashIterations = hashIterations;

    }

    

	/**

	 * 加密密码

	 */

    public SysUser encryptPassword(SysUser sysUser) {

    	//设置随机salt

        sysUser.setSalt(randomNumberGenerator.nextBytes().toHex());

        //密码明文+随机salt=密码密文         

        String newPassword = new SimpleHash(

                algorithmName,

                sysUser.getPassword(),

                //credentialsSalt=username+salt

                ByteSource.Util.bytes(sysUser.getCredentialsSalt()),

                hashIterations).toHex();

        //设置密码密文

        sysUser.setPassword(newPassword);

        

        return sysUser;

    }

    

   

}
