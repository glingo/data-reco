// WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package net.ar.webonswing.ui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.swing.layouts.*;
import net.ar.webonswing.wrapping.*;

public class LabelUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JLabel aLabel= (JLabel) getJComponent();

		RemoteHelper.removeToolTipListeners(aLabel);

		String theComponentName= theComponent.getName();
		HtmlTemplate theTemplate= (HtmlTemplate) WosSwingHelper.getTemplateForComponent("JLabel", aLabel);
		String theAlign= "left";

		Tag theAnchorTag= null;
		Tag theImageTag= null;
		Tag theIdTag= null;
		if (aLabel.getText() != null)
		{
			theAnchorTag= new Tag("span");
			theAnchorTag.addAttribute(new TagAttribute("id", theComponentName));			
			theAnchorTag.addElementToContainer(new TagContent(aLabel.getText()));
			Color theBackGroundColor= aLabel.getBackground();

			if (theBackGroundColor != null && !theBackGroundColor.equals(UIManager.getColor("Label.background")))
				theAnchorTag.addAttribute(new TagStyleAttribute().addElement("BACKGROUND-COLOR", "#" + WosSwingHelper.getHTMLColorString(theBackGroundColor)));

			theIdTag= theAnchorTag;
		}

		if (aLabel.getIcon() != null)
		{
			theImageTag= new Tag("img");
			theAlign= "center";

			theImageTag.setNeedsClosure(false);
			theImageTag.addAttribute(new TagAttribute("name", theComponentName));
			theImageTag.addAttribute(new TagAttribute("id", theComponentName));			
			theImageTag.addAttribute(new TagAttribute("src", WosFramework.getInstance().getContextPath() + "/" + ((ImageIcon) aLabel.getIcon()).getDescription()));

			if (aLabel.getToolTipText() != null)
				theImageTag.addAttribute(new TagAttribute("alt", aLabel.getToolTipText()));

			theIdTag= theImageTag;
		}

		RemoteHelper.addMouseListeners(aLabel, theComponentName, theIdTag);

		if (aLabel.getLayout() instanceof TemplateLayout)
		{
			theTemplate.addElement(new IdTagTemplateElement("theLabel", theAnchorTag));

			HtmlTemplate theImageTemplate= theTemplate.getSubTemplate("theImage");
			if (theImageTemplate != null && theImageTag != null)
				theImageTemplate.getFoundTag().addAllTagAttributes(theImageTag);
		}
		else
		{
			HtmlTemplate theAnchorTemplate= theTemplate.getSubTemplate("theTable.theTd.theLabel");
			HtmlTemplate theImageTemplate= theTemplate.getSubTemplate("theTable.theTd.theImage");
			HtmlTemplate theTableTemplate= theTemplate.getSubTemplate("theTable");
			HtmlTemplate theTdTemplate= theTemplate.getSubTemplate("theTable.theTd");

			if (theAnchorTag != null)
				theAnchorTemplate.mergeFoundTag(theAnchorTag);
			else
				theAnchorTemplate.deleteFoundTag();

			if (theImageTag != null)
				theImageTemplate.getFoundTag().addAllTagAttributesAndName(theImageTag);
			else
				theImageTemplate.deleteFoundTag();

			if (aLabel.getWidth() != 0 && aLabel.getHeight() != 0)
			{
				Tag theTableTag= theTableTemplate.getFoundTag();
				theTableTag.addAttribute("width", aLabel.getWidth());
				theTableTag.addAttribute("height", aLabel.getHeight());
				theTdTemplate.getFoundTag().addNewTagAttributes(new Tag("td").addAttribute("align", theAlign));
			}
			else
			{
				theTdTemplate.deleteFoundTag();
				theTemplate= theTdTemplate;
			}
		}

		theContribManager.doContribution(theComponent, theTemplate, theIdTag, RemoteHelper.getComponentInitScript(theComponent));
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JLabel theLabel= (JLabel) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (theEvent.getType().equals("mouseClicked"))
				RemoteHelper.fireClickEvent(theLabel);
		}
	}
}