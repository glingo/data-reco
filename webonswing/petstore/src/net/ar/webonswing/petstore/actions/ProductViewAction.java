package net.ar.webonswing.petstore.actions;

import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.*;
import net.ar.webonswing.petstore.model.*;

public class ProductViewAction extends MouseAdapter
{
	protected final Product product;

	public ProductViewAction(Product product)
	{
		super();
		this.product= product;
	}

	public void mouseClicked(MouseEvent e)
	{
		ProductView productView= new ProductView(product);
		productView.setModal(true);
		WosFramework.showChildWindow(((JComponent)e.getSource()).getTopLevelAncestor(), productView);
	}
}