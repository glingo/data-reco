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

import java.io.*;
import java.util.*;

import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public interface PersistenceContributionContainer extends Serializable, PublicCloneable
{
	public Map getPersistedData();
	public void setPersistedData(Map aPersistedData);
	
	public Map getComponentPersistedData();
	public void setComponentPersistedData(Map componentPersistedData);
	
	public Map getExternalPersistedData();
	public void setExternalPersistedData(Map externalPersistedData);

	public void persistValue(VisualComponent aComponent, Serializable aContent);
	public Serializable restoreValue(VisualComponent aComponent);
	
	public void persistValue(String theKey, Serializable aString);
	public Serializable restoreValue(String theKey);

	public List getChangedComponents(VisualComponent aRootComponent);
	public void restoreAllValues(VisualComponent aRootComponent);
	
	public void clearData();
}