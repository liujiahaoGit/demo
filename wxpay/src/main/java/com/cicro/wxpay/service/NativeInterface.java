package com.cicro.wxpay.service;

import java.util.Map;

public interface NativeInterface {

    /**
     * 统一下单接口
     * @param money 金额
     * @param outTradeNo 商户订单号
     * @param ip 终端IP
     * @return
     */
    Map<String,String> unifiedOrder(String money,String outTradeNo,String ip);

    /**
     * 订单查询接口
     * @param outTradeNo 商户订单号
     * @return
     */
    Map<String,String> orderQuery(String outTradeNo);
}
