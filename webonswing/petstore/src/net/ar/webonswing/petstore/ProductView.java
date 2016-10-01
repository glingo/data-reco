
package net.ar.webonswing.petstore;

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.actions.*;
import net.ar.webonswing.petstore.contributors.*;
import net.ar.webonswing.petstore.helpers.*;
import net.ar.webonswing.petstore.model.*;

public class ProductView extends ApplicationFrame
{
	protected Product product;

	public ProductView()
	{
		WosFramework.assignContributor(this, new HibernateObjectPersistenceContributor("product"));
	}

	public ProductView(Product aProduct)
	{
		this();

		setProduct(aProduct);
		init();
	}

	protected JComponent getBody()
	{
		GenericTable table= new GenericTable(new String[]{"ItemId", "ProductId", "Description", "ListPrice", "Add"});

		for (Iterator i= product.getItems().iterator(); i.hasNext();)
		{
			final Item item= (Item) i.next();

			JLabel itemId= new JLabel(item.getItemId());
			itemId.addMouseListener(new ShowItemAction(item));

			JButton addItemButton= new JButton("Add Item");
			addItemButton.addActionListener(new AddItemToCartAction(item, getCart()));

			table.addRow(new JComponent[]{itemId, new JLabel(product.getProductId()), new JLabel(item.getDescription()), new JLabel(item.getListPrice() + ""), addItemButton});
		}

		JPanel panel= new JPanel();
		panel.add(table);
		panel.add(new JLabel(product.getName())).setName("name");
		panel.add(new JLabel(product.getDescription())).setName("description");

		panel.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("ProductView.main"));

		return panel;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product aProduct)
	{
		this.product= aProduct;
	}
}