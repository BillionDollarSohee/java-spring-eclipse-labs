package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    private Long id;                  // NUMBER
    private String title;             // VARCHAR2(200)
    private String writer;            // VARCHAR2(100)
    private String content;           // CLOB -> 엄청 긴 텍스트를 저장하는 타입
    private java.util.Date createdAt; // DATE

}

