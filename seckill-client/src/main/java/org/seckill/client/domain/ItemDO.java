package org.seckill.client.domain;

import java.io.Serializable;
import java.util.Date;

public class ItemDO implements Serializable {

	private static final long	serialVersionUID	= -5692800935504470747L;

	private long				id;

	private String				itemName;

	private String				itemCode;

	private long				inventoryQty;

	private long				usableQty;

	private long				price;

	private Date				createTime;

	private Date				updateTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public long getInventoryQty() {
		return inventoryQty;
	}

	public void setInventoryQty(long inventoryQty) {
		this.inventoryQty = inventoryQty;
	}

	public long getUsableQty() {
		return usableQty;
	}

	public void setUsableQty(long usableQty) {
		this.usableQty = usableQty;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "ItemDO [id=" + id + ", itemName=" + itemName + ", itemCode=" + itemCode + ", inventoryQty="
				+ inventoryQty + ", usableQty=" + usableQty + ", price=" + price + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
}
