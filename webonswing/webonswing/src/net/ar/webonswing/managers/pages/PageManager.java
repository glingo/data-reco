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

package net.ar.webonswing.managers.pages;

import java.io.*;
import java.util.*;

import net.ar.webonswing.pages.*;
import net.ar.webonswing.wrapping.*;

/**
 * Permite administrar las relaciones que existen entre paginas, ventanas y
 * ubicacion de paginas (ruta) de web.
 * 
 * @author Fernando Damian Petrola
 */
public interface PageManager extends Serializable
{
	public abstract Page getPageForWindowName(String aWindowName);
	public abstract Page getPageForPath(String aPath);
	public abstract Class getPageClassForWindowName(String aWindowName);

	public abstract String getPathForWindowName(String aWindowName);
	public abstract String getPathForPageName(String aPageName);

	public abstract VisualWindow getWindowForPageName(String aPageName);
	public abstract VisualWindow getWindowForPath(String aPath);

	public abstract void removePageWithPath(String aPath);

	public abstract void addPage(String aPath, String aPage, String aWindow, String aPlace);

	public abstract Iterator getIterator();
	
	public String getDefaultPageClass();
	public String getDefaultWindowPlace();
}