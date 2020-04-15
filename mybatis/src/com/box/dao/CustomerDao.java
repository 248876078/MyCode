/**
 * 
 */
package com.box.dao;

import java.util.List;

import com.box.pojo.Customer;

/**
 *
 */
public interface CustomerDao {
	//通过用户ID查询一个用户
		public Customer selectById(Integer id);
		public List<Customer> selectByUsername(String username);
}
