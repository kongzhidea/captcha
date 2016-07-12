package com.kk.captcha.cache;

import java.util.concurrent.TimeUnit;

public interface CacheService {

    String PATCHCA_CACHE_PREFIX = "PATCHCA_CACHE_PREFIX_";

    int EXPIRE_TIME = (int) TimeUnit.MINUTES.toSeconds(5);

    boolean cacheCaptcha(String token, String code);

    boolean checkCaptcha(String token, String code);

}
