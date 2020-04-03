package io.snow.springcloud.auth.service.impl;

import io.snow.springcloud.auth.service.SmsVerificationCodeService;

public class SmsVerificationCodeServiceImpl implements SmsVerificationCodeService {
    @Override
    public String getVerificationCode(String phone) {
        return "1234";
    }
}
