package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.petstore.model.*;

public class CartViewPage extends ApplicationFrame
{
	protected CartView cartView;

	public CartViewPage()
	{
		cartView= new CartView(getCart());
		init();
	}

	public CartViewPage(Cart aCart)
	{
		cartView= new CartView(aCart);
		init();
	}

	protected JComponent getBody()
	{
		return cartView;
	}
}