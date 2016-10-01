package net.ar.webonswing.petstore.model;

import java.io.*;

public class CreditCard implements Serializable
{
	public static final String AMEX= "American Express";
	public static final String MASTERCARD= "MasterCard";
	public static final String VISA= "Visa";

	public static Object[] getTypes()
	{
		return new String[] { "", VISA, MASTERCARD, AMEX};
	}
	
	private String expiryDate;

	private String number;
	private String type;

	public CreditCard()
	{
	}

	public String getExpiryDate()
	{
		return expiryDate;
	}
	
	public String getNumber()
	{
		return number;
	}
	public String getType()
	{
		return type;
	}
	public void setExpiryDate(String expiryDate)
	{
		this.expiryDate= expiryDate;
	}
	
	public void setNumber(String number)
	{
		this.number= number;
	}
	public void setType(String type)
	{
		this.type= type;
	}
}