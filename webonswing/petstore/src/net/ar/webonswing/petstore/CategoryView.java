package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.contributors.*;
import net.ar.webonswing.petstore.model.*;

public class CategoryView extends ApplicationFrame
{
	protected Category category;

	public CategoryView()
	{
		WosFramework.assignContributor(this, new HibernateObjectPersistenceContributor("category"));
	}

	public CategoryView(Category aCategory)
	{
		this();

		setCategory(aCategory);
		init();
	}

	protected JComponent getBody()
	{
		ProductsView productsView= new ProductsView(category.getProducts());
		productsView.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("CategoryView.main"));

		productsView.add(new JLabel(category.getName())).setName("name");
		productsView.add(new JLabel(category.getDescription())).setName("description");

		return productsView;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category aCategory)
	{
		this.category= aCategory;
	}
}