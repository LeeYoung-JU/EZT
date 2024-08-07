package com.yedam.app.usr.service;

import lombok.Data;

@Data
public class UserRvwCriteria {
	//페이징
	private Integer pageNum;
	private int amount;
	private int writer;
	private String categoryCode;
	private String regionCode;
	
	
	
	//검색
	private String type;
	private String keyword;
	
	public UserRvwCriteria() {
		this(1,5);
	}
	public UserRvwCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
	
	
	
}
