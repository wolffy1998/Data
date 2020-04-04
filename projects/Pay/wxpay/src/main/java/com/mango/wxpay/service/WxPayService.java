package com.mango.wxpay.service;

import com.github.wxpay.sdk.WXPayUtil;
import com.mango.wxpay.config.WxPayConfig;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author mango
 * @Since 2020/3/17 13:00
 **/
@Service
public class WxPayService {

    public Map result(Map<String, String> resp, WxPayConfig config) throws Exception {
        if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {
            /** 重要的事情说三遍  小程序支付 所有的字段必须大写 驼峰模式 严格按照小程序支付文档
             *https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3#
             * ******* 我当初就因为timeStamp中S没大写弄了3个小时 **********
             * */
            Map<String, String> app = new HashMap<>();
            app.put("appId", resp.get("appid"));
            app.put("package", "prepay_id=" + resp.get("prepay_id"));   //package = prepay_id = resp.get("prepay_id")
            app.put("nonceStr", resp.get("nonce_str"));
            app.put("timeStamp", new Date().getTime() / 1000 + "");  // 时间为秒，JDK 生成的是毫秒，故除以 1000
            app.put("signType", "MD5");
            app.put("paySign", WXPayUtil.generateSignature(app, config.getKey()));
            resp.put("paySign", app.get("paySign"));
            resp.put("timeStamp", app.get("timeStamp"));
            resp.put("package", app.get("package"));
            return resp;
        } else
            return resp;
    }

}
