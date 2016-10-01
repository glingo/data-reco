package net.ar.webonswing.petstore.actions;

import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.*;
import net.ar.webonswing.petstore.model.*;

public final class AddItemToCartAction implements ActionListener
{
	private final Item item;
	private Cart cart;

	public AddItemToCartAction(Item anItem, Cart aCart)
	{
		super();
		cart= aCart;
		this.item= anItem; 
	}

	public void actionPerformed(ActionEvent e)
	{
		cart.addItem(item, true);
		CartViewPage cartView= new CartViewPage(cart);
		cartView.setModal(true);
		WosFramework.showChildWindow(((JComponent)e.getSource()).getTopLevelAncestor(), cartView);
	}
}