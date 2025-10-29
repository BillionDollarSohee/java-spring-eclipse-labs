package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserAuth;
import com.example.demo.dto.Users;
import com.example.demo.mapper.UserMapper;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

// 트랜잭션 권한 처리는 여기서
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users login(String username) {
        // 예시: userMapper에 해당 메서드가 있다고 가정
        return userMapper.login(username);
    }

    @Override
    @Transactional
    public int join(Users users) throws Exception {
        // 비밀번호 암호화
        String encodedUserPw = passwordEncoder.encode(users.getUserPw());
        users.setUserPw(encodedUserPw);

        int result = 0;
        try {
            result = userMapper.join(users);

            if (result > 0) {
                UserAuth userAuth = new UserAuth();
                userAuth.setUserId(users.getUserId());
                userAuth.setAuth("ROLE_USER");
                userMapper.insertAuth(userAuth);
            }

            log.info("회원가입 성공: {}", users.getUserId());
        } catch (Exception e) {
            log.error("회원가입 중 오류 발생", e);
            throw e; // rollback 유도
        }
        return result;
    }

    @Override
    public int insertAuth(UserAuth userAuth) throws Exception {
        return userMapper.insertAuth(userAuth);
    }
}
