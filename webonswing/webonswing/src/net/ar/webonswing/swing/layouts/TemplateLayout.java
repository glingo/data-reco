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
import java.util.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

/**
 * Permite ubicar los subcomponentes por medio de una clave, y luego, cuando se invoca a "doLayout" es asociado a un "hueco" de la plantilla.
 * 
 * @author Fernando Damian Petrola
 */
public class TemplateLayout implements LayoutManager, Serializable
{
	protected Map theComponents;
	protected Template theTemplate;

	public TemplateLayout(KeyPositionTemplate aTemplate)
	{
		theTemplate= aTemplate;
		theComponents= new HashMap();
	}

	public TemplateLayout(Template aTemplate)
	{
		theTemplate= aTemplate;
		theComponents= new HashMap();
	}

	public void addLayoutComponent(String name, Component comp)
	{
		theComponents.put(name, comp);
	}

	public void layoutContainer(Container parent)
	{
		for (Iterator i= theComponents.keySet().iterator(); i.hasNext();)
		{
			String theKey= i.next().toString();
			Component theComponent= (Component) theComponents.get(theKey);

			VisualComponent theComponentWrapper= null;

			if (WosFramework.isActive())
				theComponentWrapper= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theComponent);

			if (theComponentWrapper != null || !WosFramework.isActive())
			{
				Rectangle theBounds= ((KeyPositionTemplate) theTemplate).addKey(theKey, theComponentWrapper);
				theComponent.setBounds(theBounds);
			}
		}
	}

	public String getName(Component aComponent)
	{
		for (Iterator i= theComponents.keySet().iterator(); i.hasNext();)
		{
			String theKey= i.next().toString();
			Component theComponent= (Component) theComponents.get(theKey);

			if (theComponent == aComponent)
				return theKey;
		}

		return null;
	}

	public void removeLayoutComponent(Component comp)
	{
		for (Iterator i= theComponents.keySet().iterator(); i.hasNext();)
		{
			String theKey= i.next().toString();
			Component theComponent= (Component) theComponents.get(theKey);

			if (theComponent == comp)
				i.remove();
		}
	}

	public Dimension preferredLayoutSize(Container parent)
	{
		return new Dimension(1000, 1000);
	}

	public Dimension minimumLayoutSize(Container parent)
	{
		return new Dimension(1000, 1000);
	}

	public Template getTemplate()
	{
		try
		{
			return (Template) ((PublicCloneable) theTemplate).clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new WebOnSwingException("Cannot clone the template: " + this, e);
		}
	}

	public void setTemplate(Template aTemplate)
	{
		this.theTemplate= (KeyPositionTemplate) aTemplate;
	}

}
