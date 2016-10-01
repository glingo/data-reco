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

package net.ar.webonswing.ui;

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.swing.layouts.*;
import net.ar.webonswing.wrapping.*;

public class ViewPortUIContributor  extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JViewport aViewPort= (JViewport) getJComponent();

		Template theTemplate= aViewPort.getLayout() instanceof TemplateLayout ? ((TemplateLayout) aViewPort.getLayout()).getTemplate() : new HtmlDivsTemplate();
		StringBuffer theScript= new StringBuffer();

		Tag theTag= new Tag("div");
		theTag.setNeedsClosure(true);
		theTag.addAttribute(new TagAttribute("name", theComponent.getName()));
		theTag.addAttribute("scrollleft", "0");
		theTag.addAttribute("scrolltop", "0");
		theTag.addAttribute("scrollbottom", "0");
		TagStyleAttribute theStyle= new TagStyleAttribute();
		theStyle.addElement("position", "relative");

		theStyle.addElement("width", aViewPort.getWidth());
		theStyle.addElement("height", aViewPort.getHeight());
		
		theStyle.addElement("border", "0px solid");
		theStyle.addElement("overflow", "auto");
		theTag.addAttribute(theStyle);


		Component[] theComponents= aViewPort.getComponents();
		for (int i= 0; i < theComponents.length; i++)
		{
			Container theSubContainer= (Container) theComponents[i];

			if (theSubContainer.isVisible() || theSubContainer instanceof JPanel)
			{
				VisualComponent theChildComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theSubContainer);
				
				theTemplate.addElement(new IdTagTemplateElement(theChildComponent, theContribManager.getContainerRenderer().render(theChildComponent), theContribManager.getComponentIdTag(theChildComponent)));
				theScript.append(theContribManager.getComponentInitScripts(theChildComponent)[0]);
			}
		}
		
		theTag.addTextToContainer(new ContentRenderer(theTemplate).getResult());

		theContribManager.doContribution(theComponent, theTag, theTag, theScript.toString());
	}
}