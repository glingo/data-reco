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
import net.ar.webonswing.managers.script.ScriptContributionContainer;
import net.ar.webonswing.managers.styles.StyleContributionContainer;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.swing.layouts.*;
import net.ar.webonswing.wrapping.*;

public class MenuBarUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JMenuBar aMenuBar= (JMenuBar)getJComponent();

		//TODO: revisar
		if (!(aMenuBar.getLayout() instanceof TemplateLayout))
		{
			aMenuBar.setLayout(new PropagateMenuTemplateLayout(WosFramework.getKeyPositionTemplateForName("WinMenu.JMenuBar")));
			aMenuBar.doLayout();
		}

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JMenuBar", aMenuBar);

		StringBuffer theBarResult= new StringBuffer();
		StringBuffer theScript= new StringBuffer();
		StringBuffer theMenusInitScripts= new StringBuffer();

		Component[] theComponents= aMenuBar.getComponents();
		for (int i= 0; i < theComponents.length; i++)
		{
			JComponent theJMenu= (JComponent)theComponents[i];
			VisualComponent theMenuComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theJMenu);

			String theJMenuName= theMenuComponent.getName();
			String theTabName= theJMenuName + ".tab";

			HtmlTemplate theItemTemplate= getItemTemplate(theTemplate);
			theItemTemplate.mergeFoundTagKeepingName(new Tag("span").addAttribute("id", theTabName));
			theItemTemplate.addIdTagTemplateElement("aContent", theContribManager.getContainerRenderer().render(theMenuComponent), theContribManager.getComponentIdTag(theMenuComponent));

			theBarResult.append(theItemTemplate.renderToText());
			theBarResult.append(createSeparator(theTemplate, theJMenuName));

			theScript.append(".add(" + theContribManager.getComponentInitScripts(theMenuComponent)[1] + ")");

			theMenusInitScripts.append(theContribManager.getComponentInitScripts(theMenuComponent)[0]);
		}

		String theBegin= getBegin(theComponent.getName(), theTemplate);
		String theEnd= getEnd(theComponent.getName(), theTemplate);
		theTemplate.addElement(new IdTagTemplateElement("theItems", new TextContent(theBegin + theBarResult + theEnd)));

		String theInitScript= "new JMenuBar('" + theComponent.getName() + "')" + theScript + ";" + theMenusInitScripts.toString();

		theContribManager.doContribution(theComponent, theTemplate, null, theInitScript);
	}

	private String createSeparator(Template theTemplate, String theName)
	{
		HtmlTemplate theSeparatorLeftTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theItems.aSeparatorLeftUp");
		HtmlTemplate theSeparatorTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theItems.aSeparatorDisabled");
		HtmlTemplate theSeparatorRightTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theItems.aSeparatorRightDown");

		theSeparatorLeftTemplate.mergeFoundTag(new Tag("div").addAttribute("style", "display:none").addAttribute("id", theName + ".separator.left"));
		theSeparatorRightTemplate.mergeFoundTag(new Tag("div").addAttribute("style", "display:none").addAttribute("id", theName + ".separator.right"));
		theSeparatorTemplate.mergeFoundTag(new Tag("div").addAttribute("style", "display:block").addAttribute("id", theName + ".separator"));

		HtmlTemplate theItemTemplate= getItemTemplate(theTemplate);
		theItemTemplate.addIdTagTemplateElement("aContent", theSeparatorLeftTemplate.renderToText() + theSeparatorTemplate.renderToText() + theSeparatorRightTemplate.renderToText());

		return theItemTemplate.renderToText();
	}

	private HtmlTemplate getItemTemplate(Template theTemplate)
	{
		return HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theItems.anItem");
	}

	private String getBegin(String theMenuBarName, Template theTemplate)
	{
		HtmlTemplate theMenuBarBeginDisabledTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theItems.aBeginDisabled");
		theMenuBarBeginDisabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuBarName + ".begin.disabled"));
		HtmlTemplate theMenuBarBeginEnabledTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theItems.aBeginEnabled");
		theMenuBarBeginEnabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuBarName + ".begin.enabled"));
		HtmlTemplate theItemTemplate= getItemTemplate(theTemplate);
		theItemTemplate.addIdTagTemplateElement("aContent", theMenuBarBeginDisabledTemplate.renderToText() + theMenuBarBeginEnabledTemplate.renderToText());

		return theItemTemplate.renderToText();
	}

	private String getEnd(String theMenuBarName, Template theTemplate)
	{
		HtmlTemplate theMenuBarBeginDisabledTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theItems.anEndDisabled");
		theMenuBarBeginDisabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuBarName + ".end.disabled"));
		HtmlTemplate theMenuBarBeginEnabledTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theItems.anEndEnabled");
		theMenuBarBeginEnabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuBarName + ".end.enabled"));
		HtmlTemplate theItemTemplate= getItemTemplate(theTemplate);
		theItemTemplate.addIdTagTemplateElement("aContent", theMenuBarBeginDisabledTemplate.renderToText() + theMenuBarBeginEnabledTemplate.renderToText());

		return theItemTemplate.renderToText();
	}

	public void installUI(JComponent aComponent)
	{
		//TODO: no esta el default
		JMenuBar theMenuBar= (JMenuBar)aComponent;

		if (!(theMenuBar.getLayout() instanceof TemplateLayout))
			theMenuBar.setLayout(new PropagateMenuTemplateLayout(WosFramework.getKeyPositionTemplateForName("WinMenu.JMenuBar")));
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/JMenu.js");
	}

	public void doStyleContribution(StyleContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/styles/winmenu.css");
	}

}