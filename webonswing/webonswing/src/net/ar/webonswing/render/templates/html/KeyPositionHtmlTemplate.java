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

import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public class KeyPositionHtmlTemplate extends HtmlTemplate implements KeyPositionTemplate, PublicCloneable
{
	HashMap theKeys= new HashMap();
	HtmlTemplate theHtmlTemplate;

	public KeyPositionHtmlTemplate(HtmlTemplate aHtmlTemplate)
	{
		theHtmlTemplate= aHtmlTemplate;
	}

	public Rectangle addKey(String aKey, VisualComponent aComponent)
	{
		theKeys.put(aComponent, aKey);
		return aComponent.getBounds(); //TODO: Hacer que tome las dimensiones del template
	}

	public void addElement(TemplateElement anElement)
	{
		if (theKeys.containsKey(anElement.getId()))
			anElement.setId(theKeys.get(anElement.getId()));

		theHtmlTemplate.addElement(anElement);
	}

	public void accept(Visitor aVisitor)
	{
		theHtmlTemplate.accept(aVisitor);
	}

	public boolean equals(Object obj)
	{
		return theHtmlTemplate.equals(obj);
	}

	public TemplateElement getElementForId(Object anId)
	{
		return theHtmlTemplate.getElementForId(anId);
	}

	public void setFoundTag(Tag aTag)
	{
		theHtmlTemplate.setFoundTag(aTag);
	}

	public void removeElement(TemplateElement anElement)
	{
		theHtmlTemplate.removeElement(anElement);
	}

	public List getElements()
	{
		return theHtmlTemplate.getElements();
	}

	public void setBody(TemplateBody aBody)
	{
		theHtmlTemplate.setBody(aBody);
	}

	public Tag getFoundTag()
	{
		return theHtmlTemplate.getFoundTag();
	}

	public String toString()
	{
		return theHtmlTemplate.toString();
	}

	public int hashCode()
	{
		return theHtmlTemplate.hashCode();
	}

	public HtmlTemplateBody getHtmlTemplateBody()
	{
		return theHtmlTemplate.getHtmlTemplateBody();
	}

	public TemplateBody getBody()
	{
		return theHtmlTemplate.getBody();
	}

	public Object clone() throws CloneNotSupportedException
	{
		KeyPositionHtmlTemplate theClone= new KeyPositionHtmlTemplate(theHtmlTemplate != null ? (HtmlTemplate) theHtmlTemplate.clone() : null);
		theClone.theKeys= (HashMap) theKeys.clone();
		return theClone;
	}

}
