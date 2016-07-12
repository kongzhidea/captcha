package com.kk.captcha.pojo;


import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletRequest;

/**
 * 从requst中得到token
 * 
 * @author zhihui.kong@renren-inc.com
 * 
 */
public class RRToken {

    protected String token;

    public boolean isValid(){

        if (token==null || "".equals(token)) {
            return false;
        }
        return true;
    }

    public String getToken() {
        return token;
    }

	public RRToken() {
	}

	public RRToken(String tokenStr) {
		token = tokenStr;
	}

	public RRToken(ServletRequest req) {
		// 解析token,参数为post。 目前触屏的有一个post参数，模式为 "_PASSWORD_7895642122"
		String param = req.getParameter("post");
		if (param != null) {
            String[] str = StringUtils.split(param,"_");

            token = str[0].replace("<","");
            token = token.replace(">", "");
		}
	}

}
