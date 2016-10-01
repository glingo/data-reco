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

package net.ar.webonswing.managers.persistence;

import java.io.*;
import java.util.*;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.wrapping.*;

/**
 * Administra la persistencia de componentes, guarda en su HashMap los datos que
 * cada contribuidor decide persistir.
 * 
 * @author Fernando Damian Petrola
 */
public class DefaultPersistenceContributionContainer implements PersistenceContributionContainer
{
	private static final String EXTERNAL_DATA= "external-data";
	private static final String COMPONENT_DATA= "component-data";
	protected Map persistedData;
	protected Map componentPersistedData;
	protected Map externalPersistedData;

	public DefaultPersistenceContributionContainer()
	{
		clearData();
	}

	public void persistValue(VisualComponent aComponent, Serializable aContent)
	{
		if (aContent != null)
			componentPersistedData.put(aComponent.getName(), aContent);
	}

	public Serializable restoreValue(VisualComponent aComponent)
	{
		return (Serializable)componentPersistedData.get(aComponent.getName());
	}

	public void persistValue(String theKey, Serializable aSerializable)
	{
		externalPersistedData.put(theKey, aSerializable);
	}

	public Serializable restoreValue(String theKey)
	{
		return (Serializable)externalPersistedData.get(theKey);
	}

	public void restoreAllValues(VisualComponent aRootComponent)
	{
		String theKey= "";

		try
		{
			aRootComponent.accept(new ComponentVisitorAdapter()
			{
				public void visitComponentBegin(VisualComponent aComponent)
				{
					if (componentPersistedData.get(aComponent.getName()) != null)
					{
						((PersistenceContributor)aComponent.getContributor()).restorePersistedValue(DefaultPersistenceContributionContainer.this);
						new ComponentStandardizer().standardizeChildsComponentHierarchy(aComponent);
					}
				}

				public void visitComponentEnd(VisualComponent aComponent)
				{
				}
			});
		}
		catch (WebOnSwingException e)
		{
			throw new PersistenceDoNotMatchException(this, aRootComponent, theKey, e);
		}
	}

	public List getChangedComponents(VisualComponent aRootComponent)
	{
		Vector theChangedComponents= new Vector();

		for (Iterator i= componentPersistedData.keySet().iterator(); i.hasNext();)
		{
			String theKey= (String)i.next();
			VisualComponent theComponent= aRootComponent.findComponent(theKey);

			if (!((PersistenceContributor)theComponent.getContributor()).isPersistedValueEqualToModel(this))
				theChangedComponents.add(theComponent.getName());
		}

		return theChangedComponents;
	}

	public Map getPersistedData()
	{
		return persistedData;
	}

	public void setPersistedData(Map aPersistedData)
	{
		persistedData= new Hashtable(aPersistedData);
	}

	public void clearData()
	{
		persistedData= new Hashtable();
		persistedData.put(COMPONENT_DATA, componentPersistedData= new Hashtable());
		persistedData.put(EXTERNAL_DATA, externalPersistedData= new Hashtable());
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public Map getComponentPersistedData()
	{
		return componentPersistedData;
	}

	public void setComponentPersistedData(Map aComponentPersistedData)
	{
		this.componentPersistedData= aComponentPersistedData;
	}

	public Map getExternalPersistedData()
	{
		return externalPersistedData;
	}

	public void setExternalPersistedData(Map aExternalPersistedData)
	{
		this.externalPersistedData= aExternalPersistedData;
	}
}