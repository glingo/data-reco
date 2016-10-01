
package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.petstore.contributors.*;

public class Home extends ApplicationFrame
{
	public Home()
	{
		init();
	}

	protected JComponent getBody()
	{
		CategoriesPanel categoriesPanel= new CategoriesPanel(getDao().getCategories(), LabelUIContributor.class, false);

		JPanel map= new JPanel();
		map.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("Home.main"));
		map.add(categoriesPanel).setName("estoremap");

		return map;
	}

}