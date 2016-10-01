package net.ar.webonswing.petstore.model;

import java.io.*;

public class OrderItem implements Serializable
{
	protected Integer id;
	private Item item;
	private Integer orderItemId;
	private int quantity;
	private double unitPrice;

	public OrderItem()
	{
	}

	public OrderItem(Item anItem, int aQuantity)
	{
		item= anItem;
		quantity= aQuantity;
		unitPrice= anItem.getListPrice();
	}
	public Integer getId()
	{
		return id;
	}
		
	public Item getItem()
	{
		return item;
	}
	public Integer getOrderItemId()
	{
		return orderItemId;
	}
	public int getQuantity()
	{
		return quantity;
	}

	public double getSubTotal()
	{
		return quantity * unitPrice;
	}
	public double getUnitPrice()
	{
		return unitPrice;
	}
	public void setId(Integer id)
	{
		this.id= id;
	}
	public void setItem(Item item)
	{
		this.item= item;
	}
	public void setOrderItemId(Integer orderItemId)
	{
		this.orderItemId= orderItemId;
	}
	public void setQuantity(int quantity)
	{
		this.quantity= quantity;
	}
	public void setUnitPrice(double unitPrice)
	{
		this.unitPrice= unitPrice;
	}
}