package com.kk.captcha.manager;

import com.kk.captcha.pojo.AbstractCaptcha;

/**
 * author:dapeng.zhou@renren-inc.com <br/>
 * date:2014-6-10 下午2:35:33
 */
public interface CaptchaManager {

    /**
     * 生成并缓存验证码
     * 
     * @param token
     * @return
     */
    AbstractCaptcha generateAndCacheCaptcha(String token);

    /**
     * 缓存验证码
     * 
     * @param token
     * @param code
     * @return
     */
    boolean cacheCaptcha(String token, String code);

    /**
     * 校验验证码
     * 
     * @param token
     * @param valicode
     * @return
     */
    boolean checkValicode(String token, String valicode);
}
