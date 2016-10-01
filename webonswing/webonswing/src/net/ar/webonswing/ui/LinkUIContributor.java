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

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.pages.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.wrapping.*;

public class LinkUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JLink aLink= (JLink) getJComponent();

		String theUrl= aLink.getUrl();

		if (theUrl == null)
		{
			String theWindowName= "";

			if (aLink.getRootPaneContainer() == null)
				theWindowName= aLink.getRootPaneContainerClass().getName();
			else
				theWindowName= aLink.getRootPaneContainer().getClass().getName();

			theUrl= ((DefaultHtmlPage) WosFramework.getInstance().getPageManager().getPageForWindowName(theWindowName)).getUrl();
		}

		Tag theTag= new Tag("a");

		if (!theUrl.equals(""))
			theTag.addAttribute(new TagAttribute("href", theUrl));
		else
			theTag.addAttribute(new TagAttribute("style", "cursor: hand"));

		theTag.addAttribute(new TagAttribute("name", theComponent.getName()));
		theTag.getTheMarkupContainer().addElement(new TagContent(aLink.getLabel()));

		RemoteHelper.addMouseListeners(aLink, theComponent.getName(), theTag);

		if (aLink.getListeners(MouseMotionListener.class).length > 0)
			theTag.addAttribute(new TagAttribute("onmouseover", "ed.dispatch(new MouseMovedEvent(this.name));"));

		HtmlTemplate theTemplate= (HtmlTemplate) WosSwingHelper.getTemplateForComponent("JLink", aLink);
		HtmlTemplate theLinkTemplate= theTemplate.getSubTemplate("theLink");
		theLinkTemplate.setFoundTag(theTag);

		theContribManager.doContribution(theComponent, theTemplate, theTag, RemoteHelper.getComponentInitScript(theComponent));
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JLink theLabel= (JLink) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (theEvent.getType().equals("mouseClicked"))
				RemoteHelper.fireClickEvent(theLabel);
		}
	}
}