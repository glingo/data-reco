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

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.wrapping.*;

public class PersistenceDoNotMatchException extends WebOnSwingException
{
	protected VisualComponent theRootComponent;
	protected DefaultPersistenceContributionContainer thePersistenceContainer;
	protected String theKey;

	public PersistenceDoNotMatchException(DefaultPersistenceContributionContainer aPersistenceContainer, VisualComponent aRootComponent, String aKey, Throwable e)
	{
		super("Persisted data do not match with component hierarchy: key=" + aKey, e);

		thePersistenceContainer= aPersistenceContainer;
		theRootComponent= aRootComponent;
		theKey= aKey;
	}

	public DefaultPersistenceContributionContainer getPersistenceContainer()
	{
		return thePersistenceContainer;
	}
	public void setPersistenceContainer(DefaultPersistenceContributionContainer aPersistenceContainer)
	{
		thePersistenceContainer= aPersistenceContainer;
	}
	public VisualComponent getRootComponent()
	{
		return theRootComponent;
	}
	public void setRootComponent(VisualComponent aRootComponent)
	{
		theRootComponent= aRootComponent;
	}

	public String getKey()
	{
		return theKey;
	}
	public void setKey(String aKey)
	{
		theKey= aKey;
	}
}