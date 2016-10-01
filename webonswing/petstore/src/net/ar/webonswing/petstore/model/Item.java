package net.ar.webonswing.petstore.model;

import java.io.*;

public class Item implements Serializable
{
	protected String description;
	protected Integer id;
	protected String imagePath;
	protected String itemId;
	protected double listPrice;
	protected Product product;
	protected double unitCost;

	public Item()
	{
	}

	public Item(String anItemId, String aDescription, double aListPrice, double anUnitCost, String anImagePath)
	{
		itemId= anItemId;
		description= aDescription;
		listPrice= aListPrice;
		unitCost= anUnitCost;
		imagePath= anImagePath;
	}

	public String getDescription()
	{
		return description;
	}
	public Integer getId()
	{
		return id;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public String getItemId()
	{
		return itemId;
	}

	public double getListPrice()
	{
		return listPrice;
	}

	public Product getProduct()
	{
		return product;
	}

	public double getUnitCost()
	{
		return unitCost;
	}

	public void setDescription(String description)
	{
		this.description= description;
	}
	public void setId(Integer id)
	{
		this.id= id;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath= imagePath;
	}

	public void setItemId(String itemId)
	{
		this.itemId= itemId;
	}

	public void setListPrice(double listPrice)
	{
		this.listPrice= listPrice;
	}

	public void setProduct(Product product)
	{
		this.product= product;
	}

	public void setUnitCost(double unitCost)
	{
		this.unitCost= unitCost;
	}
}