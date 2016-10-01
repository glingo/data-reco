package net.ar.webonswing.petstore;

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.contributors.*;
import net.ar.webonswing.swing.layouts.*;

public class Search extends ApplicationFrame
{
	protected List products;
	private String keyword;

	public Search()
	{
		WosFramework.assignContributor(this, SearchPersistenceContributor.class);
	}

	public Search(List aProducts, String aKeyword)
	{
		this();

		products= aProducts;
		keyword= aKeyword;
		init();
	}

	protected JComponent getBody()
	{
		JPanel conditional= new JPanel();

		if (!products.isEmpty())
		{
			ProductsView productsView= new ProductsView(products);

			conditional.setName("products");
			conditional.add(productsView);
		}
		else
			conditional.setName("noProducts");

		conditional.add(new JLabel(keyword)).setName("keyword");

		JPanel panel= new JPanel();
		panel.add(conditional);
		panel.setLayout(new PropagateTemplateLayoutByName(ApplicationFrame.getCurrentSkin().getCurrentTemplateFor("Search.main"), new TemplateFlowLayout(), true));

		return panel;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String aKeyword)
	{
		this.keyword= aKeyword;
	}

	public List getProducts()
	{
		return products;
	}

	public void setProducts(List aProducts)
	{
		this.products= aProducts;
	}
}