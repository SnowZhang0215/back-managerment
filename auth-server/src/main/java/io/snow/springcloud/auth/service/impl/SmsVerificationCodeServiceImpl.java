package io.snow.springcloud.auth.service.impl;

import io.snow.springcloud.auth.service.SmsVerificationCodeService;

/**
 * 一般验证码可以存储在Redis中
 */
public class SmsVerificationCodeServiceImpl implements SmsVerificationCodeService {
    @Override
    public String getVerificationCode(String phone) {
        return "1234";
    }
}
