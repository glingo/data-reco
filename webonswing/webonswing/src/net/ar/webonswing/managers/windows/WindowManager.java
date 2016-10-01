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

import java.io.*;

import net.ar.webonswing.wrapping.*;

public interface WindowManager extends Serializable
{
	public void show(VisualWindow aWindow, boolean isModal);
	public void hide(VisualWindow aWindow);
	public VisualWindow findWindowWithId(Object anId);
	public VisualWindow findWindow(Object aComponent);
	public VisualWindow getCurrentWindow();
	public void replaceCurrentWindow(VisualWindow aReplaceWindow);
	public void setCurrentWindow(VisualWindow aWindow);
	
	public void showChildWindow(Object anOpenerWindow, Object aWindowToShow);
	public void showAndExecute(Object anOpenerWindow, Object aWindowToShow, String aMethodName);
	public void hide(Object aWindow);
	
	public VisualWindow getWindow(String windowId);
}