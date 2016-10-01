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
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public class EditableUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JEditable anEditable= (JEditable) getJComponent();

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JEditable", anEditable);

		Component[] theComponents= anEditable.getComponents();
		StringBuffer theInitScript= new StringBuffer();

		for (int i= 0; i < theComponents.length; i++)
		{
			JComponent theSubComponent= (JComponent) theComponents[i];
			VisualComponent theChildComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theSubComponent);

			if (theSubComponent.isVisible() || theSubComponent instanceof JPanel)
			{
				Visitable theRendering= theContribManager.getContainerRenderer().render(theChildComponent);
				Tag theIdTag= theContribManager.getComponentIdTag(theChildComponent);

				theTemplate.addElement(new IdTagTemplateElement(theChildComponent, theRendering, theIdTag));
			}

			theInitScript.append(theContribManager.getComponentInitScripts(theChildComponent)[0]);
		}

		theContribManager.doContribution(theComponent, theTemplate, null, theInitScript.toString());
	}
}
