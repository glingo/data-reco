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

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.managers.windows.*;
import net.ar.webonswing.wrapping.*;

public class SwingWindowManager extends DefaultWindowManager
{
	protected boolean isWindowEquals(Object aComponent, VisualWindow theWindowWrapper)
	{
		if (aComponent instanceof Window)
		{
			Window theWindow= (Window) aComponent;
			return ((ComponentWrapper) theWindowWrapper).getWrappedComponent().equals(theWindow);
		}
		return false;
	}

	protected VisualWindow getNewVisualWindowFor(Object aWindowToShow)
	{
		VisualWindow theWindowToShowWrapper= new SwingWindowWrapper(aWindowToShow);
		return theWindowToShowWrapper;
	}

	protected void showNormal(Object aWindowToShow)
	{
		((Window) aWindowToShow).show();
	}

	protected void hideNormal(Object aWindow)
	{
		((Window) aWindow).hide();
	}

	protected boolean isWindowModal(Object aWindowToShow)
	{
		if (aWindowToShow instanceof Dialog)
			return ((Dialog) aWindowToShow).isModal();
		else
			return false;
	}

	protected WindowManager getWindowManager(Object anOpenerWindow)
	{
		return (WindowManager) getWindowProperty(anOpenerWindow, WINDOW_MANAGER_PROPERTY);
	}

	protected void setWindowManager(Object aWindow, WindowManager aWindowManager)
	{
		if (aWindow instanceof ComponentWrapper)
			setWindowProperty(((ComponentWrapper) aWindow).getWrappedComponent(), WINDOW_MANAGER_PROPERTY, aWindowManager);
		else if (aWindow instanceof Window)
			setWindowProperty(aWindow, WINDOW_MANAGER_PROPERTY, aWindowManager);
	}

	protected Object getRealWindow(Object aWindow)
	{
		return ((ComponentWrapper) aWindow).getWrappedComponent();
	}
	
	public static Object getWindowProperty(Object aWindow, String aPropertyName)
	{
		if (aWindow instanceof RootPaneContainer)
			return ((JComponent) ((RootPaneContainer) aWindow).getContentPane()).getClientProperty(aPropertyName);

		return null;
	}
	public static Object setWindowProperty(Object aWindow, String aPropertyName, Object aValue)
	{
		if (aWindow instanceof RootPaneContainer)
		{
			((JComponent) ((RootPaneContainer) aWindow).getContentPane()).putClientProperty(aPropertyName, aValue);
			return aValue;
		}

		return null;
	}
}
