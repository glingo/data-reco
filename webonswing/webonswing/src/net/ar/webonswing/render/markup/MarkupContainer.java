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

import net.ar.webonswing.visitor.*;

public class MarkupContainer extends MarkupElement
{
	Vector theElements;

	public MarkupContainer()
	{
		theElements= new Vector();
	}

	public void addAll(MarkupContainer aContainer)
	{
		for (Iterator i= aContainer.getTheElements().iterator(); i.hasNext();)
			addElement((MarkupElement) i.next());
	}

	public void addElement(MarkupElement anElement)
	{
		theElements.add(anElement);
	}

	public void removeElement(MarkupElement anElement)
	{
		theElements.remove(anElement);
	}

	public List getTheElements()
	{
		return theElements;
	}

	public void setTheElements(Vector anElements)
	{
		this.theElements= anElements;
	}

	public void accept(Visitor aMarkupVisitor)
	{
		((MarkupVisitor) aMarkupVisitor).visitMarkupContainerBegin(this);

		for (Iterator i= getTheElements().iterator(); i.hasNext();)
			 ((MarkupElement) i.next()).accept(aMarkupVisitor);

		((MarkupVisitor) aMarkupVisitor).visitMarkupContainerEnd(this);
	}
	
	public boolean isEmpty ()
	{
		return theElements.isEmpty();
	}
	
	public void clear()
	{
		theElements.clear();
	}

	public Object clone() throws CloneNotSupportedException
	{
		MarkupContainer theContainer= (MarkupContainer) super.clone();

		theContainer.theElements= new Vector();
		for (Iterator i= theElements.iterator(); i.hasNext();)
			theContainer.theElements.add(i.next());

		return theContainer;
	}

}
