package net.ar.webonswing.petstore.model;

import java.io.*;
import java.util.*;

public class Product implements Serializable
{
	protected String description;
	protected Integer id;
	protected Set items= new HashSet();
	protected String name;
	protected String productId;

	public Product()
	{
	}

	public Product(String aProductId, String aName, String aDescription)
	{
		productId= aProductId;
		name= aName;
		description= aDescription;
	}

	public void addItem(Item anItem)
	{
		items.add(anItem);
		anItem.setProduct(this);
	}

	public String getDescription()
	{
		return description;
	}
	public Integer getId()
	{
		return id;
	}

	public Set getItems()
	{
		return items;
	}

	public String getName()
	{
		return name;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setDescription(String description)
	{
		this.description= description;
	}
	public void setId(Integer id)
	{
		this.id= id;
	}

	public void setItems(Set items)
	{
		this.items= items;
	}

	public void setName(String name)
	{
		this.name= name;
	}

	public void setProductId(String productId)
	{
		this.productId= productId;
	}
}