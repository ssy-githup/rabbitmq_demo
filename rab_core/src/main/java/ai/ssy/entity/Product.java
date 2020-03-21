package ai.ssy.entity;

import java.io.Serializable;

/**
 * 实体类
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private int productId;
	private String productName;
	private int count;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
