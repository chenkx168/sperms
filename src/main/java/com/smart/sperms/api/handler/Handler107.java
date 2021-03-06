package com.smart.sperms.api.handler;

import com.smart.sperms.api.protocol.MsgPayload;
import org.springframework.stereotype.Component;

/**
 * 处理107协议
 */
@Component
public class Handler107 extends Handler {

    @Override
    public void execute(String eId, MsgPayload msgPayload) {
        logger.info("send msg to android...msg ={}", msgPayload);
        super.sendMsg(eId, msgPayload);
    }

}
