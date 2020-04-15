package com.box.dao;

import org.apache.ibatis.session.SqlSession;

import com.box.pojo.Buyer;
import com.box.pojo.Seller;
import com.box.util.MybatisUtil;

public class BuyerDaoImpl implements BuyerDao{
	private MybatisUtil mybatisUtil;
	
	public BuyerDaoImpl(MybatisUtil mybatisUtil) {
		this.mybatisUtil = mybatisUtil;
	}
	
	public Buyer selectById(int id) {
		SqlSession sqlSession = mybatisUtil.getSqlSession();
		return sqlSession.selectOne("a.selectObject", id);
	}

	//购物方法
	public void shopping() {
		//获取sqlSession
		SqlSession sqlSession = mybatisUtil.getSqlSession();
		//创建Buyer实体类
		Buyer buyer = new Buyer();
		//创建Seller实体类
		Seller seller = new Seller();
		//卖家库存减1
		seller.setQuantity(1);
		//买家库存加1
		buyer.setQuantity(1);
		//执行买家数据库sql语句
		int row = sqlSession.update("a.update",buyer);
		//执行卖家数据库sql语句
		int row2 = sqlSession.update("b.update",seller);
		//从买家数据库查找买家姓名
		String name = sqlSession.selectOne("a.select", 1);
		//从买卖家数据库查找商品信息
		Seller seller2 = sqlSession.selectOne("b.selectObject", 1);
		/*
		 * System.out.println(name); System.out.println(seller2);
		 * System.out.println(row); System.out.println(row2);
		 */
		System.out.println("欢迎光临本店！！！" + name + "先生！！！" + "您购买的商品为：" + seller2 );
		System.out.println("买家库存加：" + row + "," + "卖家库存减:" + row2 );
		
		/*
		 * if(rows > 0) { System.out.println("您成功更新了" + rows + "条数据！"); }else {
		 * System.out.println("执行更新操作失败"); }
		 */
		//提交事务
		sqlSession.commit();
		//关闭SqlSession
		sqlSession.close();
		
	}
	

}
