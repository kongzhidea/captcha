package com.kk.captcha.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kk.captcha.manager.CaptchaManager;
import com.kk.captcha.pojo.AbstractCaptcha;
import com.kk.captcha.pojo.RRToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perf4j.log4j.Log4JStopWatch;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 生成验证码
 * @author zhihui.kong@renren-inc.com
 *
 */
public class CaptchaServlet extends HttpServlet {

	private static final Log logger = LogFactory.getLog(CaptchaServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		CaptchaManager captchaManager = context.getBean("captchaManager", CaptchaManager.class);

		response.setContentType("image/png");
		response.setHeader("cache", "no-cache");
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
            RRToken rrToken = new RRToken(request);
            Log4JStopWatch stopWatch = new Log4JStopWatch();

			if (rrToken.isValid()) {
				AbstractCaptcha rrCaptcha = captchaManager
						.generateAndCacheCaptcha(rrToken.getToken());
				ImageIO.write((BufferedImage) rrCaptcha.getImage(), "png",
						outputStream);
			}
			outputStream.flush();
            stopWatch.stop(request.getParameter("source"));
		} catch (Exception e) {
			logger.error("error when get captcha image.", e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("error when close outputStream.", e);
				}
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
