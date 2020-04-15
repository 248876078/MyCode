package com.box.pojo;

public class Seller {
	//商品id
	private int id;
	//商品名称
	private String name;
	//商品数量
	private int quantity;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + ", quantity=" + quantity + "]";
	}
	
}
