package io.snow.springcloud.auth.service;

public interface SmsVerificationCodeService {
    String getVerificationCode(String phone);
}
