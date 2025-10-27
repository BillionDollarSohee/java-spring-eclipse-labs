package com.model;

import java.util.List;

/*
 주문서 클래스 - 하나의 주문은 여러개의 상품(OrderItem)을 가질 수 있다.
 
 = 하나의 게시글을 여러개의 댓글을 가질 수 있다.
 Board클래스는 댓글을 가지고 있다.
 private List<Reply> replyList;
 
 주문이 2건 발생하면 orderCommand를 만들고, List<OrderItem>을 만든다.
 1, 10개, 파손주의 
 2, 2개, 리모콘 별도주문
 
 List<OrderItem> itemList = new ArrayList();
 itemList.add(new OrderItem(1, 10, "파손주의"));
 itemList.add(new OrderItem(2, 2, "리모콘 별도주문"));
 command.setOrderItem(itemList);
 */

public class OrderCommand { 
	// 주문 1건은 여러개의 주문상세(상품)을 가질 수 있다.
	private List<OrderItem> orderItem;

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}
	

}
