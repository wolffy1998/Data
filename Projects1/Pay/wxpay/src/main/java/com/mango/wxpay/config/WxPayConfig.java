package com.mango.wxpay.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Author mango
 * @Since 2020/3/17 12:01
 **/
@Component
@ConfigurationProperties(prefix = "wxpay")
public class WxPayConfig implements WXPayConfig {

    private String appid;

    private String mchid;

    private String key;

    private byte[] certData;


    /**
     * 对象创建时先执行代码块，后执行构造方法
     */
    {
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/cert/apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }

    /**
     * 先调用构造器创建对象，后调用setter给属性赋值
     * @throws Exception
     */
    public WxPayConfig() throws Exception {

    }

    @Override
    public String getAppID() {
        return appid;
    }

    @Override
    public String getMchID() {
        return mchid;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 5000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 5000;
    }

    public void setAppID(String appid) {
        this.appid = appid;
    }

    public void setMchID(String mchid) {
        this.mchid = mchid;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
