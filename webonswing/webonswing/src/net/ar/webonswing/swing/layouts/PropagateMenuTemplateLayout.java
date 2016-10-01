//WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package net.ar.webonswing.swing.layouts;

import java.awt.*;
import java.io.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.render.templates.*;

/**
 * Este LayoutManager propaga el contenido de una plantilla tipo "menu" a todos los subcomponentes de un JMenuBar.
 * Va recorriendo recursivamente todos los subcomponentes y segun el tipo de elemento de menu que sea le
 * copia la subplantilla correspondiente extraida del template proporcionado en la construccion.
 * 
 * @author Fernando Damian Petrola
 */
public class PropagateMenuTemplateLayout implements LayoutManager, Serializable
{
	protected KeyPositionTemplate theTemplate;

	public PropagateMenuTemplateLayout(KeyPositionTemplate aTemplate)
	{
		theTemplate= aTemplate;
	}

	public void addLayoutComponent(String name, Component comp)
	{
	}

	public void removeLayoutComponent(Component comp)
	{
	}

	public Dimension preferredLayoutSize(Container parent)
	{
		return null;
	}

	public Dimension minimumLayoutSize(Container parent)
	{
		return null;
	}

	public void layoutContainer(Container parent)
	{
		JMenuBar theMenuBar= (JMenuBar) parent;
		theMenuBar.setLayout(new TemplateLayout(WosFramework.getInstance().getTemplateManager().getKeyPositionTemplateForSubTemplate(theTemplate, "")));

		Component[] theMenus= theMenuBar.getComponents();
		for (int i= 0; i < theMenus.length; i++)
			propagateToMenu((JMenu) theMenus[i]);
	}
	
	private void propagateToMenu (JMenu aMenu)
	{
		aMenu.setLayout(new TemplateLayout(WosFramework.getInstance().getTemplateManager().getKeyPositionTemplateForSubTemplate(theTemplate, "theItems")));
		propagateToPopupMenu(aMenu.getPopupMenu());
	}
	
	private void propagateToPopupMenu (JPopupMenu aPopupMenu)
	{
		aPopupMenu.setLayout(new TemplateLayout(WosFramework.getInstance().getTemplateManager().getKeyPositionTemplateForSubTemplate(theTemplate, "theItems.JPopupMenu")));

		Component[] theMenuItems= aPopupMenu.getComponents();
		for (int i= 0; i < theMenuItems.length; i++)
			propagateToMenuItem((JComponent) theMenuItems[i]);
	}

	private void propagateToMenuItem(JComponent aItem)
	{
		if (aItem instanceof JMenu)
		{
			JMenu theMenu= (JMenu) aItem;
			theMenu.setLayout(new TemplateLayout(WosFramework.getInstance().getTemplateManager().getKeyPositionTemplateForSubTemplate(theTemplate, "theItems.JPopupMenu.thePopupMenu.theMenuItem.MenuWithChilds")));
			propagateToPopupMenu(theMenu.getPopupMenu());
		}
		else
		{
			aItem.setLayout(new TemplateLayout(WosFramework.getInstance().getTemplateManager().getKeyPositionTemplateForSubTemplate(theTemplate, "theItems.JPopupMenu.thePopupMenu.theMenuItem")));
		}
	}	
}
