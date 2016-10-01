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

public class PasswordFieldUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JPasswordField aPasswordField= (JPasswordField) getJComponent();

		Tag theTag= new Tag("input");
		theTag.setNeedsClosure(false);

		theTag.addAttribute(new TagAttribute("type", "password"));
		theTag.addAttribute(new TagAttribute("name", theComponent.getName()));
		theTag.addAttribute(new TagAttribute("value", new String(aPasswordField.getPassword())));

		if (aPasswordField.getColumns() > 0)
			theTag.addAttribute(new TagAttribute("size", aPasswordField.getColumns()));

		if (aPasswordField.getWidth() > 0 && aPasswordField.getHeight() > 0)
			theTag.addAttribute(new TagAttribute("style", "position:absolute; width:" + aPasswordField.getWidth() + "; height:" + aPasswordField.getHeight()));

		if (!aPasswordField.isEnabled())
			theTag.addAttribute(new TagAttribute("DISABLED", ""));

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JTextField", aPasswordField);
		theTemplate.addElement(new IdTagTemplateElement("theTextField", theTag));

		theContribManager.doContribution(theComponent, theTemplate, theTag, "");
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();

			if (theEvent.getType().equals("update"))
			{
				JTextField aTextField= (JTextField) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();
				aTextField.setText((String) theEvent.getParameters()[0]);
			}
		}
	}
}