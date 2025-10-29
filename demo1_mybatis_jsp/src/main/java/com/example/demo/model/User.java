package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    id number primary key,
    username varchar2(50),
    password varchar2(50),
    email varchar2(50) 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private Long id;
	private String username;
	private String password;
	private String email;
}
