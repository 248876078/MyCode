package com.box.dao;

import com.box.pojo.Buyer;

public interface BuyerDao {
		public Buyer selectById(int id);
		
		public void shopping();
}
