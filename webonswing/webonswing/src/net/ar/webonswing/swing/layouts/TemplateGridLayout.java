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

import net.ar.webonswing.render.templates.*;

/**
 * Permite ubicar los elementos en lineas y columnas, se puede asociar una plantilla para las lineas pares y otra para las impares.
 * Estas plantillas deben tener un "hueco" con nombre "theContent" 
 * 
 * @author Fernando Damian Petrola
 */
public class TemplateGridLayout extends TemplateLayout 
{
	protected KeyPositionTemplate theSecondTemplate;
	protected Template theGridTemplate;

	public TemplateGridLayout(int aRows, int aCols, KeyPositionTemplate aFirstTemplate, KeyPositionTemplate aSecondTemplate)
	{
		super(aFirstTemplate);
		theSecondTemplate= aSecondTemplate;
		theGridTemplate= new DefaultTemplate(new TemplateGridBody(aRows, aCols, theTemplate, theSecondTemplate));
	}

	public TemplateGridLayout(int aRows, int aCols, KeyPositionTemplate aFirstTemplate)
	{
		super(aFirstTemplate);
		theSecondTemplate= aFirstTemplate;
		theGridTemplate= new DefaultTemplate(new TemplateGridBody(aRows, aCols, theTemplate, theSecondTemplate));
	}

	public void addLayoutComponent(String name, Component comp)
	{
	}

	public void removeLayoutComponent(Component comp)
	{
	}

	public Dimension preferredLayoutSize(Container parent)
	{
		return new Dimension(1000, 1000);
	}

	public Dimension minimumLayoutSize(Container parent)
	{
		return new Dimension(1000, 1000);
	}

	public void layoutContainer(Container parent)
	{
	}

	public Template getTemplate()
	{
		return theGridTemplate;
	}

	public void setTemplate(Template aTemplate)
	{
		super.setTemplate(aTemplate);
	}

}
