package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.petstore.model.*;
import net.ar.webonswing.ui.*;

public class Border extends JPanel
{
	protected JComponent body;
	protected Customer customer;
	private Cart cart;
	private ApplicationFrame frame;
	protected Header header;
	protected CategoriesPanel navigation;
	
	public Border ()
	{
	}

	public Border(JComponent aBody, Customer aCustomer, Cart aCart, ApplicationFrame aFrame)
	{
		customer= aCustomer;
		body= aBody;
		cart= aCart;
		frame= aFrame;
		
		init();
	}

	protected void init()
	{
		header= new Header(customer, cart, frame);
		header.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("Header"));

		navigation= new CategoriesPanel(frame.getDao().getCategories(), LabelUIContributor.class, true);
		navigation.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("Navigation"));

		add(header).setName("header");
		add(body).setName("body");
		add(navigation).setName("navigation");
		setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("Border.main"));
	}
	
	public JComponent getBody()
	{
		return body;
	}
	public void setBody(JComponent aBody)
	{
		this.body= aBody;
	}
	public Header getHeader()
	{
		return header;
	}
	public void setHeader(Header aHeader)
	{
		this.header= aHeader;
	}
	public CategoriesPanel getNavigation()
	{
		return navigation;
	}
	public void setNavigation(CategoriesPanel aNavigation)
	{
		this.navigation= aNavigation;
	}
}