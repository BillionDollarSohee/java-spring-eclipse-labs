package com.example.demo;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheck {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String dbHash = "$2a$12$XaNpE2vVBM7x4ZCqlafgxu2W5vw9SXnvMgUZQnzOkFSZt605JHuqq";

        System.out.println("1234 -> " + encoder.matches("1234", dbHash));
        System.out.println("1004 -> " + encoder.matches("1004", dbHash));
        System.out.println("abcd -> " + encoder.matches("abcd", dbHash));
        System.out.println("admin -> " + encoder.matches("admin", dbHash));
    }
}
