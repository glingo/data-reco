
package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.skins.*;
import net.ar.webonswing.petstore.model.*;
import net.ar.webonswing.petstore.persistence.*;

public abstract class ApplicationFrame extends JDialog
{
	public static final String CART_KEY= "cart";
	public static final String SKIN_MANAGER= "skin-manager";
	public static final String USER_KEY= "user";

	public static Skin getCurrentSkin()
	{
		SimpleSkinManager skinManager= getSkinManager();
		return skinManager.getCurrentSkin();
	}

	public static SimpleSkinManager getSkinManager()
	{
		SimpleSkinManager skinManager= (SimpleSkinManager) WosFramework.getSessionContext().get(SKIN_MANAGER);
		if (skinManager == null)
		{
			skinManager= (SimpleSkinManager) WosHelper.restoreObjectFromXml("/net/ar/webonswing/config/skin-manager.config.xml");
			skinManager.setCurrentSkin("MsSkin");
			WosFramework.getSessionContext().put(SKIN_MANAGER, skinManager);
		}
		
		return skinManager;
	}

	public boolean setCurrentSkin (String aSkinName)
	{
		boolean result= getSkinManager().setCurrentSkin(aSkinName);
		border=null;
		init();
		
		return result;
	}
	
	protected Border border;
	protected Dao dao= new Dao();
	protected abstract JComponent getBody();

	public Border getBorder()
	{
		return border;
	}

	public Cart getCart()
	{
		Cart cart= (Cart) WosFramework.getSessionContext().get(CART_KEY);

		if (cart == null)
		{
			cart= new Cart();
			WosFramework.getSessionContext().put(CART_KEY, cart);
		}

		return cart;
	}

	public Customer getCurrentCustomer()
	{
		return (Customer) WosFramework.getSessionContext().get(USER_KEY);
	}

	public Dao getDao()
	{
		return dao;
	}

	public void init()
	{
		if (border == null)
			border= new Border(getBody(), getCurrentCustomer(), getCart(), this);

		setContentPane(border);
	}

	public void setBorder(Border aBorder)
	{
		this.border= aBorder;
	}

	public void setCurrentCustomer(Customer aCustomer)
	{
		if (aCustomer != null && aCustomer.getId() != null)
			WosFramework.getSessionContext().put(USER_KEY, aCustomer);
	}
}