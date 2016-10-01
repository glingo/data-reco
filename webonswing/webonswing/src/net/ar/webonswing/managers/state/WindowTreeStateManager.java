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

package net.ar.webonswing.managers.state;

import java.io.*;
import java.util.*;

/**
 * Maneja el estado de cada componente que este dentro del arbol de componentes de una ventana dada. 
 * Cada componente tiene varios "estados" asociados a el, y estos estan diferenciados por una cadena que los nombra. 
 * Por ejemplo podria tener los estados "cache", "contribution"; para "cache" el camino seria: 
 * 		Map(nombre_de_ventana):Map(nombre_de_componente):String("cache")= valor guardado
 * y para el "contribution" seria lo mismo pero variaria la ultima rama por String("contribution").
 * 
 * @author Fernando Damian Petrola
 */
public class WindowTreeStateManager implements Serializable
{
	protected Map theWindowsState;

	public WindowTreeStateManager()
	{
		theWindowsState= new Hashtable();
	}

	public Map getTheWindowState()
	{
		return theWindowsState;
	}

	public void setTheWindowState(Map anTheWindowState)
	{
		theWindowsState= anTheWindowState;
	}

	public Object getComponentState(String aWindowName, String aComponentName, String theStateName)
	{
		Object theResult= null;
		Map aWindowState= (Map) theWindowsState.get(aWindowName);

		if (aWindowState != null)
		{
			Map theStates= (Map) aWindowState.get(aComponentName);

			if (theStates != null)
				theResult= theStates.get(theStateName);
		}

		return theResult;
	}

	public Object setComponentState(String aWindowName, String aComponentName, String theStateName, Object theValue)
	{
		Map aWindowState= (Map) theWindowsState.get(aWindowName);
		if (aWindowState == null)
			theWindowsState.put(aWindowName, aWindowState= new Hashtable());

		Map theStates= (Map) aWindowState.get(aComponentName);
		if (theStates == null)
			aWindowState.put(aComponentName, theStates= new Hashtable());

		theStates.put(theStateName, theValue);
		
		return theValue;
	}
}
