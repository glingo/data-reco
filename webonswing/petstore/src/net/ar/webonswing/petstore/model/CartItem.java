package net.ar.webonswing.petstore.model;

import java.io.*;

public class CartItem implements Serializable
{
	protected boolean inStock;
	protected Item item;
	protected int quantity;
	protected long total;

	public CartItem()
	{
	}

	protected void calculateTotal()
	{
		if (item != null && item.getUnitCost() != 0)
			total= (long)(item.getUnitCost() * quantity);
		else
			total= 0;
	}

	public Item getItem()
	{
		return item;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public long getTotal()
	{
		return total;
	}

	public void incrementQuantity()
	{
		quantity++;
		calculateTotal();
	}

	public boolean isInStock()
	{
		return inStock;
	}

	public void setInStock(boolean isInStock)
	{
		this.inStock= isInStock;
	}

	public void setItem(Item anItem)
	{
		this.item= anItem;
		calculateTotal();
	}

	public void setQuantity(int aQuantity)
	{
		this.quantity= aQuantity;
		calculateTotal();
	}
}