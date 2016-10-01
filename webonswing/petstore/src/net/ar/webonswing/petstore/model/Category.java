package net.ar.webonswing.petstore.model;

import java.io.*;
import java.util.*;

public class Category implements Serializable
{
	protected String description;
	protected Integer id;
	protected String name;
	protected Set products= new HashSet();
	
	public Category ()
	{
	}

	public Category(String aName, String aDescription)
	{
		name= aName;
		description= aDescription;
	}

	public void addProduct(Product aProduct)
	{
		products.add(aProduct);
	}
	
	public String getDescription()
	{
		return description;
	}
	public Integer getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public Set getProducts()
	{
		return products;
	}
	public void setDescription(String description)
	{
		this.description= description;
	}
	public void setId(Integer id)
	{
		this.id= id;
	}
	public void setName(String name)
	{
		this.name= name;
	}
	public void setProducts(Set products)
	{
		this.products= products;
	}
}