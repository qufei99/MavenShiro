package com.lishen.utils;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

 

    private Cache<String, AtomicInteger> passwordRetryCache;

 

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {

        passwordRetryCache = cacheManager.getCache("passwordRetryCache");

    }

 

    @Override

    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

    	//获取用户名

        String username = (String)token.getPrincipal();

        //从缓存中获取该用户已经输入密码的尝试次数

        AtomicInteger retryCount = passwordRetryCache.get(username);

        if(retryCount == null) {

            retryCount = new AtomicInteger(0);

            //将用户输入密码的尝试次数缓存起来

            passwordRetryCache.put(username, retryCount);

        }

        if(retryCount.incrementAndGet() > 5) {

            //密码输入次数超过五次

            throw new ExcessiveAttemptsException();

        }

        /*

         * token是表单上输入的用户名和密码，info是从数据中查的信息，将作密码比对

         */

        boolean matches = super.doCredentialsMatch(token, info);

        if(matches) {

            //清楚尝试次数

            passwordRetryCache.remove(username);

        }

        return matches;

    }

}
