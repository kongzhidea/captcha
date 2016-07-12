package com.kk.captcha.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.kk.captcha.pojo.RRCaptcha;
import com.kk.captcha.pojo.AbstractCaptcha;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.patchca.background.BackgroundFactory;
import org.patchca.color.RandomColorFactory;
import org.patchca.filter.AbstractFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.word.RandomWordFactory;

/**
 * 生成验证码并以图片的形式输出。
 * 
 * @author 
 */
public class CaptchaUtil {

    private static final Log logger = LogFactory.getLog(CaptchaUtil.class);
    private static ConfigurableCaptchaService configurableCaptchaService = null;
    private static RandomColorFactory colorFactory = null;
    private static RandomFontFactory fontFactory = null;
    private static RandomWordFactory wordFactory = null;
    private static TextRenderer textRenderer = null;
    static {
        configurableCaptchaService = new ConfigurableCaptchaService();

        // 颜色创建工厂,使用一定范围内的随机色
        colorFactory = new RandomColorFactory();
        // 自定义颜色。默认的颜色看起来还可以。
        // Color min = new Color(20, 40, 20);
        // Color max = new Color(80, 50, 40);
        // colorFactory.setMin(min);
        // colorFactory.setMax(max);
        configurableCaptchaService.setColorFactory(colorFactory);

        // 随机字体生成器
        fontFactory = new RandomFontFactory();
        fontFactory.setMaxSize(28);
        fontFactory.setMinSize(24);
        configurableCaptchaService.setFontFactory(fontFactory);

        // 随机字符生成器,去除掉容易混淆的字母和数字,如o和0等
        wordFactory = new RandomWordFactory();
        wordFactory.setCharacters("abcdefhjkmnrtwxyABCDEFGHJKMNQRTUVWXYZ234678");
        wordFactory.setMaxLength(4);
        wordFactory.setMinLength(4);
        configurableCaptchaService.setWordFactory(wordFactory);

        // 自定义验证码图片背景
        MyCustomBackgroundFactory backgroundFactory =
                new MyCustomBackgroundFactory();
        configurableCaptchaService.setBackgroundFactory(backgroundFactory);

        // 文字渲染器设置
        textRenderer = new BestFitTextRenderer();
        textRenderer.setBottomMargin(3);
        textRenderer.setTopMargin(3);
        configurableCaptchaService.setTextRenderer(textRenderer);

        // 验证码图片的大小
        configurableCaptchaService.setWidth(112);
        configurableCaptchaService.setHeight(40);

        // 图片滤镜设置.
        AbstractFilterFactory filterFactory = new DoubleRippleFilterFactory();
        configurableCaptchaService.setFilterFactory(filterFactory);

    }

    public static AbstractCaptcha generateCaptcha() {

        // 图片滤镜设置. 随机设置样式
        AbstractFilterFactory filterFactory = null;
        Random random = new Random();
        int i = random.nextInt(2);
        switch (i) {
            case 0:
                // 双波性
                filterFactory = new DoubleRippleFilterFactory();
                break;
            case 1:
                // 游移不定
                filterFactory = new WobbleRippleFilterFactory();
                break;
        }
        configurableCaptchaService.setFilterFactory(filterFactory);
        AbstractCaptcha rrCaptcha = new RRCaptcha();
        try {
            // 得到验证码对象,有验证码图片和验证码字符串
            Captcha captcha = configurableCaptchaService.getCaptcha();
            String validationCode = captcha.getChallenge();
            // 取得验证码图片并输出
            BufferedImage bufferedImage = captcha.getImage();
            rrCaptcha.setCode(validationCode);
            rrCaptcha.setImage(bufferedImage);
        } catch (Exception e) {
            logger.debug("error happen when generate captcha!", e);
        }
        return rrCaptcha;

    }

}

class MyCustomBackgroundFactory implements BackgroundFactory {

    private Random random = new Random();

    public void fillBackground(BufferedImage image) {
        Graphics graphics = image.getGraphics();

        // 验证码图片的宽高
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        // 填充为白色背景
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imgWidth, imgHeight);

        // 画30个噪点(颜色及位置随机)
        for (int i = 0; i < 30; i++) {
            // 随机颜色
            int rInt = random.nextInt(255);
            int gInt = random.nextInt(255);
            int bInt = random.nextInt(255);

            graphics.setColor(new Color(rInt, gInt, bInt));

            // 随机位置
            int xInt = random.nextInt(imgWidth - 3);
            int yInt = random.nextInt(imgHeight - 2);

            // 随机旋转角度
            int sAngleInt = random.nextInt(360);
            int eAngleInt = random.nextInt(360);

            // 随机大小
            int wInt = random.nextInt(6);
            int hInt = random.nextInt(6);

            graphics.fillArc(xInt, yInt, wInt, hInt, sAngleInt, eAngleInt);

            // 画2条干扰线
            if (i % 20 == 0) {
                int xInt2 = random.nextInt(imgWidth);
                int yInt2 = random.nextInt(imgHeight);
                graphics.drawLine(xInt, yInt, xInt2, yInt2);
            }
        }
    }
}
