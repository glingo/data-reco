// WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package net.ar.webonswing.swing.layouts;

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;

public class PropagateTemplateLayoutByName extends TemplateLayout implements LayoutManager2
{
	protected LayoutManager theOriginalLayout;
	protected boolean completed;

	public PropagateTemplateLayoutByName(KeyPositionTemplate aTemplate)
	{
		this(aTemplate, true);
	}

	public PropagateTemplateLayoutByName(KeyPositionTemplate aTemplate, boolean isCompleted)
	{
		super(aTemplate);

		completed= isCompleted;
	}

	public PropagateTemplateLayoutByName(KeyPositionTemplate aTemplate, LayoutManager anOriginalLayout, boolean isCompleted)
	{
		this(aTemplate, isCompleted);

		theOriginalLayout= anOriginalLayout;
	}

	public void layoutContainer(Container aParent)
	{
		KeyPositionHtmlTemplate theHtmlTemplate= (KeyPositionHtmlTemplate)theTemplate;
		Component[] components= aParent.getComponents();

		for (int i= 0; i < components.length; i++)
		{
			boolean tagFound= false;
			Container theOriginalComponent= null;
			Container theComponent= (Container)components[i];
			theComponent.setBounds(0, 0, 0, 0);

			String theName= theComponent.getName();

			if (theName == null || theName.equals(""))
			{
				theOriginalComponent= theComponent;
				theComponent= (JComponent)theComponent.getComponent(0);

				if (theComponent instanceof JViewport)
					theComponent= (JComponent)((JViewport)theComponent).getView();
				else
					theOriginalComponent.setLayout(new TemplateFlowLayout());

				theOriginalComponent.setName(theComponent.getName());

				theName= theComponent.getName();
			}

			if (theName != null)
			{
				LayoutManager theLayout= theComponent.getLayout();
				boolean replaceGridLayout= theLayout instanceof GridLayout && (((GridLayout)theLayout).getRows() == 0 || ((GridLayout)theLayout).getColumns() == 0);

				if (replaceGridLayout || theLayout instanceof TemplateFlowLayout)
				{
					KeyPositionHtmlTemplate theClonedTemplate= new KeyPositionHtmlTemplate(((KeyPositionHtmlTemplate)theTemplate).getClonedSubTemplate(theComponent.getName()));
					theComponent.setLayout(new PropagateTemplateLayoutByName(theClonedTemplate, new TemplateFlowLayout(), completed));
				}
				else
				{
					HtmlTemplate theSubTemplate= null;

					if (theName == null || theName.trim().equals(""))
						theSubTemplate= theHtmlTemplate;
					else
					{
						tagFound= theHtmlTemplate.getSubTemplate(theName) != null;
						if (tagFound || completed)
							theSubTemplate= theHtmlTemplate.getClonedSubTemplate(theName);
					}

					if ((completed || tagFound) && theSubTemplate != null && theSubTemplate.getElementCount() > 0 && !(theLayout instanceof PropagateTemplateLayoutByName))
						try
						{
							theComponent.setLayout(new PropagateTemplateLayoutByName(new KeyPositionHtmlTemplate(theSubTemplate), completed));
						}
						catch (Exception e)
						{
						}
				}
			}

			if (theOriginalComponent != null)
				theComponent= theOriginalComponent;

			if ((theComponent instanceof JComponent) && theName != null && !theName.equals("") && (completed || tagFound))
				theHtmlTemplate.addKey(theName, WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theComponent));
		}
		
		
		if (theOriginalLayout != null)
			aParent.setLayout(theOriginalLayout);
		else
			aParent.setLayout(new TemplateLayout(theTemplate));
	}

	public float getLayoutAlignmentX(Container aTarget)
	{
		return 0;
	}

	public float getLayoutAlignmentY(Container aTarget)
	{
		return 0;
	}

	public void invalidateLayout(Container aTarget)
	{
	}

	public Dimension maximumLayoutSize(Container aTarget)
	{
		return new Dimension(640, 480);
	}

	public void addLayoutComponent(Component aComponent, Object aConstraints)
	{
	}
}