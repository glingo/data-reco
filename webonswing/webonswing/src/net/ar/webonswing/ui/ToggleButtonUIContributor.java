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

public class ToggleButtonUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JToggleButton aToggleButton= (JToggleButton) getJComponent();

		Tag theInputTag= new Tag("input").addAttribute("type", "button").addAttribute("value", aToggleButton.getText()).addAttribute("class", "buttonUp").setNeedsClosure(false);
		theInputTag.addAttribute(new TagStyleAttribute().addElement("width", aToggleButton.getWidth()).addElement("height", aToggleButton.getHeight()));

		if (aToggleButton.isSelected())
			theInputTag.addAttribute(new TagAttribute("CHECKED", ""));

		Tag theHiddenTag= new Tag("input").addAttribute("type", "hidden").setNeedsClosure(false);

		theInputTag.addAttribute(new TagAttribute("onclick", "ed.dispatch(new ItemEvent('" + theComponent.getName() + "'));this.className= this.className == 'buttonUp' ? 'buttonDown' : 'buttonUp';"));
		theHiddenTag.addAttribute("name", theComponent.getName()).addAttribute("value", aToggleButton.isSelected() ? "on" : "off");

		Tag theTextTag= new Tag("span");

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JCheckBox", aToggleButton);

		theTemplate.addElement(new IdTagTemplateElement("theHiddenField", theHiddenTag, theHiddenTag));
		theTemplate.addElement(new IdTagTemplateElement("theButton", theInputTag, theInputTag));
		theTemplate.addElement(new IdTagTemplateElement("theText", theTextTag, theTextTag));

		theContribManager.doContribution(theComponent, theTemplate, theInputTag, getInitScript(theComponent));
	}

	public String getInitScript(VisualComponent aComponent)
	{
		StringBuffer theScript= new StringBuffer();

		if (!theComponent.getName().equals("unnamed"))
		{
			theScript.append("getComponent('" + theComponent.getName() + "').addListener(new ChangeCheckStateListener());");
			theScript.append(RemoteHelper.getListenersAdds(aComponent));
		}

		return theScript.toString();
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JCheckBox theCheckBox= (JCheckBox) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (theEvent.getType().equals("update"))
				if ((theCheckBox.getSelectedObjects() != null) != theEvent.getParameters()[0].equals("on"))
					theCheckBox.doClick();
		}
	}
}