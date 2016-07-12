package com.kk.captcha.manager;

import com.kk.captcha.cache.CacheService;
import com.kk.captcha.pojo.AbstractCaptcha;
import com.kk.captcha.util.CaptchaUtil;

public class CaptchaManagerImpl implements CaptchaManager {

	private CacheService cacheService;

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	/**
	 * 校验验证码
	 */
	@Override
	public boolean checkValicode(String token, String valicode) {
		return cacheService.checkCaptcha(token, valicode);
	}

	/**
	 * 生成验证码，并且保存到缓存中
	 */
	@Override
	public AbstractCaptcha generateAndCacheCaptcha(String token) {
		// 生成验证码
		AbstractCaptcha captcha = CaptchaUtil.generateCaptcha();
		// 缓存验证码
		cacheService.cacheCaptcha(token, captcha.getCode());
		return captcha;
	}

	@Override
	public boolean cacheCaptcha(String token, String code) {
		// 缓存验证码
		cacheService.cacheCaptcha(token, code);
		return false;
	}

}
