package com.example.demo.domain;

import lombok.Data;

@Data
public class Message {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private String createdAt;
    private String readYn;
}
