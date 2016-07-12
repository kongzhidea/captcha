package com.kk.captcha.service;


import com.kk.captcha.manager.CaptchaManager;
import com.kk.captcha.pojo.RRToken;

/**
 * author:dapeng.zhou@renren-inc.com <br/>
 * date:2014-6-10 下午2:24:00
 */
public class CaptchaServiceImpl {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private CaptchaManager captchaManager;

	public boolean checkValicode(String token, String valicode) {
        RRToken rrToken = new RRToken(token);
		if (!rrToken.isValid()) {
			return false;
		}
		return captchaManager.checkValicode(rrToken.getToken(), valicode);
	}

	public void setCaptchaManager(CaptchaManager captchaManager) {
		this.captchaManager = captchaManager;
	}

}
