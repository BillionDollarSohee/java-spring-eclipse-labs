package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.OrderCommand;

// 하나의 요청 주소로 2개의 업무 처리

@Controller
@RequestMapping("/order/order.do")
public class OrderController {
	
	@GetMapping
	public String frim() {
		return "order/OrderForm";
	}
	
	@PostMapping
	public String submit(OrderCommand orderCommand) {
		return "order/OrderCommitted";   // 뷰의 주소
	}
}

// 어떤 코드가 생략되었을까??
// 1. OrderCommand orderCommand = new OrderCommand();
// 2. 자동매핑 -> member필드가 자동매핑되어야 한다. private List<OrderItem> orderItem >> 자동주입
// 3. List<OrderItem> itemList = new ArrayList();
// -> orderItem[0].itemid >> 1
// -> orderItem[0].number >> 10
// -> orderItem[0].remark >> 파손주의
// -> itemList.add(new OrderItem(1,10,"파손주의"); *3번
// -> orderCommand.setOrderItem(itemList); 자동 세터 주입
// 4. view에 전달
// forward 는 자동으로 orderCommand 를 key로 value는 객체주소 잡아서 객체주소를 전달해줌
// mv.addObject("orderCommand주소");
// value는 객체주소
