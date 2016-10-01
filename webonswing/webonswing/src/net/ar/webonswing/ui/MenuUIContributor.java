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

import javax.swing.*;
import javax.swing.event.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.walkers.*;
import net.ar.webonswing.wrapping.*;

public class MenuUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JMenu theMenu= (JMenu) getJComponent();
		String theMenuName= theComponent.getName();

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JMenu", theMenu);

		removeChangeListener(theMenu);

		Tag theTag= new Tag("span").addElementToContainer(new TagContent(theMenu.getText())).addAttribute(new TagAttribute("name", theMenuName));
		if (theMenu.getListeners(ActionListener.class).length > 0)
			theTag.addAttribute(new TagAttribute("onclick", "ed.dispatch(new ActionEvent(this.name, ''));"));

		HtmlTemplate theMenuDisabledTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, "aMenuDisabled");
		theMenuDisabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuName + ".disabled"));
		theMenuDisabledTemplate.addIdTagTemplateElement("aText", theTag);
		HtmlTemplate theMenuEnabledTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, "aMenuEnabled");
		theMenuEnabledTemplate.mergeFoundTag(new Tag("div").addAttribute("id", theMenuName + ".enabled").addAttribute("style", "display:none"));
		theMenuEnabledTemplate.addIdTagTemplateElement("aText", theTag);

		JPopupMenu thePopup= theMenu.getPopupMenu();
		
		VisualComponent thePopupComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(thePopup);
		
		HtmlTemplate thePopupTemplate= (HtmlTemplate) theContribManager.getContainerRenderer().render(thePopupComponent);

		setIcon(theMenu, theMenuDisabledTemplate, theMenuEnabledTemplate);

		
		//TODO ver lo de la posicion Y
		
		Tag theDiv= new Tag("div").addAttribute(new TagAttribute("id", theMenuName)).addAttribute(new TagStyleAttribute().addElement("position", "absolute").addElement("display", "none").addElement("left", theMenu.getX()).addElement("top", 19).addElement("width", theMenu.getWidth()));
		thePopupTemplate.setFoundTag(theDiv);

		String theResult= theMenuDisabledTemplate.renderToText() + theMenuEnabledTemplate.renderToText() + thePopupTemplate.renderToText();

		theContribManager.doContribution(theComponent, new TextContent(theResult), null, new String[] { theContribManager.getComponentInitScripts(thePopupComponent)[1], "new JMenu('" + theMenuName + "').add(" + theContribManager.getComponentInitScripts(thePopupComponent)[0] + ")" });
	}

	private void setIcon(JMenu theMenu, HtmlTemplate theMenuDisabledTemplate, HtmlTemplate theMenuEnabledTemplate)
	{
		Icon theIcon= theMenu.getIcon();
		Tag theIconTag= null;

		if (theIcon != null)
			theIconTag= new Tag("img").addAttribute("src", WosFramework.getInstance().getContextPath() + "/" + ((ImageIcon) theIcon).getDescription());

		HtmlTemplate theIconElement= HtmlTemplateManager.getSubTemplate(theMenuDisabledTemplate, "theIcon");
		if (theIconElement != null)
			theIconElement.setFoundTag(theIconTag);

		theIconElement= HtmlTemplateManager.getSubTemplate(theMenuEnabledTemplate, "theIcon");
		if (theIconElement != null)
			theIconElement.setFoundTag(theIconTag);
	}

	private void removeChangeListener(JMenuItem theMenuItem)
	{
		ChangeListener[] theChangeListeners= (ChangeListener[]) theMenuItem.getListeners(ChangeListener.class);
		if (theChangeListeners.length > 0)
			theMenuItem.removeChangeListener(theChangeListeners[0]);
	}

	public VisitableContainer getContainerVisitable()
	{
		return new JMenuContainerVisitable();
	}
}
