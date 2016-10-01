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

import javax.swing.*;

import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.html.*;

public class ScrollBarUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JScrollBar aScrollBar= (JScrollBar) getJComponent();
		Tag theTag= new Tag("div");
		theTag.setNeedsClosure(true);
		theTag.addAttribute(new TagAttribute("style", "position:absolute; width:" + aScrollBar.getWidth() + "; height:" + aScrollBar.getHeight()+ ";background-color:#0000FF"));

		theContribManager.doContribution(theComponent, new HtmlDivsTemplate(), null, "");
	}
}