package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import com.example.demo.domain.Message;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.websocket.WSHandler;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageMapper mapper;
    private final WSHandler wsHandler;

    @Transactional
    public void send(Message message) {
        mapper.insertMessage(message);

        try {
            wsHandler.notify(
                message.getReceiverId(),
                "NEW_MESSAGE:" + message.getSenderId()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Message> getMessages2(Long senderId, Long receiverId){
        return mapper.select2(senderId, receiverId);
    }

    public List<Message> getMessages(Long receiverId){
        return mapper.selectMessages(receiverId);
    }

    @Transactional
    public void readMessage(Long id) throws Exception {
        mapper.updateRead(id);
        Long senderId = mapper.findSenderIdByMessageId(id);
        if (senderId != null) {
            wsHandler.notifyRead(senderId, id);
        }
    }
}
