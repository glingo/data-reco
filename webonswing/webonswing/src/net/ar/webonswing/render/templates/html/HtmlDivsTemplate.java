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

package net.ar.webonswing.render.templates.html;

import java.awt.*;
import java.util.*;
import java.util.List;

import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.wrapping.*;

/**
 * Plantilla que permite ubicar los componentes de Swing sin usar plantillas de HTML,
 * mediante divs anidados logra que los componentes se grafiquen en la coordenadas que 
 * cada uno especifica. 
 * 
 * @author Fernando Damian Petrola
 */
public class HtmlDivsTemplate extends DefaultTemplate 
{
	public HtmlDivsTemplate()
	{
		super(new HtmlDivsTemplateBody());
	}
}

class HtmlDivsTemplateBody implements TemplateBody
{
	StringBuffer theReplacedBodyString;

	public HtmlDivsTemplateBody()
	{
		theReplacedBodyString= new StringBuffer();
	}

	public void replace(List theElements)
	{
		MarkupContainer theMarkupContainer= new MarkupContainer();

		if (!theElements.isEmpty())
		{
			Tag theMainTag= new Tag("div");
			theMainTag.addAttribute(new TagAttribute("style", "position:relative"));

			for (Iterator i= theElements.iterator(); i.hasNext();)
			{
				DefaultTemplateElement theElement= (DefaultTemplateElement) i.next();
				Rectangle theBounds= ((VisualComponent) theElement.getId()).getBounds();

				Tag theElementTag= new Tag("div");

				TagStyleAttribute theStyle= new TagStyleAttribute();
				theStyle.addElement("position", "absolute");
				theStyle.addElement("top", theBounds.getY() > 0 ? (int) theBounds.getY() : 0);
				theStyle.addElement("left", theBounds.getX() > 0 ? (int) theBounds.getX() : 0);
				theElementTag.addAttribute(theStyle);

				theElementTag.addTextToContainer(new ContentRenderer(theElement.getContent()).getResult().toString());
			
				theMainTag.getTheMarkupContainer().addElement(theElementTag);
			}

			theMarkupContainer.addElement(theMainTag);
		}

		theReplacedBodyString= new StringBuffer(new TextMarkupRenderer(theMarkupContainer).getResult().toString());
	}

	public Object getResult()
	{
		return theReplacedBodyString;
	}

	public Object clone() throws CloneNotSupportedException
	{
		HtmlDivsTemplateBody theTemplateBody= (HtmlDivsTemplateBody) super.clone();
		theTemplateBody.theReplacedBodyString= new StringBuffer(theReplacedBodyString.toString());
	
		return theTemplateBody;
	}

}