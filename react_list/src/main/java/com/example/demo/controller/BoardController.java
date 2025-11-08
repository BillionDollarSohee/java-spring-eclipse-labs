package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;

import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public Map<String, Object> getBoardList(@RequestParam(name="page", defaultValue = "1") int page,
                                            @RequestParam(name="size", defaultValue = "10") int size) {

        List<Board> boards = boardService.getBoards(page, size);
        int total = boardService.getTotalCount();

        Map<String, Object> result = new HashMap<>();
        result.put("data", boards);
        result.put("total", total);
        result.put("page", page);
        result.put("size", size);

        return result;
    }
}
