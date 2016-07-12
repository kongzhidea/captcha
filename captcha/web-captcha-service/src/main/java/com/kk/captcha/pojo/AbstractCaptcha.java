package com.kk.captcha.pojo;

import java.awt.Image;

public abstract class AbstractCaptcha {
	
	protected String code;
	protected Image image;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	
	

}
