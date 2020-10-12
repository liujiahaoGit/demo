package com.cicro.stream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(Sink.class) //表示绑定 Sink 消息通道
public class StreamController {




    private static final Logger LOGGER= LoggerFactory.getLogger(StreamController.class);

    @StreamListener(Sink.INPUT)
    public void test(Object message){
        LOGGER.info("receive:"+message);
    }

    @Autowired
    MyChannel myChannel;

    /**
     * 发送消息
     */
    @GetMapping("/publish")
    public void publish(){
        myChannel.output().send(MessageBuilder.withPayload("自定义通道测试").setHeader("x-delay",3000).build());
    }
}
