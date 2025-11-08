package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Board;
import com.example.demo.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<Board> getBoards(int page, int size) {
        return boardMapper.getBoards(page, size);
    }

    @Override
    public int getTotalCount() {
        return boardMapper.getTotalCount();
    }
}
