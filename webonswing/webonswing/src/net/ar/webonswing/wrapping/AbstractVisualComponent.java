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

package net.ar.webonswing.wrapping;

import java.util.*;

import net.ar.webonswing.*;
import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.visitor.*;

public abstract class AbstractVisualComponent implements VisualComponent
{
	protected String theName;
	protected ComponentContributor theContributor;
	protected List theChilds;
	protected VisualComponent theParent;

	public AbstractVisualComponent ()
	{
		theChilds= new Vector();
	}

	public void addChild(VisualComponent aChild)
	{
		theChilds.add(aChild);
		aChild.setParent(this);
	}

	public void removeChild(VisualComponent aChild)
	{
		theChilds.remove(aChild);
		aChild.setParent(null);
	}

	public String getName()
	{
		return theName;
	}

	public void setName(String string)
	{
		theName= string;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof VisualComponent)
		{
			VisualComponent theOtherComponent= (VisualComponent) obj;
			return theOtherComponent.getName().equals(getName());
		}
		else
			return false;
	}

	public ComponentContributor getContributor()
	{
		return theContributor;
	}

	public void setContributor(ComponentContributor contributor)
	{
		theContributor= contributor;
	}

	public List getChilds()
	{
		return theChilds;
	}

	public int getChildCount()
	{
		return theChilds.size();
	}

	public void setChilds(List list)
	{
		theChilds= list;
	}

	public VisualComponent getChildAt(int aPosition)
	{
		return (VisualComponent) theChilds.get(aPosition);
	}

	public VisualComponent getParent()
	{
		return theParent;
	}

	public void setParent(VisualComponent id)
	{
		theParent= id;
	}

	public VisualComponent findComponent(String aComponentName)
	{
		return WosFramework.getComponentNameManager().findComponentWithNameIn(aComponentName, this);
	}

	public VisualComponent getTopParent()
	{
		VisualComponent theComponent= this;

		while (theComponent.getParent() != null)
			theComponent= theComponent.getParent();

		return theComponent;
	}

	public void accept(Visitor aVisitor)
	{
		ComponentVisitor theComponentVisitor= ((ComponentVisitor) aVisitor);

		theComponentVisitor.visitComponentBegin(this);

		for (int i= 0; i < theChilds.size(); i++)
			 ((VisualComponent) theChilds.get(i)).accept(aVisitor);

		theComponentVisitor.visitComponentEnd(this);
	}
}
