package com.kk.captcha.server;

import com.kk.captcha.manager.CaptchaManager;
import com.kk.xoa.captcha.CaptchaService;
import com.kk.xoa.captcha.CodeVerifyRequest;
import com.kk.xoa.captcha.CodeVerifyResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * thrift的服务端
 * 
 * @author kk
 */
public class CaptchaServiceImpl extends ThriftServerImplementor implements CaptchaService.Iface {
	protected static Log logger = LogFactory.getLog(CaptchaServiceImpl.class);

    private CaptchaManager captchaManager;

    public void setCaptchaManager(CaptchaManager captchaManager) {
        this.captchaManager = captchaManager;
    }

    @Override
	public void afterStart() {
	}

	@Override
	protected TProcessor createProcessor() {
//		return new XMonitorProcessor(new CaptchaService.Processor(this));
		return new CaptchaService.Processor(this);
	}

    @Override
    public CodeVerifyResponse verifySecurityCode(CodeVerifyRequest req) throws TException {
        logger.info(req);
        String post = req.getPost();
        String captcha = req.getCaptcha();

        // 解析token。 目前触屏的有一个post参数，模式为 "_PASSWORD_7895642122"
        boolean ret = captchaManager.checkValicode(post, captcha);
        return new CodeVerifyResponse(ret);
    }
}
