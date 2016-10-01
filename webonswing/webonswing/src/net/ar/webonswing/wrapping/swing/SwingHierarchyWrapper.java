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

package net.ar.webonswing.wrapping.swing;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.ui.*;
import net.ar.webonswing.walkers.*;
import net.ar.webonswing.wrapping.*;

public class SwingHierarchyWrapper extends HierarchyWrapper
{
	protected ComponentWrapper createComponentWrapper(Object aComponent)
	{
		return new SwingComponentWrapper((JComponent) aComponent);
	}

	public VisitableContainer getContainerVisitable(Object aComponent)
	{
		JComponent theJComponent= (JComponent) aComponent;
		VisitableContainer theContainerVisitable= getContainerVisitableForJComponent(theJComponent);
		theContainerVisitable.setComponent(theJComponent);
		return theContainerVisitable;
	}

	protected VisitableContainer getContainerVisitableOfVisualComponent(VisualComponent aComponent)
	{
		JComponent theJComponent= (JComponent) ((ComponentWrapper) aComponent).getWrappedComponent();
		VisitableContainer theContainerVisitable= getContainerVisitableForJComponent(theJComponent);
		theContainerVisitable.setComponent(theJComponent);
		return theContainerVisitable;
	}

	public boolean isWrapped(Object theComponent)
	{
		return ((JComponent) theComponent).getClientProperty(COMPONENT_PROPERTY) != null;
	}

	protected Object getUnwrappedRootComponent(VisualWindow aWindow)
	{
		return ((RootPaneContainer) ((ComponentWrapper) aWindow).getWrappedComponent()).getRootPane();
	}

	public WindowWrapper wrapWindow(Object aWindow)
	{
		return new SwingWindowWrapper(aWindow);
	}

	protected VisitableContainer getContainerVisitableForJComponent(JComponent aComponent)
	{
		ComponentUIContributor theContributor;

		theContributor= ((ComponentUIContributor) WosHelper.createClassInstance(WosFramework.getInstance().getContributorManager().getDefaultComponentContributorClassName(aComponent.getUIClassID())));

		return theContributor.getContainerVisitable();
	}

	public void setComponentWrapper(Object aComponent, ComponentWrapper aWrapper)
	{
		((JComponent) aComponent).putClientProperty(COMPONENT_PROPERTY, aWrapper);
	}

	public ComponentWrapper getComponentWrapper(Object component)
	{
		if (!WosFramework.isActive())
			return new SwingComponentWrapper((JComponent) component);

		Object theWrapper= ((JComponent) component).getClientProperty(COMPONENT_PROPERTY);

		if (theWrapper != null)
			return (ComponentWrapper) theWrapper;
		else
			throw new WebOnSwingException("No asocciated component for: " + component);
	}
}