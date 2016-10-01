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

package net.ar.webonswing.managers.cache;

import java.util.*;

import net.ar.webonswing.*;

/**
 * Maneja el estado del cache, si existe un Map como estado de "cache" quiere
 * decir que el componente va a ser cacheado. Y este Map va a contener todas las
 * diferentes alternativas cacheadas del mismo componente determinadas y
 * parametrizadas por la clave "aKey" que se le pasa al grabar.
 * 
 * @author Fernando Damian Petrola
 */
public class ComponentCacheStateContainer
{
	private static final String CACHE_ID= "cache";
	protected String theCachedComponentName= "";

	protected Map theCachedState;
	protected String theCachedWindowName= "";

	public Object[] getCacheData(String aWindowName, String aComponentName, String aKey)
	{
		updateCachedState(aWindowName, aComponentName);
		return (Object[])theCachedState.get(aKey);
	}

	public boolean isCached(String aWindowName, String aComponentName)
	{
		updateCachedState(aWindowName, aComponentName);
		return theCachedState != null;
	}

	public void setCached(String aWindowName, String aComponentName, boolean isCached)
	{
		updateCachedState(aWindowName, aComponentName);

		if (!isCached)
			theCachedState= null;
		else if (theCachedState == null)
			theCachedState= new Hashtable();

		WosFramework.getInstance().getWindowTreeStateManager().setComponentState(aWindowName, aComponentName, CACHE_ID, theCachedState);
	}

	public void setCacheData(String aWindowName, String aComponentName, String aKey, Object aProps)
	{
		updateCachedState(aWindowName, aComponentName);
		theCachedState.put(aKey, aProps);
	}

	protected void updateCachedState(String aWindowName, String aComponentName)
	{
		if (!aWindowName.equals(theCachedWindowName) || !aComponentName.equals(theCachedComponentName))
		{
			theCachedState= (Map)WosFramework.getInstance().getWindowTreeStateManager().getComponentState(aWindowName, aComponentName, CACHE_ID);
			theCachedWindowName= aWindowName;
			theCachedComponentName= aComponentName;
		}
	}
}