// WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package net.ar.webonswing.managers.windows;

import java.lang.reflect.*;
import java.util.*;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.wrapping.*;

/**
 * Implementacion simple de un administrador de ventanas; tiene la capacidad de
 * apilar las ventanas que se van abriendo para poder trabajar con ventanas de
 * tipo "modal".
 * 
 * @author Fernando Damian Petrola
 */
public class DefaultWindowManager implements WindowManager
{
	public static final String AFTER_HIDE_METHOD_NAME= "theAfterHideMethodName";
	public static final String OPENER_WINDOW= "theOpenerWindow";
	public static final String WINDOW_MANAGER_PROPERTY= "theWindowManager";

	protected Stack theStack;
	protected Hashtable theOpenersTable;
	private VisualWindow currentWindow;

	protected void showNormal(Object aWindowToShow)
	{
	}

	protected void hideNormal(Object aWindow)
	{
	}

	protected WindowManager getWindowManager(Object anOpenerWindow)
	{
		VisualComponent theComponent= (VisualComponent)anOpenerWindow;
		return (WindowManager)theComponent.getClientProperty(WINDOW_MANAGER_PROPERTY);
	}

	protected void setWindowManager(Object aWindow, WindowManager aWindowManager)
	{
		VisualComponent theComponent= (VisualComponent)aWindow;
		theComponent.putClientProperty(WINDOW_MANAGER_PROPERTY, aWindowManager);
	}

	protected VisualWindow getNewVisualWindowFor(Object aWindowToShow)
	{
		return (VisualWindow)aWindowToShow;
	}

	protected boolean isWindowModal(Object aWindowToShow)
	{
		return false;
	}

	protected boolean isWindowEquals(Object aComponent, VisualWindow theWindowWrapper)
	{
		return aComponent.equals(theWindowWrapper);
	}

	protected Object getRealWindow(Object aWindow)
	{
		return aWindow;
	}

	public DefaultWindowManager()
	{
		theStack= new Stack();
		theOpenersTable= new Hashtable();
	}

	public void show(VisualWindow aWindow, boolean isModal)
	{
		if (!isModal)
			theStack.clear();
		else
			addModalWindowExecuteMethod(aWindow);

		setCurrentWindow(aWindow);
	}

	protected void addModalWindowExecuteMethod(VisualWindow aWindow)
	{
		Object theMethod= aWindow.getClientProperty(AFTER_HIDE_METHOD_NAME);
		if (theMethod != null)
			theOpenersTable.put(aWindow.getName(), theMethod);
	}

	public void hide(VisualWindow aWindow)
	{
		VisualWindow foundWindow= findWindowWithId(aWindow.getName());
		if (existOpennedWindows() && foundWindow != null)
		{
			int indexOfWindow= theStack.indexOf(foundWindow);

			if (indexOfWindow > 0)
			{
				VisualWindow parentWindow= (VisualWindow)theStack.get(indexOfWindow - 1);
				executeMethodForModalClosing(aWindow, parentWindow);
				setCurrentWindow(parentWindow);
			}
			else 
				setCurrentWindow((VisualWindow)theStack.peek());

			theStack.remove(foundWindow);
		}

		if (!existOpennedWindows())
		{
			theStack.push(aWindow);
			setCurrentWindow(aWindow);
		}
	}

	protected void executeMethodForModalClosing(VisualWindow aWindow, VisualWindow aParentWindow)
	{
		String theMethodName= (String)theOpenersTable.get(aWindow.getName());
		if (theMethodName != null)
			callReturnMethod(aParentWindow, aWindow, theMethodName);
	}

	public VisualWindow getCurrentWindow()
	{
		if (!existOpennedWindows())
			return null;
		else
			return currentWindow;
	}

	public void setCurrentWindow(VisualWindow aWindow)
	{
		setWindowManager(aWindow, this);

		currentWindow= findWindowWithId(aWindow.getName());

		if (currentWindow == null)
		{
			currentWindow= aWindow;
			theStack.push(aWindow);
		}
	}

	public VisualWindow findWindowWithId(Object aName)
	{
		for (Iterator i= theStack.iterator(); i.hasNext();)
		{
			VisualWindow theWindow= (VisualWindow)i.next();
			if (theWindow.getName().equals(aName))
				return theWindow;
		}

		return null;
	}

	public void replaceCurrentWindow(VisualWindow aReplaceWindow)
	{
		theStack.set(theStack.indexOf(currentWindow), aReplaceWindow);
		setCurrentWindow(aReplaceWindow);
	}

	public void showChildWindow(Object anOpenerWindow, Object aWindowToShow)
	{
		showAndExecute(anOpenerWindow, aWindowToShow, null);
	}

	protected void callReturnMethod(VisualWindow aParentWindow, VisualWindow aWindow, String theMethodName)
	{
		Object theWindow= getRealWindow(aWindow);
		Object theOpenerWindow= getRealWindow(aParentWindow);

		callMethod(theMethodName, theWindow, theOpenerWindow);
	}

	protected void callMethod(String theMethodName, Object theWindow, Object theOpenerWindow)
	{
		try
		{
			Method theMethod= theOpenerWindow.getClass().getMethod(theMethodName, new Class[] { theWindow.getClass() });
			if (theMethod != null)
				theMethod.invoke(theOpenerWindow, new Object[] { theWindow });
		}
		catch (Exception e)
		{
			throw new WebOnSwingException("Cannot invoke the method after window hide", e);
		}
	}

	public void showAndExecute(Object anOpenerWindow, Object aWindowToShow, String aMethodName)
	{
		WindowManager theWindowManager= getWindowManager(anOpenerWindow);
		setWindowManager(aWindowToShow, theWindowManager);

		if (theWindowManager != null)
		{
			VisualWindow theWindowToShowWrapper= getNewVisualWindowFor(aWindowToShow);
			if (aMethodName != null)
				theWindowToShowWrapper.putClientProperty(AFTER_HIDE_METHOD_NAME, aMethodName);

			theWindowToShowWrapper.putClientProperty(OPENER_WINDOW, anOpenerWindow);
			theWindowManager.show(theWindowToShowWrapper, isWindowModal(aWindowToShow));
		}
		else
		{
			showNormal(aWindowToShow);

			if (aMethodName != null)
				callMethod(aMethodName, aWindowToShow, anOpenerWindow);
		}
	}

	public void hide(Object aWindow)
	{
		WindowManager theWindowManager= getWindowManager(aWindow);

		if (theWindowManager != null)
		{
			VisualWindow theFoundWindow= theWindowManager.findWindow(aWindow);
			if (theFoundWindow != null)
				theWindowManager.hide(theFoundWindow);
		}
		else
			hideNormal(aWindow);
	}

	public VisualWindow findWindow(Object aComponent)
	{
		for (Iterator i= theStack.iterator(); i.hasNext();)
		{
			VisualWindow theWindowWrapper= (VisualWindow)i.next();
			if (isWindowEquals(aComponent, theWindowWrapper))
				return theWindowWrapper;
		}

		return null;
	}

	public VisualWindow getWindow(String aWindowId)
	{
		VisualWindow foundWindow= null;

		if (existOpennedWindows())
			if (aWindowId != null)
				foundWindow= findWindowWithId(aWindowId);

		return foundWindow;
	}

	private boolean existOpennedWindows()
	{
		return !theStack.isEmpty();
	}
}