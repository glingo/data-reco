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

package net.ar.webonswing.render.markup;

import java.io.*;

import net.ar.webonswing.visitor.*;

public class TagAttribute implements Visitable, PublicCloneable, Serializable
{
	protected String theValue;
	protected String theKey;

	public TagAttribute(String aKey, String aValue)
	{
		theKey= aKey;
		theValue= aValue;
	}

	public TagAttribute(String aKey, int aValue)
	{
		theKey= aKey;
		theValue= new Integer(aValue).toString();
	}

	public String getKey()
	{
		return theKey;
	}

	public void setKey(String aKey)
	{
		this.theKey= aKey;
	}

	public String getValue()
	{
		return theValue;
	}

	public void setValue(String aValue)
	{
		this.theValue= aValue;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof TagAttribute)
		{
			TagAttribute theAttribute= (TagAttribute) obj;
			return getKey().equals(theAttribute.getKey());
		}
		else
			return false;
	}

	public void accept(Visitor aRenderer)
	{
		((MarkupVisitor) aRenderer).visitAttribute(this);
	}

	public Object clone() throws CloneNotSupportedException
	{
		TagAttribute theAttribute= (TagAttribute) super.clone();
		
		theAttribute.theKey= theKey;
		theAttribute.theValue= theValue;
		
		return theAttribute;
	}

}
