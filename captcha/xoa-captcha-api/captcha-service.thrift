namespace java com.kk.xoa.captcha

struct CodeVerifyResponse {
	1:required bool verify;
}

struct CodeVerifyRequest {
    1:required string post;
    2:required string captcha;
}

service CaptchaService {
    CodeVerifyResponse verifySecurityCode(1:CodeVerifyRequest req);
}
