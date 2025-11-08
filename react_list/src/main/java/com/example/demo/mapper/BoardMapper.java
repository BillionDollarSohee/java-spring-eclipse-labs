package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Board;

@Mapper
public interface BoardMapper {

    List<Board> getBoards(@Param("page") int page, @Param("size") int size);
    int getTotalCount();

}
