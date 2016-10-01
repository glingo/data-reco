package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.contributors.*;
import net.ar.webonswing.petstore.model.*;

public class Confirmation extends ApplicationFrame
{
	protected Order order;

	public Confirmation()
	{
		WosFramework.assignContributor(this, new HibernateObjectPersistenceContributor("order"));
	}

	public Confirmation(Order anOrder)
	{
		this();
		
		setOrder(anOrder);
		init();
	}

	protected JComponent getBody()
	{
		JPanel panel= new JPanel();
		panel.add(new JLabel("Order Confimation")).setName("title");
		panel.add(new JLabel(("Your order has been submitted.\nThe order number is: " + order.getId()))).setName("message");
		panel.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("Message.main"));
		return panel;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order anOrder)
	{
		this.order= anOrder;
	}
}