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
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.swing.layouts.*;
import net.ar.webonswing.wrapping.*;

public class SplitPaneUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JSplitPane theSplitPane= (JSplitPane) getJComponent();

		Template theTemplate= theSplitPane.getLayout() instanceof TemplateLayout ? ((TemplateLayout) theSplitPane.getLayout()).getTemplate() : new HtmlDivsTemplate();

		StringBuffer theInitScript= new StringBuffer();

		Component[] theComponents= theSplitPane.getComponents();
		for (int i= 0; i < theComponents.length; i++)
		{
			Container theSubContainer= (Container) theComponents[i];
			VisualComponent theChildComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theSubContainer);
			
			if (theSubContainer.isVisible() || theSubContainer instanceof JPanel)
			{
				theTemplate.addElement(new IdTagTemplateElement(WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theSubContainer), theContribManager.getContainerRenderer().render(theChildComponent), theContribManager.getComponentIdTag(theChildComponent)));
				theInitScript.append(theContribManager.getComponentInitScripts(theChildComponent)[0]);
			}
		}

		theContribManager.doContribution(theComponent, theTemplate, null, theInitScript.toString());
	}
}
