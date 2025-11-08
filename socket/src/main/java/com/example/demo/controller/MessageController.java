package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.Message;
import com.example.demo.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    // 메시지 전송
    @PostMapping("/send")
    public void send(@RequestBody Message message) {
        messageService.send(message);
    }

    // 한쪽 기준 전체 메시지 가져오기
    @GetMapping("/list/{receiverId}")
    public List<Message> list(@PathVariable("receiverId") Long receiverId){
        return messageService.getMessages(receiverId);
    }

    @GetMapping("/room")
    public List<Message> list2(
            @RequestParam("senderId") Long senderId,
            @RequestParam("receiverId") Long receiverId
    ){
        return messageService.getMessages2(senderId, receiverId);
    }

    // 읽음 처리
    @PostMapping("/read/{id}")
    public void read(@PathVariable("id") Long id) throws Exception {
        messageService.readMessage(id);
    }
}
