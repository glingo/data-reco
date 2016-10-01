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

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.wrapping.*;

public class TextAreaUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JTextArea aTextArea= (JTextArea) getJComponent();

		Tag theTag= new Tag("textarea");
		theTag.setNeedsClosure(true);

		theTag.addAttribute(new TagAttribute("name", theComponent.getName()));

		if (aTextArea.getWidth() + aTextArea.getHeight() > 0)
			theTag.addAttribute(new TagAttribute("style", "position:absolute; width:" + aTextArea.getWidth() + "; height:" + aTextArea.getHeight()));
			
		theTag.getTheMarkupContainer().addElement(new TagContent(aTextArea.getText()));

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JTextArea", aTextArea);
		theTemplate.addElement(new IdTagTemplateElement("theTextArea", theTag));

		theContribManager.doContribution(theComponent, theTemplate, theTag, "");
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();

			if (theEvent.getType().equals("update"))
			{
				JTextArea aTextArea= (JTextArea) ((ComponentWrapper)theEvent.getSource()).getWrappedComponent();
				aTextArea.setText((String) theEvent.getParameters()[0]);
			}
		}
	}
}