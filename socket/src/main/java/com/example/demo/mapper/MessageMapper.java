package com.example.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Message;


@Mapper
public interface MessageMapper {
	 void insertMessage(Message message);
	 List<Message> selectMessages(@Param("receiverId") Long receiverId);
	 void updateRead(@Param("id") Long id);
	 Long findSenderIdByMessageId(@Param("id") Long id);
	 List<Message> select2(@Param("senderId") Long senderId,
                           @Param("receiverId") Long receiverId);
}
