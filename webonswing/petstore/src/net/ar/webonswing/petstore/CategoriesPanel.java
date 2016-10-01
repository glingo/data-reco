package net.ar.webonswing.petstore;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.model.*;

public class CategoriesPanel extends JPanel
{
	public CategoriesPanel (List aCategoriesList, Class aLabelContributorClass, boolean withLabelText)
	{
		List categories= aCategoriesList;
		for (Iterator i= categories.iterator(); i.hasNext();)
		{
			final Category category= ((Category) i.next());
			
			JLabel label= new JLabel(withLabelText ? category.getName() : "");
			WosFramework.assignContributor(label, aLabelContributorClass);

			label.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent anEvent)
				{
					CategoryView categoryView= new CategoryView(category);
					categoryView.setModal(true);
					WosFramework.showChildWindow(CategoriesPanel.this.getTopLevelAncestor(), categoryView);
				}
			});

			add(label).setName(category.getName().toLowerCase());
		}
	}
}
