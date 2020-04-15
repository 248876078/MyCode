package com.box.pojo;
/**
 * 
 */
public class Customer {
	private int id;       // 
	private String username; // 
	private int quantity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getBalance() {
		return quantity;
	}
	public void setBalance(int balance) {
		this.quantity = balance;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", username=" + username + ", quantity=" + quantity + "]";
	}
	
	

}
