//WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
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

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;

public class ProgressBarUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer aRenderingContribManager)
	{
		JProgressBar aProgressBar= (JProgressBar)getJComponent();

		String name= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(aProgressBar).getName();
		int value= (aProgressBar.getValue() * 100 / aProgressBar.getMaximum());
		int width= aProgressBar.getWidth();
		int height= aProgressBar.getHeight();

		TagStyleAttribute textStyleAttribute= new TagStyleAttribute();
		textStyleAttribute.addElement("position", "absolute");
		textStyleAttribute.addElement("width", width);
		textStyleAttribute.addElement("height", height);

		Tag textTag= new Tag("div").addAttribute(textStyleAttribute).addAttribute("align", "center").addAttribute("valign", "center").addTextToContainer(aProgressBar.getString());

		TagStyleAttribute barStyleAttribute= new TagStyleAttribute();
		barStyleAttribute.addElement("width", (aProgressBar.getOrientation() == SwingConstants.HORIZONTAL ? value : 100) + "%");
		barStyleAttribute.addElement("height", (aProgressBar.getOrientation() == SwingConstants.VERTICAL ? value : 100) + "%");
		barStyleAttribute.addElement("background-color", WosSwingHelper.getHTMLColorString(aProgressBar.getForeground()));
		barStyleAttribute.addElement("left", "0px");
		barStyleAttribute.addElement("top", "0px");
		barStyleAttribute.addElement("position", "absolute");

		Tag progressTag= new Tag("div").addAttribute(new TagAttribute("name", name)).addAttribute(barStyleAttribute);

		TagStyleAttribute style= new TagStyleAttribute();
		style.addElement("width", width);
		style.addElement("height", height);
		style.addElement("border", "solid 1px #999999");
		style.addElement("position", "relative");
		if (aProgressBar.getX() != 0)
			style.addElement("left", aProgressBar.getX());
		if (aProgressBar.getY() != 0)
			style.addElement("top", aProgressBar.getY());

		Tag theTag= new Tag("div").addAttribute(new TagAttribute("name", name)).addAttribute(style);

		theTag.addElementToContainer(progressTag);
		theTag.addElementToContainer(textTag);

		aRenderingContribManager.doContribution(theComponent, theTag, theTag, RemoteHelper.getListenersAdds(theComponent));
	}
}