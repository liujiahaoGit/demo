package com.cicro.wxpay.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/*
 * @className: MyWxPayConfig
 * @description 支付所需相关配置类
 * @since JDK1.8
 * @author ljh
 * @createdAt  2020/9/1 0001
 * @version 1.0.0
 **/
@Configuration
public class MyWxPayConfig implements WXPayConfig {
    /**
     * 获取 App ID
     *
     * @return App ID
     */
    @Override
    public String getAppID() {
        return "wx8397f8696b538317";
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    @Override
    public String getMchID() {
        return "1473426802";
    }

    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    @Override
    public String getKey() {
        return "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";
    }

    /**
     * 异步接收微信支付结果通知的回调地址(此处的值随意填写)
     *
     * @return
     */
    public String getNotifyUrl() {
        return "http://localhost:8080/notify";
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream() {
        return null;
    }

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
