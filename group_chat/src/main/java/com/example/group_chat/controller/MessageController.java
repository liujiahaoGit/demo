package com.example.group_chat.controller;

import com.example.group_chat.DTO.Chat;
import com.example.group_chat.DTO.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 群聊
     * 客户端发送消息的请求路径为/app/hello
     * 方法体内处理完相应逻辑后返回消息,SendTo将消息广播到/topic中 然后客户端订阅此队列即可
     *
     * @param message
     * @return
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message recive(Message message) {
        LOGGER.info("接收到消息:{}", message);
        return message;
    }

    /**
     * 私聊
     *
     * @param chat 发送的消息
     * @param principal 中保存登录后的用户信息
     * @return
     */
    @MessageMapping("/singleChat")
    public void singleChat(Chat chat, Principal principal) {

        chat.setFrom(principal.getName());
        LOGGER.info("接收到消息:{}", chat);
        simpMessagingTemplate.convertAndSendToUser(chat.getTo(), "/queue/private", chat);
    }

}
