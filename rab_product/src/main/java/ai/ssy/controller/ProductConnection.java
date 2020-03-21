package ai.ssy.controller;

import ai.ssy.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.ssy.entity.Order;


@RestController
@RequestMapping(value="/product")
public class ProductConnection {

	@Autowired
	private IProductService service;
	
	@RequestMapping(value="/updateProduct", produces = "application/json;charset=UTF-8;")
	public String updateProduct(@RequestParam int productId, @RequestParam int number) throws Exception{
		Order order = new Order();
		order.setNumber(number);
		order.setProductId(productId);
		int row = service.updateProduct(order);
		
		if(row==0) throw new RuntimeException("============修改库存失败。"); 
		return "true";
	}
}
