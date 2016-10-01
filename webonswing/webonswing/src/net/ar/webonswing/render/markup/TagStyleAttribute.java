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

import java.util.*;

public class TagStyleAttribute extends TagAttribute
{
	HashMap theElements;
	
	public TagStyleAttribute()
	{
		super("style", "");
		
		theElements= new HashMap();
	}
	
	public TagStyleAttribute addElement(String aKey, String anElement)
	{
		theElements.put(aKey, anElement);
		setValue(renderStyle());
		
		return this;
	}

	public TagStyleAttribute addElement(String aKey, int anElement)
	{
		theElements.put(aKey, new Integer(anElement).toString());
		setValue(renderStyle());
		
		return this;
	}

	public void removeElement(String aKey)
	{
		theElements.remove(aKey);
	}
	
	private String renderStyle ()
	{
		StringBuffer theResult= new StringBuffer();
		
		for (Iterator i= theElements.entrySet().iterator(); i.hasNext();)
		{
			Map.Entry theElement= (Map.Entry) i.next();
			theResult.append(theElement.getKey()).append(":").append(theElement.getValue()).append(";"); 
		}
		
		return theResult.toString();
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		TagStyleAttribute theClone= (TagStyleAttribute) super.clone();
		theClone.theElements= (HashMap) theElements.clone();
		return theClone;
	}
}
