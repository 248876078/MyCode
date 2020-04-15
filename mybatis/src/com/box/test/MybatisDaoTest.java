package com.box.test;

import org.junit.Test;

import com.box.dao.BuyerDao;
import com.box.dao.BuyerDaoImpl;
import com.box.util.MybatisUtil;

public class MybatisDaoTest {

	MybatisUtil mybatisUtil;
	
	@Test
	public void testDao4() throws Exception {
		  
		 //生成BuyerDao实体类
		 BuyerDao buyerDao = new BuyerDaoImpl(mybatisUtil);
		 //实体类执行购物车方法
		 buyerDao.shopping();
		
	}
	
	
	
}
