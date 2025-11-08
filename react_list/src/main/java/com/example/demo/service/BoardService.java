package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Board;

public interface BoardService {
    List<Board> getBoards(int page, int size);
    int getTotalCount();
}