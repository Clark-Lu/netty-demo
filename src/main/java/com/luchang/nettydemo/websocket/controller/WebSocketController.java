package com.luchang.nettydemo.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * created by LuChang
 * 2019/1/29 16:10
 */
@Controller
public class WebSocketController {

    @RequestMapping(value = "/chat",method = RequestMethod.GET)
    public String chat(){
        return "chat.html";
    }

}
