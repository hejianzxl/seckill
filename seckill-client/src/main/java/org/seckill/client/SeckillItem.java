package org.seckill.client;

import java.io.Serializable;

public class SeckillItem implements Serializable {
	private static final long serialVersionUID = 3571285614922319586L;
	private String userId;
	private String itemId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Override
	public String toString() {
		return "SeckillItem [userId=" + userId + ", itemId=" + itemId + "]";
	}
	
	
}
