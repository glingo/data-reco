// WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
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

package net.ar.webonswing.swing.components.validators.contributors;

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.swing.components.validators.*;
import net.ar.webonswing.ui.*;
import net.ar.webonswing.wrapping.*;

public class ValidatorUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JValidator theValidator= (JValidator) getJComponent();

		RemoteHelper.removeToolTipListeners(theValidator);
		Tag theTag= new Tag("span");
		StringBuffer theScript= new StringBuffer();

		if (theValidator.isEnabled())
		{
			theTag.addTextToContainer(RemoteHelper.str2web(theValidator.getText())).addAttribute("id", theComponent.getName());

			for (int i= 0; i < theComponent.getChildCount(); i++)
			{
				VisualComponent child= theComponent.getChildAt(i);
				theContribManager.getContainerRenderer().render(child);
				theScript.append(theContribManager.getComponentInitScripts(child)[0]);
			}

			if (theValidator.isRemoteValidation())
				theScript.append(getScript(theValidator));
		}

		theContribManager.doContribution(theComponent, theTag, theTag, theScript.toString());
	}

	protected String getScript(JValidator aValidator)
	{
		JComponent componentToValidate= aValidator.getComponentToValidate();
		String componentToValidateName= componentToValidate != null ? WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(componentToValidate).getName() : "";
		return getJsCreationScript(new Object[]{theComponent.getName(), componentToValidateName, aValidator.getOwnMessage(), aValidator.getGroupMessage(), aValidator.isGrouped() + "", getShootersNames(aValidator).toArray()});
	}

	protected String getJsCreationScript(Object[] parameters)
	{
		return RemoteHelper.createJsInstance(theComponent.getName(), getJsValidatorClassName(), parameters);
	}

	protected String getJsValidatorClassName()
	{
		return WosHelper.getNoPackageClassName(getJComponent());
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/validators/JValidator.js");

		JValidator theValidator= (JValidator) getJComponent();
		
		if (theValidator.isRemoteValidation())
			aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/validators/" + getJsValidatorClassName() + ".js");
	}

	protected Vector getShootersNames(JValidator aValidator)
	{
		Vector result= new Vector();
		for (Iterator i= aValidator.getValidationShooters().iterator(); i.hasNext();)
			result.add(WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(i.next()).getName());
		return result;
	}
}