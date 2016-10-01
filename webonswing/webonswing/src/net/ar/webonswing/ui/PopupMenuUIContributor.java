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
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.wrapping.*;

public class PopupMenuUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JPopupMenu aPopupMenu= (JPopupMenu) getJComponent();

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JPopupMenu", aPopupMenu);
		HtmlTemplate theMainTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, "thePopupMenu");

		if (!theComponent.getName().equals(""))
		{
			Tag theMainTag= new Tag("span").addAttribute("id", theComponent.getName());
			theMainTemplate.mergeFoundTagKeepingName(theMainTag);
		}

		StringBuffer theResult= new StringBuffer();
		StringBuffer theScript= new StringBuffer("new JPopupMenu('" + theComponent.getName() + "')");
		StringBuffer theMenuItemsInitScripts= new StringBuffer();

		Component[] theComponents= aPopupMenu.getComponents();
		for (int i= 0; i < theComponents.length; i++)
		{
			VisualComponent theMenuItemComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theComponents[i]);

			String theItemName= theMenuItemComponent.getName() + ".tab";

			HtmlTemplate theItemTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.anItem");
			theItemTemplate.mergeFoundTagKeepingName(new Tag("span").addAttribute("id", theItemName));
			theItemTemplate.addIdTagTemplateElement("aContent", new ContentRenderer(theContribManager.getContainerRenderer().render(theMenuItemComponent)).getResult().toString());

			theResult.append(theItemTemplate.renderToText());
			theResult.append(createSeparator(theTemplate, theMenuItemComponent.getName()));

			String theMenuItemScript= theContribManager.getComponentInitScripts(theMenuItemComponent)[1];
			theMenuItemsInitScripts.append(theContribManager.getComponentInitScripts(theMenuItemComponent)[0]);

			if (theMenuItemScript.length() > 0)
				theScript.append(".add(" + theMenuItemScript + ")");
		}

		String theBegin= getBegin(theComponent.getName(), theTemplate);
		String theEnd= getEnd(theComponent.getName(), theTemplate);

		theMainTemplate.addIdTagTemplateElement("theMenuItem", new TextContent(theBegin + theResult + theEnd));

		theContribManager.doContribution(theComponent, theTemplate, null, new String[] { theScript.toString(), theMenuItemsInitScripts.toString()});
	}

	private String createSeparator(Template theTemplate, String theName)
	{
		HtmlTemplate theSeparatorLeftTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.aSeparatorLeftUp");
		HtmlTemplate theSeparatorTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.aSeparatorDisabled");
		HtmlTemplate theSeparatorRightTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.aSeparatorRightDown");

		theSeparatorLeftTemplate.mergeFoundTag(new Tag("div").addAttribute("style", "display:none").addAttribute("id", theName + ".separator.left"));
		theSeparatorRightTemplate.mergeFoundTag(new Tag("div").addAttribute("style", "display:none").addAttribute("id", theName + ".separator.right"));
		theSeparatorTemplate.mergeFoundTag(new Tag("div").addAttribute("style", "display:block").addAttribute("id", theName + ".separator"));

		HtmlTemplate theItemTemplate= getItemTemplate(theTemplate);
		theItemTemplate.addIdTagTemplateElement("aContent", theSeparatorLeftTemplate.renderToText() + theSeparatorTemplate.renderToText() + theSeparatorRightTemplate.renderToText());

		return theItemTemplate.renderToText();
	}

	private HtmlTemplate getItemTemplate(Template theTemplate)
	{
		return HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.anItem");
	}

	private String getBegin(String theMenuName, Template theTemplate)
	{
		HtmlTemplate theMenuBarBeginDisabledTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.aBeginDisabled");
		theMenuBarBeginDisabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuName + ".begin.disabled"));
		HtmlTemplate theMenuBarBeginEnabledTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.aBeginEnabled");
		theMenuBarBeginEnabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuName + ".begin.enabled"));
		HtmlTemplate theItemTemplate= getItemTemplate(theTemplate);
		theItemTemplate.addIdTagTemplateElement("aContent", theMenuBarBeginDisabledTemplate.renderToText() + theMenuBarBeginEnabledTemplate.renderToText());

		return theItemTemplate.renderToText();
	}

	private String getEnd(String theMenuName, Template theTemplate)
	{
		HtmlTemplate theMenuBarBeginDisabledTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.anEndDisabled");
		theMenuBarBeginDisabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuName + ".end.disabled"));
		HtmlTemplate theMenuBarBeginEnabledTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "thePopupMenu.theMenuItem.anEndEnabled");
		theMenuBarBeginEnabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuName + ".end.enabled"));
		HtmlTemplate theItemTemplate= getItemTemplate(theTemplate);
		theItemTemplate.addIdTagTemplateElement("aContent", theMenuBarBeginDisabledTemplate.renderToText() + theMenuBarBeginEnabledTemplate.renderToText());

		return theItemTemplate.renderToText();
	}
}
