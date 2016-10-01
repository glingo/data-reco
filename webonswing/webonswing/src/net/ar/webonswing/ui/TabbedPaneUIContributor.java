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
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.wrapping.*;
import net.ar.webonswing.wrapping.swing.*;

import org.apache.commons.logging.*;

public class TabbedPaneUIContributor extends AbstractSwingComponentUIContributor
{
	StringBuffer theScript= new StringBuffer();

	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		try
		{
			JTabbedPane theTabbedPane= (JTabbedPane) getJComponent();

			Template theTemplate= WosSwingHelper.getTemplateForComponent("JTabbedPane", theTabbedPane);
			HtmlTemplate theMainTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, "theMainTag");

			Tag theMainTag= new Tag("div");
			
			if (theTabbedPane.getWidth() + theTabbedPane.getHeight() > 0)
				theMainTag.addAttribute(new TagStyleAttribute().addElement("width", theTabbedPane.getWidth()).addElement("height", theTabbedPane.getHeight()));
			
			JComponent theSelectedPane= (JComponent) theTabbedPane.getComponentAt(theTabbedPane.getSelectedIndex());
			
			theMainTag.getTheMarkupContainer().addElement(new Tag("input").setNeedsClosure(false).addAttribute(new TagAttribute("type", "hidden")).addAttribute(new TagAttribute("name", theComponent.getName())).addAttribute(new TagAttribute("value", WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theSelectedPane).getName())));
			theMainTemplate.mergeFoundTag(theMainTag);
			
			createTabPane(theContribManager, theTabbedPane, theTemplate, theMainTemplate);

			theContribManager.doContribution(theComponent, theMainTemplate, theMainTag, getInitScript());
		}
		catch (Exception e)
		{
			LogFactory.getLog(TabbedPaneUIContributor.class).error("", e);
		}
	}

	private StringBuffer createTabPane(RenderingContributionContainer theContribManager, JTabbedPane theTabbedPane, Template theTemplate, HtmlTemplate theMainTemplate) throws CloneNotSupportedException
	{
		HtmlTemplate theTabPaneTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, "theMainTag.theTabPane");

		StringBuffer theResult= new StringBuffer();
		theScript= new StringBuffer();
		HtmlTemplate theTabTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, "theMainTag.theBar.theTab");
		StringBuffer theTabsResult= new StringBuffer();
		Component[] theComponents= theTabbedPane.getComponents();

		for (int i= 0; i < theComponents.length; i++)
		{
			Container theSubComponent= (Container) theComponents[i];
			VisualComponent theTabComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theSubComponent);
			
			if (theSubComponent.isVisible() || theSubComponent instanceof JPanel)
			{
				Tag theTabPaneTag= new Tag("div");
				theTabPaneTag.addAttribute(new TagAttribute("id", theTabComponent.getName()));
				TagStyleAttribute theStyle= new TagStyleAttribute();
				theStyle.addElement("width", theSubComponent.getWidth()).addElement("height", "100%").addElement("left", 2).addElement("top", "20").addElement("position", "absolute");
				if (!theSubComponent.isVisible())
					theStyle.addElement("display", "none");
				theTabPaneTag.addAttribute(theStyle);

				HtmlTemplate theClonedTabPaneTemplate= (HtmlTemplate) theTabPaneTemplate.clone();
				theClonedTabPaneTemplate.mergeFoundTag(theTabPaneTag);
				theClonedTabPaneTemplate.addElement(new IdTagTemplateElement("theContent", new TagContent(new ContentRenderer(theContribManager.getContainerRenderer().render(theTabComponent)).getResult())));

				theResult.append(new TextTemplateRenderer(theClonedTabPaneTemplate).getResult());
				theScript.append(theContribManager.getComponentInitScripts(theTabComponent)[0]);
				

				Tag theTabTag= new Tag("div");
				theTabTag.addAttribute(new TagAttribute("onclick", "getElement('" + theComponent.getName() + "').value+='-" + theTabComponent.getName() + "'; ed.dispatch(new ChangeEvent('" + theComponent.getName() + "'));"));
				theTabTag.addAttribute(new TagAttribute("class", theTabbedPane.getSelectedIndex() == i ? "tabDown" : "tabUp"));
				theTabTag.addAttribute(new TagAttribute("id", theComponent.getName() + "_" + theTabComponent.getName()));

				HtmlTemplate theClonedTabTemplate= (HtmlTemplate) theTabTemplate.clone();
				theClonedTabTemplate.mergeFoundTag(theTabTag);
				theClonedTabTemplate.addElement(new IdTagTemplateElement("theContent", new TagContent(theTabbedPane.getTitleAt(i))));

				theTabsResult.append(new TextTemplateRenderer(theClonedTabTemplate).getResult());
				
			}
		}

		theMainTemplate.addElement(new IdTagTemplateElement("theBar", new TextContent(theTabsResult)));
		theMainTemplate.addElement(new IdTagTemplateElement("theTabPane", new TextContent(theResult), null));

		return theResult;
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JTabbedPane theTabbedPane= (JTabbedPane) ((ComponentWrapper)theEvent.getSource()).getWrappedComponent();

			if (theEvent.getType().equals("stateChanged") || theEvent.getType().equals("update"))
			{
				String theTab= (String) theEvent.getParameters()[0];
				
				SwingComponentWrapper theTabComponent= (SwingComponentWrapper) WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theTabbedPane).getTopParent().findComponent(theTab);
				
				theTabbedPane.setSelectedComponent(theTabComponent.getJComponent());
			}
		}
	}

	public String getInitScript()
	{
		String theComponentName= theComponent.getName();
		//		theScriptTag.getTheMarkupContainer().addAll(RemoteHelper.getListenersAdds(theComponent));
		return theScript.toString() + "getComponent('" + theComponentName + "').addListener(new TabbedPaneChangeListener());";
	}
	
	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/JTabbedPane.js");
	}
}
