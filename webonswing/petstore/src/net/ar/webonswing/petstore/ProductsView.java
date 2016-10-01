package net.ar.webonswing.petstore;

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.petstore.actions.*;
import net.ar.webonswing.petstore.helpers.*;
import net.ar.webonswing.petstore.model.*;

public class ProductsView extends JPanel
{
	public ProductsView(Collection aProducts)
	{
		GenericTable table= new GenericTable(new String[] { "ProductID", "Name", "Description" });

		Collection products= aProducts;

		for (Iterator i= products.iterator(); i.hasNext();)
		{
			final Product product= (Product)i.next();

			JLabel productId= new JLabel(product.getProductId());
			productId.addMouseListener(new ProductViewAction(product));

			table.addRow(new JComponent[] { productId, new JLabel(product.getName()), new JLabel(product.getDescription()) });
		}

		add(table);
	}
}