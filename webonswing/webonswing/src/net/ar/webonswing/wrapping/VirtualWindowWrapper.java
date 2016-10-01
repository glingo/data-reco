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

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.persistence.*;

public class VirtualWindowWrapper extends VirtualWindow implements VisualWindow, ComponentWrapper
{
	public VirtualWindowWrapper(VisualWindow aWindow, String aWindowName, String aWindowClassName, PersistenceContributionContainer aPersistenceContainer)
	{
		super(aWindow, aWindowName, aWindowClassName, aPersistenceContainer);
	}

	protected ComponentWrapper getOriginalAsComponentWrapper ()
	{
		return (ComponentWrapper) theOriginalWindow;
	}

	protected void restoreOriginal()
	{   
		if (getOriginalAsComponentWrapper() == null)
		{
			theOriginalWindow= WosFramework.getInstance().getHierarchyWrapper().wrapWindow(WosHelper.createClassInstance(theWindowClassName));
			theOriginalWindow.setName(theVirtualName); 
			new ComponentStandardizer().standardizeWindowHierarchy(theOriginalWindow);
			thePersistenceContainer.restoreAllValues(theOriginalWindow.getRootComponent());
		}
	}

	public void setWrappedComponent(Object object)
	{
		restoreOriginal();
		getOriginalAsComponentWrapper().setWrappedComponent(object);
	}

	public Object getWrappedComponent()
	{
		restoreOriginal();
		return getOriginalAsComponentWrapper().getWrappedComponent();
	}
}
