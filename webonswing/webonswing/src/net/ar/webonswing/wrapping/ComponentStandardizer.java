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
import net.ar.webonswing.managers.contributors.*;

public class ComponentStandardizer
{
	protected HierarchyWrapper theHierarchyWrapper;

	public ComponentStandardizer()
	{
		theHierarchyWrapper= WosFramework.getInstance().getHierarchyWrapper();
	}

	public ComponentStandardizer(HierarchyWrapper aHierarchyWrapper)
	{
		theHierarchyWrapper= aHierarchyWrapper;
	}

	public VisualWindow standardizeWindowHierarchy(VisualWindow aWindow)
	{
		if (theHierarchyWrapper != null)
			theHierarchyWrapper.createHierarchy(aWindow);

		assignNamesAndContributors(aWindow, aWindow.getRootComponent());
		return aWindow;
	}

	public VisualComponent standardizeChildsComponentHierarchy(VisualComponent aComponent)
	{
		VisualComponent theResult= aComponent;
		
		if (theHierarchyWrapper != null)
			theResult= theHierarchyWrapper.createChildsHierarchy(aComponent);

		return assignNamesAndContributors(aComponent, theResult);
	}

	protected VisualComponent assignNamesAndContributors(VisualComponent anStartComponent, VisualComponent theRootComponent)
	{
		WosFramework.getComponentNameManager().assignNamesFrom(theRootComponent);
		new ComponentContributorAssigner(anStartComponent).assignContributorsFrom(theRootComponent);
		return theRootComponent;
	}
}
