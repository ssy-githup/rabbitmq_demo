package ai.ssy.service;

import ai.ssy.entity.Order;


public interface IOrderService {

	public int shopping(Order order) throws Exception;

	public int shopping4MQ(Order order) throws Exception;
	
	public void shoppingRollback(int orderId) throws Exception;
	
	public void shoppingCommit(int orderId) throws Exception;
}
