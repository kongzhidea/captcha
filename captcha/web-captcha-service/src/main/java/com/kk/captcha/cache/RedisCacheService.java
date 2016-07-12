package com.kk.captcha.cache;

import com.kk.captcha.service.RedisService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 缓存和校验验证码。 缓存实现为redis
 * 
 * @author jingwei.qi@renren-inc.com, created at 2014-6-11 下午6:06:30
 */
public class RedisCacheService implements CacheService {

    private static final Log logger =
            LogFactory.getLog(RedisCacheService.class);

    private RedisService redisProxy;

    @Override
    public boolean cacheCaptcha(String token, String code) {
        // 需要设置key的超期时间
        redisProxy.setex(PATCHCA_CACHE_PREFIX + token, EXPIRE_TIME, code);
        return true;
    }

    @Override
    public boolean checkCaptcha(String token, String code) {
        if (token == null || "".equals(token) || code == null
            || "".equals(code)) {
            logger.error("error when parse captcha token and submit code, return false!");
            return false;
        }
        String cachedCode = redisProxy.get(PATCHCA_CACHE_PREFIX + token);
        return cachedCode == null ? false : (cachedCode.equalsIgnoreCase(code));
    }

    public void setRedisProxy(RedisService redisProxy) {
        this.redisProxy = redisProxy;
    }

}
