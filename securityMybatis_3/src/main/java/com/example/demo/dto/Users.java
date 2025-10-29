package com.example.demo.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class Users {
    private  int userNo;
    private String userId;
    private String userPw;
    private String name;
    private String email;
    private Date regDate;
    private Date updDate;

    //권한 목록
    List<UserAuth> authList;

    private int enabled;

}
