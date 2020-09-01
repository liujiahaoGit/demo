package com.cicro.wxpay.impl;

import com.cicro.wxpay.config.MyWxPayConfig;
import com.cicro.wxpay.service.NativeInterface;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class NativeService implements NativeInterface {

    @Autowired
    MyWxPayConfig payConfig;

    /**
     * 统一下单接口(用于返回二维码的url)
     *
     * @param money      金额
     * @param outTradeNo 商户订单号
     * @param ip         终端IP
     * @return
     */
    @Override
    public Map<String, String> unifiedOrder(String money, String outTradeNo, String ip) {
        Map<String, String> paramMap = new LinkedHashMap<>(8);
        WXPay wxPay = new WXPay(payConfig);
        //入参 根据微信开放文档的接口入参规则填写即可
        paramMap.put("device_info", "WEB");
        paramMap.put("body", "腾讯充值中心-QQ会员充值");
        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("total_fee", money);
        paramMap.put("spbill_create_ip", ip);
        paramMap.put("notify_url", payConfig.getNotifyUrl());
        paramMap.put("trade_type", "NATIVE");
        paramMap.put("product_id", "000000000");

        HashMap<String, String> result = new HashMap<>(2);
        try {
            Map<String, String> map = wxPay.unifiedOrder(paramMap);
            //return_code是通信标识，非交易标识，交易是否成功需要查看result_code来判断
            if ("SUCCESS".equals(map.get("return_code"))) {
                if ("SUCCESS".equals(map.get("result_code"))) {
                    result.put("status", "success");
                    result.put("msg", "请求成功");
                    result.put("code_url", map.get("code_url"));
                    result.put("prepay_id", map.get("prepay_id"));
                    result.put("trade_type", map.get("trade_type"));
                    result.put("money", money);
                    result.put("out_trade_no", outTradeNo);
                    return result;
                } else {
                    result.put("status", "fail");
                    result.put("msg", map.get("err_code_des"));
                    return result;
                }
            } else {
                result.put("status", "fail");
                result.put("msg", map.get("return_msg"));
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("status", "fail");
        result.put("msg", "未知异常");
        return result;
    }

    /**
     * 查询订单
     *
     * @param outTradeNo 商户订单号
     * @return
     */
    @Override
    public Map<String, String> orderQuery(String outTradeNo) {
        Map<String, String> paramMap = new LinkedHashMap<>(1);
        WXPay wxPay = new WXPay(payConfig);
        paramMap.put("out_trade_no", outTradeNo);
        Map<String, String> result = new HashMap<>();
        try {
            Map<String, String> map = wxPay.orderQuery(paramMap);
            if ("SUCCESS".equals(map.get("return_code"))) {
                if ("SUCCESS".equals(map.get("result_code"))) {
                    //result.put("status", "success");
                    result.put("out_trade_no", map.get("out_trade_no")); //商户订单号
                    switch (map.get("trade_state")) {

                        case "SUCCESS":
                            result.put("trade_type", map.get("trade_type")); //支付类型
                            result.put("trade_state", map.get("trade_state")); //支付状态
                            result.put("transaction_id", map.get("transaction_id")); //微信支付订单号
                            result.put("total_fee", map.get("total_fee")); //支付金额
                            result.put("time_end", map.get("time_end")); //支付完成时间
                            result.put("msg", "支付成功");
                            break;
                        case "REFUND":
                            result.put("msg", "转入退款");
                            break;
                        case "NOTPAY":
                            map.put("msg", "未支付");
                            break;
                        case "CLOSED":
                            map.put("msg", "已关闭");
                            break;
                        case "REVOKED":
                            map.put("msg", "已撤销");
                            break;
                        case "USERPAYING":
                            map.put("msg", "用户支付中");
                            break;
                        case "PAYERROR":
                            map.put("msg", "支付失败");
                            break;
                        default:
                            map.put("msg", "支付失败");
                            break;
                    }

                    return result;
                } else {
                    result.put("status", "fail");
                    result.put("out_trade_no", map.get("out_trade_no")); //商户订单号
                    result.put("msg", map.get("err_code_des"));
                    return result;
                }
            } else {
                result.put("status", "fail");
                result.put("msg", map.get("return_msg"));
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("status", "fail");
        result.put("msg", "未知异常");
        return result;
    }

}
