package com.cicro.wxpay.controller;

import com.cicro.wxpay.impl.NativeService;
import com.cicro.wxpay.util.IpUtil;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class PayController {

    @Autowired
    private NativeService nativeService;

    @RequestMapping("/createNative")
    public Map createNative(HttpServletRequest request) {

        return nativeService.unifiedOrder("1", WXPayUtil.generateNonceStr(), IpUtil.getIpAddr(request));

    }

    @RequestMapping("/orderQuery")
    public Map orderQuery(String outTradeNo) {

        int i=0;

        while (true) {
            Map<String, String> map = nativeService.orderQuery(outTradeNo);
            i++;
            if ("SUCCESS".equals(map.get("trade_state"))) {
                return map;
            }

            try {
                //因为这里是循环调用查询订单,所以如果前台超过5分钟没有支付 就会让刷新页面重新生成二维码
                Thread.sleep(3000);
                if (i>=100){
                    System.out.println(i);
                    map.put("status","fail");
                    map.put("msg","二维码已超时");
                    return map;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                map.put("msg", "支付失败");
                return map;
            }
        }

    }

}
