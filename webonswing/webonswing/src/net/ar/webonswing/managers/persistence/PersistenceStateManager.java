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

package net.ar.webonswing.managers.persistence;

import java.util.*;

import net.ar.webonswing.*;

public class PersistenceStateManager 
{
	private static final String PERSISTENCE_ID= "persistence";
	public static final int PERSISTED_IN_PAGE= 0;
	public static final int PERSISTED_IN_SESSION= 1;
	public static final int PERSISTED_IN_DB= 2;

	private Vector theLastState;
	private String theLastComponentName= "";
	private String theLastWindowName= "";
	
	public boolean isComponentPersisted(String aWindowName, String aComponentName)
	{
		if (!aWindowName.equals(theLastWindowName) || !aComponentName.equals(theLastComponentName))
			theLastState= (Vector) WosFramework.getInstance().getWindowTreeStateManager().getComponentState(aWindowName, aComponentName, PERSISTENCE_ID);

		if (theLastState != null)
			return ((Boolean) theLastState.get(0)).booleanValue();

		return false;
	}

	public int getPersistenceMethod(String aWindowName, String aComponentName)
	{
		if (!aWindowName.equals(theLastWindowName) || !aComponentName.equals(theLastComponentName))
			theLastState= (Vector) WosFramework.getInstance().getWindowTreeStateManager().getComponentState(aWindowName, aComponentName, PERSISTENCE_ID);

		if (theLastState != null)
			return ((Integer) theLastState.get(1)).intValue();

		return PERSISTED_IN_PAGE;
	}

	public void setComponentPersistenceState(String aWindowName, String aComponentName, boolean isPersisted)
	{
		Vector thePersistenceState= getOrCreatePersistenceState(aWindowName, aComponentName);
		thePersistenceState.set(0, new Boolean(isPersisted));
		WosFramework.getInstance().getWindowTreeStateManager().setComponentState(aWindowName, aComponentName, PERSISTENCE_ID, thePersistenceState);
	}

	public void setComponentPersistenceMethod(String aWindowName, String aComponentName, int aMethod)
	{
		Vector thePersistenceState= getOrCreatePersistenceState(aWindowName, aComponentName);
		thePersistenceState.set(1, new Integer(aMethod));
		WosFramework.getInstance().getWindowTreeStateManager().setComponentState(aWindowName, aComponentName, PERSISTENCE_ID, thePersistenceState);
	}

	private Vector getOrCreatePersistenceState(String aWindowName, String aComponentName)
	{
		Vector thePersistenceState= (Vector) WosFramework.getInstance().getWindowTreeStateManager().getComponentState(aWindowName, aComponentName, PERSISTENCE_ID);

		if (thePersistenceState == null)
		{
			thePersistenceState= new Vector();
			thePersistenceState.add(new Boolean(false));
			thePersistenceState.add(new Integer(PERSISTED_IN_PAGE));
		}

		return thePersistenceState;
	}
}
