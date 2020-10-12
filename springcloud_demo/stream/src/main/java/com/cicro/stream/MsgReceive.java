package com.cicro.stream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Date;

@EnableBinding(MyChannel.class)
public class MsgReceive {


    private static final Logger LOGGER= LoggerFactory.getLogger(MsgReceive.class);

    @StreamListener(MyChannel.INPUT)
    public void test(Object message){
        LOGGER.info("receive:"+message+"  "+new Date());
    }
}
