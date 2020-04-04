package com.mango.wxpay.controller;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.mango.wxpay.config.WxPayConfig;
import com.mango.wxpay.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author mango
 * @Since 2020/3/17 11:47
 **/
@RestController
@RequestMapping("wxpay")
public class WxPayController {

    @Autowired
    private WxPayConfig config;

    @Autowired
    private WxPayService wxPayService;

    /**
     * 统一下单
     * @param data
     * @return
     * @throws Exception
     */
    @RequestMapping("unifiedorder")
    public Map<String, String> unifiedorder(@RequestBody Map<String, String> data) throws Exception {
        WXPay wxPay = new WXPay(config);
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        // 密钥是服务器与微信服务器双方知道不在网络中传输-保证数据的安全性
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        data.put("sign", sign);
        Map<String, String> resp = wxPay.unifiedOrder(data);
        // 不同类型的支付返回结果是不一样的
        return this.wxPayService.result(resp, config);
    }

    // 回调结果通知

    // 关闭订单

    // 查询订单

    // 退款

    // 查询退款

    // 下载交易账单

}
