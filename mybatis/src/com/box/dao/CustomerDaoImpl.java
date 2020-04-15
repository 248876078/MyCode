/**
 * 
 */
package com.box.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.box.pojo.Customer;
import com.box.util.MybatisUtil;

/**
 *
 */
public class CustomerDaoImpl implements CustomerDao {

	// 注入
	private MybatisUtil mybatisUtil;

	public CustomerDaoImpl(MybatisUtil mybatisUtil) {
		this.mybatisUtil = mybatisUtil;
	}

	// 通过用户ID查询一个用户
	public Customer selectById(Integer id) {
		SqlSession sqlSession = mybatisUtil.getSqlSession();
		return sqlSession.selectOne("c.selectObject", id);
	}

	// 通过用户名称模糊查询
	public List<Customer> selectByUsername(String username) {
		SqlSession sqlSession = mybatisUtil.getSqlSession();
		return sqlSession.selectList("c.selectList", username);
	}

}
