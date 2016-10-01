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

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.wrapping.*;

public class CheckBoxMenuItemUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JCheckBoxMenuItem theCheckBoxMenuItem= (JCheckBoxMenuItem) getJComponent();

		paintStateButton((ComponentWrapper) theComponent, theContribManager, theCheckBoxMenuItem, "CheckState", "JCheckBoxMenuItem", ItemListener.class);
	}

	public static void paintStateButton(ComponentWrapper aComponent, RenderingContributionContainer theContribManager, JMenuItem theMenuItem, String theStateGroup, String theJsObject, Class theListenerClass)
	{
		removeChangeListener(theMenuItem);

		String theMenuItemName= aComponent.getName();

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JCheckBoxMenuItem", (JComponent) aComponent.getWrappedComponent());

		Tag theTextTag= new Tag("span").addElementToContainer(new TagContent(theMenuItem.getText())).addAttribute("id", theMenuItemName);

		String thePrefix= "";
		if (theMenuItem.isSelected())
			thePrefix= theStateGroup + ".";

		HtmlTemplate theMenuDisabledTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, thePrefix + "aMenuDisabled");
		theMenuDisabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuItemName + ".disabled"));
		theMenuDisabledTemplate.addIdTagTemplateElement("aText", new Tag("span").addElementToContainer(new TagContent(theMenuItem.getText())));

		HtmlTemplate theMenuEnabledTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, thePrefix + "aMenuEnabled");
		Tag theDivTag= new Tag("div").addAttribute("id", theMenuItemName + ".enabled").addAttribute("style", "display:none");
		if (theMenuItem.getListeners(theListenerClass).length > 0)
		{
			String theJsEvent= theListenerClass.equals(ItemListener.class) ? "ItemEvent" : "ActionEvent";
			theDivTag.addAttribute(new TagAttribute("onclick", "ed.dispatch(new " + theJsEvent + "('" + theMenuItemName + "'));"));

		}
		theMenuEnabledTemplate.mergeFoundTag(theDivTag);
		theMenuEnabledTemplate.addIdTagTemplateElement("aText", theTextTag);

		if (!theMenuItem.isSelected())
			MenuItemUIContributor.setIcon(theMenuItem, theMenuDisabledTemplate, theMenuEnabledTemplate);

		String theConstructionScript= "new " + theJsObject + "('" + theMenuItemName + "')";

		theContribManager.doContribution(aComponent, new TextContent(theMenuDisabledTemplate.renderToText() + theMenuEnabledTemplate.renderToText()), theTextTag, new String[] { RemoteHelper.getListenersAdds(aComponent), theConstructionScript });
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JMenuItem theMenuItem= (JMenuItem) ((ComponentWrapper)theEvent.getSource()).getWrappedComponent();

			if (theEvent.getType().equals("itemStateChange"))
				theMenuItem.doClick();
		}
	}

	private static void removeChangeListener(JMenuItem theMenuItem)
	{
		ChangeListener[] theChangeListeners= (ChangeListener[]) theMenuItem.getListeners(ChangeListener.class);

		if (theChangeListeners.length > 0)
			theMenuItem.removeChangeListener(theChangeListeners[0]);
	}
}
