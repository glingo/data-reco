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

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.wrapping.*;

/**
 * Visita cada componente de una ventana, busca por cada uno el contribuidor que
 * le corresponda y lo asigna a una propiedad del JComponent
 * 
 * @author Fernando Damian Petrola
 */
public class ComponentContributorAssigner extends ComponentVisitorAdapter
{
	public static final String CONTRIBUTOR_NAME_PROPERTY= "theContributor";
	protected String theWindowName;
	protected ContributorManager theContributionStateManager= new ContributorManager();

	public ComponentContributorAssigner(VisualComponent aTopComponent)
	{
		theWindowName= aTopComponent.getTypeId();
	}

	public void assignContributorsFrom(VisualComponent aComponent)
	{
		aComponent.accept(this);
	}

	public void visitComponentBegin(VisualComponent aComponent)
	{
		ComponentContributor theContributor= getContributor(aComponent);
		aComponent.setContributor(theContributor);
		theContributor.setComponent(aComponent);
	}

	protected ComponentContributor getContributor(VisualComponent aComponent)
	{
		ComponentContributor result= aComponent.getContributor();

		if (result == null)
		{
			String theContributorClassName= (String) aComponent.getClientProperty(CONTRIBUTOR_NAME_PROPERTY);

			if (theContributorClassName == null || theContributorClassName.equals(""))
			{
				theContributorClassName= theContributionStateManager.getContributorClassNameFromTreeState(theWindowName, aComponent.getName());

				if (theContributorClassName == null || theContributorClassName.equals(""))
					theContributorClassName= WosFramework.getInstance().getContributorManager().getDefaultComponentContributorClassName(aComponent.getUIClassID());
			}

			try
			{
				result= (ComponentContributor) Class.forName(theContributorClassName).newInstance();
			}
			catch (Exception e)
			{
				throw new WebOnSwingException(e);
			}
		}
		
		return result;
	}
}