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

import net.ar.webonswing.visitor.*;
import net.ar.webonswing.walkers.*;

public abstract class HierarchyWrapper
{
	protected final String COMPONENT_PROPERTY= "theComponent";

	protected ComponentWrapper theRootComponent;
	protected ComponentWrapper theCurrentComponentWrapper;

	protected abstract ComponentWrapper createComponentWrapper(Object aComponent);
	protected abstract Object getUnwrappedRootComponent(VisualWindow aWindow);
	protected abstract VisitableContainer getContainerVisitableOfVisualComponent(VisualComponent aComponent);

	public abstract boolean isWrapped(Object theComponent);
	public abstract void setComponentWrapper(Object aComponent, ComponentWrapper aWrapper);
	public abstract ComponentWrapper getComponentWrapper(Object component);
	public abstract VisitableContainer getContainerVisitable(Object aComponent);
	public abstract WindowWrapper wrapWindow(Object aWindow);

	public VisualWindow createHierarchy(VisualWindow aWindow)
	{
		Object aComponent= getUnwrappedRootComponent(aWindow);
		theCurrentComponentWrapper= null;
		VisitableContainer theContainerVisitable= getContainerVisitable(aComponent);

		theContainerVisitable.accept(new ContainerVisitor()
		{
			public void visitContainerBegin(Object aVisitedComponent)
			{
				ComponentWrapper theComponentWrapper= createComponentWrapper(aVisitedComponent);
				setComponentWrapper(aVisitedComponent, theComponentWrapper);

				if (theCurrentComponentWrapper != null)
					theCurrentComponentWrapper.addChild(theComponentWrapper);
				else
					theRootComponent= theComponentWrapper;

				theCurrentComponentWrapper= theComponentWrapper;
			}

			public void visitContainerEnd(Object aVisitedComponent)
			{
				theCurrentComponentWrapper= (ComponentWrapper) theCurrentComponentWrapper.getParent();
			}
		});

		aWindow.setRootComponent(theRootComponent);

		return aWindow;
	}

	public ComponentWrapper createChildsHierarchy(VisualComponent aComponent)
	{
		theCurrentComponentWrapper= null;
		VisitableContainer theContainerVisitable= getContainerVisitableOfVisualComponent(aComponent);

		theContainerVisitable.accept(new ContainerVisitor()
		{
			public void visitContainerBegin(Object aVisitedComponent)
			{
				ComponentWrapper theComponentWrapper= isWrapped(aVisitedComponent) ? getComponentWrapper(aVisitedComponent) : createComponentWrapper(aVisitedComponent);

				if (theCurrentComponentWrapper != null)
				{
					if (!isWrapped(aVisitedComponent))
						theCurrentComponentWrapper.addChild(theComponentWrapper);
				}
				else
					theRootComponent= theComponentWrapper;

				setComponentWrapper(aVisitedComponent, theComponentWrapper);
				theCurrentComponentWrapper= theComponentWrapper;
			}

			public void visitContainerEnd(Object aVisitedComponent)
			{
				theCurrentComponentWrapper= (ComponentWrapper) theCurrentComponentWrapper.getParent();
			}
		});

		return theRootComponent;
	}
}