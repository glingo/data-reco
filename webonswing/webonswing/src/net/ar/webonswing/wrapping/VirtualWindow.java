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

import java.awt.*;
import java.io.*;
import java.util.List;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.visitor.*;

public class VirtualWindow implements VisualWindow, Serializable, Virtualizable
{
	protected PersistenceContributionContainer thePersistenceContainer;
	protected String theWindowClassName;
	protected transient VisualWindow theOriginalWindow;
	protected String theVirtualName;

	public VirtualWindow(VisualWindow aWindow, String aWindowName, String aWindowClassName, PersistenceContributionContainer aPersistenceContainer)
	{
		theOriginalWindow= aWindow;
		theVirtualName= aWindowName;
		theOriginalWindow.setName(aWindowName);
		theWindowClassName= aWindowClassName;
		thePersistenceContainer= aPersistenceContainer;
	}

	protected void restoreOriginal()
	{
		if (theOriginalWindow == null)
		{
			theOriginalWindow= (VisualWindow) WosHelper.createClassInstance(theWindowClassName);
			new ComponentStandardizer().standardizeWindowHierarchy(theOriginalWindow);
			thePersistenceContainer.restoreAllValues(theOriginalWindow.getRootComponent());
		}
	}

	public void accept(Visitor aVisitor)
	{
		restoreOriginal();
		theOriginalWindow.accept(aVisitor);
	}

	public void addChild(VisualComponent aChild)
	{
		restoreOriginal();
		theOriginalWindow.addChild(aChild);
	}

	public void doLayout()
	{
		restoreOriginal();
		theOriginalWindow.doLayout();
	}

	public boolean equals(Object obj)
	{
		restoreOriginal();
		return theOriginalWindow.equals(obj);
	}

	public VisualComponent findComponent(String aComponentName)
	{
		restoreOriginal();
		return theOriginalWindow.findComponent(aComponentName);
	}

	public Rectangle getBounds()
	{
		restoreOriginal();
		return theOriginalWindow.getBounds();
	}

	public VisualComponent getChildAt(int aPosition)
	{
		restoreOriginal();
		return theOriginalWindow.getChildAt(aPosition);
	}

	public int getChildCount()
	{
		restoreOriginal();
		return theOriginalWindow.getChildCount();
	}

	public List getChilds()
	{
		restoreOriginal();
		return theOriginalWindow.getChilds();
	}

	public Object getClientProperty(Object aKey)
	{
		restoreOriginal();
		return theOriginalWindow.getClientProperty(aKey);
	}

	public ComponentContributor getContributor()
	{
		restoreOriginal();
		return theOriginalWindow.getContributor();
	}

	public Object getLockObject()
	{
		restoreOriginal();
		return theOriginalWindow.getLockObject();
	}

	public String getName()
	{
		return theVirtualName;
	}

	public VisualComponent getParent()
	{
		restoreOriginal();
		return theOriginalWindow.getParent();
	}

	public VisualComponent getRootComponent()
	{
		restoreOriginal();
		return theOriginalWindow.getRootComponent();
	}

	public VisualComponent getTopParent()
	{
		restoreOriginal();
		return theOriginalWindow.getTopParent();
	}

	public String getTypeId()
	{
		restoreOriginal();
		return theOriginalWindow.getTypeId();
	}

	public String getUIClassID()
	{
		restoreOriginal();
		return theOriginalWindow.getUIClassID();
	}

	public int hashCode()
	{
		restoreOriginal();
		return theOriginalWindow.hashCode();
	}

	public void putClientProperty(Object aKey, Object aValue)
	{
		restoreOriginal();
		theOriginalWindow.putClientProperty(aKey, aValue);
	}

	public void removeChild(VisualComponent aChild)
	{
		restoreOriginal();
		theOriginalWindow.removeChild(aChild);
	}

	public void setBounds(Rectangle aRectangle)
	{
		restoreOriginal();
		theOriginalWindow.setBounds(aRectangle);
	}

	public void setChilds(List list)
	{
		restoreOriginal();
		theOriginalWindow.setChilds(list);
	}

	public void setContributor(ComponentContributor contributor)
	{
		restoreOriginal();
		theOriginalWindow.setContributor(contributor);
	}

	public void setName(String string)
	{
		restoreOriginal();
		theOriginalWindow.setName(string);
	}

	public void setParent(VisualComponent id)
	{
		restoreOriginal();
		theOriginalWindow.setParent(id);
	}

	public void setRootComponent(VisualComponent aRootComponent)
	{
		restoreOriginal();
		theOriginalWindow.setRootComponent(aRootComponent);
	}

	public String toString()
	{
		restoreOriginal();
		return theOriginalWindow.toString();
	}

	public VisualWindow newInstance()
	{
		restoreOriginal();
		return theOriginalWindow.newInstance();
	}

	public Object getVirtualInstance(PersistenceContributionContainer aPersistenceContainer)
	{
		restoreOriginal();
		return ((Virtualizable)theOriginalWindow).getVirtualInstance(aPersistenceContainer);
	}
}
