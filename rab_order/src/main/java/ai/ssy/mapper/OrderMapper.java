package ai.ssy.mapper;

import ai.ssy.entity.Order;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Description: TODO
 * @Author: ssy
 * @Date: 2020/1/21
**/
@Mapper
public interface OrderMapper {
	public int insert(Order order);
	public int delete(int orderId);
	public int update(int orderId);
}
