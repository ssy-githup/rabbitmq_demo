package ai.ssy.mapper;

import ai.ssy.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 */
@Mapper
public interface ProductMapper {
	public int update(Order order);
}
