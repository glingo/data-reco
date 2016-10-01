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
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public class ToolBarUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theRenderingContribManager)
	{
		JToolBar aToolBar= (JToolBar) getJComponent();

		Template theTemplate= aToolBar.getLayout() instanceof TemplateLayout ? ((TemplateLayout) aToolBar.getLayout()).getTemplate() : new HtmlDivsTemplate();
		Component[] theComponents= aToolBar.getComponents();

		StringBuffer theInitScript= new StringBuffer();

		for (int i= 0; i < theComponents.length; i++)
		{
			Container theSubContainer= (Container) theComponents[i];
			VisualComponent theChildComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theSubContainer);

			if (theSubContainer.isVisible() || theSubContainer instanceof JPanel)
			{
				Visitable theRendering= theRenderingContribManager.getContainerRenderer().render(theChildComponent);
				Tag theIdTag= theRenderingContribManager.getComponentIdTag(theChildComponent);

				theTemplate.addElement(new IdTagTemplateElement(theChildComponent, theRendering, theIdTag));
			}

			theInitScript.append(theRenderingContribManager.getComponentInitScripts(theChildComponent)[0]);
		}

		Tag theDiv= new Tag("div").addAttribute("style", "border:10px solid #000000");
		theDiv.addTextToContainer(new ContentRenderer(theTemplate).getResult());

		theRenderingContribManager.doContribution(theComponent, theDiv, null, theInitScript.toString());
	}
}
