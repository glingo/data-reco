package net.ar.webonswing.petstore.model;

import java.io.*;

public class Customer implements Serializable
{
	protected Address address= new Address();
	protected CreditCard creditCard= new CreditCard();
	
	protected String email;
	protected String firstname;
	protected Integer id;
	protected String language;
	protected String lastname;
	protected String password;
	protected String telephone;
	protected String username;

	public Customer()
	{
	}
	
	public Address getAddress()
	{
		return address;
	}
	public CreditCard getCreditCard()
	{
		return creditCard;
	}
	public String getEmail()
	{
		return email;
	}
	public String getFirstname()
	{
		return firstname;
	}
	
	public Integer getId()
	{
		return id;
	}
	public String getLanguage()
	{
		return language;
	}
	public String getLastname()
	{
		return lastname;
	}
	public String getPassword()
	{
		return password;
	}
	public String getTelephone()
	{
		return telephone;
	}
	public String getUsername()
	{
		return username;
	}
	public void setAddress(Address aAddress)
	{
		address= aAddress;
	}
	public void setCreditCard(CreditCard aCreditCard)
	{
		creditCard= aCreditCard;
	}
	public void setEmail(String aEmail)
	{
		email= aEmail;
	}
	public void setFirstname(String aFirstname)
	{
		firstname= aFirstname;
	}
	public void setId(Integer aId)
	{
		id= aId;
	}
	public void setLanguage(String aLanguage)
	{
		language= aLanguage;
	}
	public void setLastname(String aLastname)
	{
		lastname= aLastname;
	}
	public void setPassword(String aPassword)
	{
		password= aPassword;
	}
	public void setTelephone(String aTelephone)
	{
		telephone= aTelephone;
	}
	public void setUsername(String aUsername)
	{
		username= aUsername;
	}
}