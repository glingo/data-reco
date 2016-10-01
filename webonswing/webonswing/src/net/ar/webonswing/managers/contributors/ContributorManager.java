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

package net.ar.webonswing.managers.contributors;

import java.util.*;

import net.ar.webonswing.*;
import net.ar.webonswing.ui.*;

import org.apache.commons.logging.*;

public class ContributorManager
{
	private static final String CONTRIBUTION_STATE_ID= "contributor-state";
	protected Hashtable contributorMappings= new Hashtable();

	public ContributorManager()
	{
	}

	public ContributorManager(Hashtable aTable)
	{
		contributorMappings= aTable;
	}

	public String getContributorClassNameFromTreeState(String aWindowName, String aComponentName)
	{
		return (String) WosFramework.getInstance().getWindowTreeStateManager().getComponentState(aWindowName, aComponentName, CONTRIBUTION_STATE_ID);
	}
	public void setContributorClassNameToTreeState(String aWindowName, String aComponentName, String aContributionClass)
	{
		WosFramework.getInstance().getWindowTreeStateManager().setComponentState(aWindowName, aComponentName, CONTRIBUTION_STATE_ID, aContributionClass);
	}

	public String getDefaultComponentContributorClassName(String anUiId)
	{
		Object theEntry= contributorMappings.get(anUiId);

		if (theEntry != null)
			return ((ComponentContributorRelationship) theEntry).getContributorClassName();
		else
		{
			LogFactory.getLog(ContributorManager.class).warn("Cannot find a contributor for: " + anUiId);
			
			return GenericUIContributor.class.getName();
		}
	}

	public Hashtable getContributorsTable()
	{
		return contributorMappings;
	}
	public void setContributorsTable(Hashtable unContributorsTable)
	{
		contributorMappings= unContributorsTable;
	}
}