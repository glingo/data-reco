package net.ar.webonswing.petstore.actions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.*;
import net.ar.webonswing.petstore.model.*;
import net.sf.hibernate.*;

public final class CheckOutAction implements ActionListener
{
	private ApplicationFrame frame;

	public CheckOutAction(ApplicationFrame aFrame)
	{
		frame= aFrame;
	}

	public void actionPerformed(ActionEvent aE)
	{
		Transaction transaction= null;

		final Order order= new Order(frame.getCurrentCustomer());
		Cart cart= frame.getCart();

		for (Iterator i= cart.getCartItems().iterator(); i.hasNext();)
		{
			CartItem cartItem= (CartItem)i.next();
			order.add(cartItem.getItem(), cartItem.getQuantity());
		}

		frame.getDao().saveOrUpdate (order);

		cart.clear();

		JDialog confirmation= new Confirmation(order);
		WosFramework.showChildWindow(frame, confirmation);
	}
}