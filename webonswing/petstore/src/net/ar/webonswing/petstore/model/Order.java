package net.ar.webonswing.petstore.model;

import java.io.*;
import java.util.*;

public class Order implements Serializable
{
	public static final String CANCELLED= "cancelled";
	public static final String DELIVERED= "delivered";
	public static final String PENDING= "pending";

	protected Address address;
	protected CreditCard creditCard;
	protected Customer customer;
	protected Date date;
	protected Integer id;
	protected Set orderItems= new HashSet();
	protected String status= PENDING;

	public Order()
	{
	}
	
	public Order(Customer aCustomer)
	{
		customer= aCustomer;
		creditCard= customer.getCreditCard();
		address= customer.getAddress();
		date= new Date();
	}

	public void add(Item item, int quantity)
	{
		orderItems.add(new OrderItem(item, quantity));
	}

	public Address getAddress()
	{
		return address;
	}
	public CreditCard getCreditCard()
	{
		return creditCard;
	}
	public Customer getCustomer()
	{
		return customer;
	}
	public Date getDate()
	{
		return date;
	}
	public Integer getId()
	{
		return id;
	}
	public Set getOrderItems()
	{
		return orderItems;
	}
	public String getStatus()
	{
		return status;
	}

	public double getTotal()
	{
		double total= 0;
		Iterator it= orderItems.iterator();

		while (it.hasNext())
		{
			total+= ((OrderItem)it.next()).getSubTotal();
		}

		return total;
	}
	public void setAddress(Address address)
	{
		this.address= address;
	}
	public void setCreditCard(CreditCard creditCard)
	{
		this.creditCard= creditCard;
	}
	public void setCustomer(Customer customer)
	{
		this.customer= customer;
	}
	public void setDate(Date date)
	{
		this.date= date;
	}
	public void setId(Integer id)
	{
		this.id= id;
	}
	public void setOrderItems(Set orderItems)
	{
		this.orderItems= orderItems;
	}
	public void setStatus(String status)
	{
		this.status= status;
	}

}