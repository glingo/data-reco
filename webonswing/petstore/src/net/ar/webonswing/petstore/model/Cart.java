package net.ar.webonswing.petstore.model;

import java.io.*;
import java.util.*;

public class Cart implements Serializable
{
	private final Map itemMap= Collections.synchronizedMap(new HashMap());

	public Cart()
	{
	}
	
	public boolean isEmpty ()
	{
		return getNumberOfItems() == 0;
	}
	
	public void addItem(Item item, boolean isInStock)
	{
		CartItem cartItem= (CartItem)itemMap.get(item.getItemId());
		if (cartItem == null)
		{
			cartItem= new CartItem();
			cartItem.setItem(item);
			cartItem.setQuantity(0);
			cartItem.setInStock(isInStock);
			itemMap.put(item.getItemId(), cartItem);
		}
		cartItem.incrementQuantity();
	}

	public boolean containsItemId(String itemId)
	{
		return itemMap.containsKey(itemId);
	}

	public Collection getCartItems()
	{
		return itemMap.values();
	}

	public int getNumberOfItems()
	{
		return itemMap.size();
	}

	public double getTotal()
	{
		double total= 0;

		for (Iterator i= getCartItems().iterator(); i.hasNext();)
		{
			CartItem cartItem= (CartItem)i.next();
			total= total + cartItem.getTotal();
		}

		return total;
	}

	public void incrementQuantityByItemId(String itemId)
	{
		CartItem cartItem= (CartItem)itemMap.get(itemId);
		cartItem.incrementQuantity();
	}

	public Item removeItem(Item anItem)
	{
		CartItem cartItem= (CartItem)itemMap.remove(anItem.getItemId());
		if (cartItem == null)
		{
			return null;
		}
		else
		{
			itemMap.remove(anItem.getItemId());
			return cartItem.getItem();
		}
	}

	public void setQuantityByItemId(String itemId, int quantity)
	{
		CartItem cartItem= (CartItem)itemMap.get(itemId);
		cartItem.setQuantity(quantity);
	}

	public void clear()
	{
		itemMap.clear();
	}
}