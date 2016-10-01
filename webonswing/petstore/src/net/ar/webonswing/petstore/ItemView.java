
package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.actions.*;
import net.ar.webonswing.petstore.contributors.*;
import net.ar.webonswing.petstore.model.*;

public class ItemView extends ApplicationFrame
{
	protected Item item;

	public ItemView()
	{
		WosFramework.assignContributor(this, new HibernateObjectPersistenceContributor("item"));
	}

	public ItemView(Item anItem)
	{
		this();

		setItem(anItem);
		init();
	}

	protected JComponent getBody()
	{
		JLabel image= new JLabel(new ImageIcon("net/ar/webonswing/petstore/" + getCurrentSkin().getImagesPath() + item.getImagePath() + getCurrentSkin().getDefaultImageExtension()));
		JLabel itemId= new JLabel(item.getItemId());
		JLabel description= new JLabel(item.getDescription());
		JLabel listPrice= new JLabel(item.getListPrice() + "");
		JLabel productDescription= new JLabel(item.getProduct().getDescription());

		JButton addToCartButton= new JButton("Add to cart");
		addToCartButton.addActionListener(new AddItemToCartAction(item, getCart()));

		JPanel panel= new JPanel();
		panel.add(image).setName("image");
		panel.add(itemId).setName("itemId");
		panel.add(description).setName("description");
		panel.add(productDescription).setName("productDescription");
		panel.add(listPrice).setName("listPrice");
		panel.add(addToCartButton).setName("addToCartButton");
		panel.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("ItemView.main"));

		return panel;
	}

	public Item getItem()
	{
		return item;
	}

	public void setItem(Item anItem)
	{
		this.item= anItem;
	}
}