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

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;

public class PopupMenuSeparatorUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		//JSeparator theSeparator= (JSeparator) theComponent;
		//TODO: ojo!
		Template theTemplate= WosSwingHelper.getTemplateForComponent("JPopupMenuSeparator", getJComponent());
		
		HtmlTemplate theSeparatorTemplate= HtmlTemplateManager.getSubTemplate(theTemplate, "aSeparator"); 

		Tag theDiv= new Tag("div").addAttribute(new TagAttribute("id", "separator")).addAttribute(new TagStyleAttribute().addElement("position", "relative").addElement("display", "block"));
		theSeparatorTemplate.setFoundTag(theDiv);

		theContribManager.doContribution(theComponent, theSeparatorTemplate, null, new String[]{"", ""});
	}
}
