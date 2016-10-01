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

import java.util.*;

import net.ar.webonswing.render.*;
import net.ar.webonswing.visitor.*;

public class DefaultTemplate implements Template, PublicCloneable
{
	protected Vector theElements;
	protected TemplateBody theBody;

	public DefaultTemplate(TemplateBody aBody)
	{
		theBody= aBody;
		theElements= new Vector();
	}

	public void setBody(TemplateBody aBody)
	{
		theBody= aBody;
	}

	public void addElement(TemplateElement anElement)
	{
		if (theElements.contains(anElement))
			theElements.remove(anElement);
		
		theElements.add(anElement);
	}

	public void removeElement(TemplateElement anElement)
	{
		theElements.remove(anElement);
	}

	public TemplateBody getBody()
	{
		return theBody;
	}

	public List getElements()
	{
		return theElements;
	}

	public void accept(Visitor aTemplateVisitor)
	{
		((TemplateVisitor) aTemplateVisitor).visitTemplate(this);
	}

	public String toString()
	{
		return new ContentRenderer(this).getResult();
	}

	public TemplateElement getElementForId(Object anId)
	{
		List theStoredElements= getElements();
		for (Iterator i= theStoredElements.iterator(); i.hasNext();)
		{
			TemplateElement theElement= (TemplateElement) i.next();
			if (theElement.getId().equals(anId))
				return theElement;
		}

		return null;
	}

	public Object clone() throws CloneNotSupportedException
	{
		DefaultTemplate theNewTemplate= (DefaultTemplate) super.clone();

		theNewTemplate.theElements= new Vector();
		for (Iterator i= theElements.iterator(); i.hasNext();)
			theNewTemplate.theElements.add(((PublicCloneable) i.next()).clone());

		theNewTemplate.theBody= (TemplateBody) theBody.clone();

		return theNewTemplate;
	}

	public void setElements(Vector vector)
	{
		theElements= vector;
	}

	public TemplateElement getElementAt(int i)
	{
		return (TemplateElement) getElements().get(i);
	}

	public int getElementCount()
	{
		return getElements().size();
	}
}
