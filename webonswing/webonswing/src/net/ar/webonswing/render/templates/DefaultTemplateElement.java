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

package net.ar.webonswing.render.templates;

import net.ar.webonswing.visitor.*;

public class DefaultTemplateElement implements TemplateElement, PublicCloneable
{
	protected Object theId;
	protected Visitable theContent;

	public DefaultTemplateElement(int i, Visitable aContent)
	{
		theId= new Integer(i);
		theContent= aContent;
	}

	public DefaultTemplateElement(Object aId, Visitable aContent)
	{
		theId= aId;
		theContent= aContent;
	}

	public Visitable getContent()
	{
		return theContent;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof DefaultTemplateElement)
		{
			DefaultTemplateElement theElement= (DefaultTemplateElement) obj;

			return theElement.getId().equals(getId());
		}
		else
			return false;
	}

	public Object getId()
	{
		return theId;
	}

	public void setId(Object anId)
	{
		this.theId= anId;
	}

	public String toString()
	{
		return super.toString() + ": theId= " + theId + ", theContent= " + theContent;
	}

	public void accept(Visitor aVisitor)
	{
	}

	public void setContent(Visitable aContent)
	{
		theContent= aContent;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		DefaultTemplateElement theTemplateElement= (DefaultTemplateElement) super.clone();
		theTemplateElement.theContent = (Visitable) ((PublicCloneable)theContent).clone();
		
		return theTemplateElement;
	}

}
