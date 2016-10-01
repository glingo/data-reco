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

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;

public class PropagateTemplateLayoutByOrder extends TemplateLayout implements LayoutManager2
{
	public PropagateTemplateLayoutByOrder(KeyPositionTemplate aTemplate)
	{
		super(aTemplate);
	}

	public void layoutContainer(Container aParent)
	{
		Component[] components= aParent.getComponents();
		for (int i= 0; i < components.length; i++)
		{
			Container theComponent= (Container) components[i];
			KeyPositionHtmlTemplate theHtmlTemplate= (KeyPositionHtmlTemplate) theTemplate;

			if (theComponent.getComponentCount() > 0)
			{
				if (theComponent.getLayout() instanceof GridLayout || theComponent.getLayout() instanceof TemplateFlowLayout)
					setTemplateFlowLayout(theComponent);
				else
				{
					if (i < theHtmlTemplate.getElementCount())
					{
						HtmlTemplate theSubTemplate= theHtmlTemplate.getClonedSubTemplateAt(i);
						if (theSubTemplate != null)
							theComponent.setLayout(new PropagateTemplateLayoutByOrder(new KeyPositionHtmlTemplate(theSubTemplate)));
					}
					else
						theComponent.getParent().setLayout(new TemplateLayout(new HtmlDivsTemplate()));
				}
			}

			if (theComponent instanceof JComponent)
				if (i < theHtmlTemplate.getElementCount())
					theHtmlTemplate.addKey(theHtmlTemplate.getElementAt(i).getId().toString(), WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theComponent));
				else
					theComponent.getParent().setLayout(new TemplateLayout(new HtmlDivsTemplate()));
		}
	}

	protected void setTemplateFlowLayout(Container theContainer)
	{
		theContainer.setLayout(new TemplateFlowLayout());

		Component[] components= theContainer.getComponents();
		for (int i= 0; i < components.length; i++)
		{
			Container theComponent= (Container) components[i];

			if (theTemplate.getElementCount() > 0)
			{
				HtmlTemplate theHtmlTemplate= (HtmlTemplate) theTemplate.getElementAt(0).getContent();
				HtmlTemplate theSubTemplate= theHtmlTemplate.getClonedSubTemplate("");
				theComponent.setLayout(new PropagateTemplateLayoutByOrder(new KeyPositionHtmlTemplate(theSubTemplate)));
			}
		}
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