package net.ar.webonswing.petstore.model;

import java.io.*;

public class Address implements Serializable
{
	protected String city;
	protected String country;
	protected Integer id;
	protected String state;
	protected String street1;
	protected String street2;
	protected String zipcode;

	public Address()
	{
	}

	public String getCity()
	{
		return city;
	}

	public String getCountry()
	{
		return country;
	}
	
	public Integer getId()
	{
		return id;
	}

	public String getState()
	{
		return state;
	}

	public String getStreet1()
	{
		return street1;
	}

	public String getStreet2()
	{
		return street2;
	}

	public String getZipcode()
	{
		return zipcode;
	}

	public void setCity(String city)
	{
		this.city= city;
	}

	public void setCountry(String country)
	{
		this.country= country;
	}
	public void setId(Integer id)
	{
		this.id= id;
	}

	public void setState(String state)
	{
		this.state= state;
	}

	public void setStreet1(String street1)
	{
		this.street1= street1;
	}

	public void setStreet2(String street2)
	{
		this.street2= street2;
	}

	public void setZipcode(String zipcode)
	{
		this.zipcode= zipcode;
	}
}