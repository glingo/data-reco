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

package net.ar.webonswing.managers.contributors;

import java.io.*;

public class ComponentContributorRelationship implements Serializable
{
	protected String contributorClass;
	protected String uiId;

	public ComponentContributorRelationship()
	{
	}

	public ComponentContributorRelationship(String aContributorClassName, String aComponentUIClassId)
	{
		contributorClass= aContributorClassName;
		uiId= aComponentUIClassId;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof ComponentContributorRelationship)
			return getComponentUIClassId().equals(((ComponentContributorRelationship) obj).getComponentUIClassId());
		else
			return getComponentUIClassId().equals(obj.toString());
	}

	public int hashCode()
	{
		return getComponentUIClassId().hashCode();
	}

	public String getComponentUIClassId()
	{
		return uiId;
	}
	public void setComponentUIClassId(String unComponentUIClassId)
	{
		uiId= unComponentUIClassId;
	}
	public String getContributorClassName()
	{
		return contributorClass;
	}
	public void setContributorClassName(String unContributorClassName)
	{
		contributorClass= unContributorClassName;
	}
	
	public String toString()
	{
		return getComponentUIClassId();
	}
}