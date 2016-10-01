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

package net.ar.webonswing.swing.components.validators;

import java.util.*;

import javax.swing.*;
import net.ar.webonswing.*;
import net.ar.webonswing.swing.components.validators.contributors.*;

public class JGroupValidator extends JValidator
{
	protected String headMessage= "Please fix the following errors:";
	protected List validators;

	public JGroupValidator()
	{
		init();
	}

	public JGroupValidator(JValidator[] aValidators, boolean hasRemoteValidation)
	{
		super(null, "", "", hasRemoteValidation);
		init();
		addValidators(aValidators);
	}

	public JGroupValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation)
	{
		super(aComponentToValidate, aMessage, aGroupMessage, hasRemoteValidation);
		init();
	}

	public JGroupValidator(String aHeadMessage, boolean hasRemoteValidation)
	{
		this(null, aHeadMessage, hasRemoteValidation);
	}

	public JGroupValidator(JComponent aComponentToValidate, String aHeadMessage, boolean hasRemoteValidation)
	{
		this(aComponentToValidate, "", "", hasRemoteValidation);
		headMessage= aHeadMessage;
	}

	protected void init()
	{
		validators= new Vector();
		WosFramework.assignContributor(this, GroupValidatorUIContributor.class);
	}

	public JValidator addValidator(JValidator aValidator)
	{
		validators.add(aValidator);
		aValidator.setGrouped(true);
		return aValidator;
	}

	public void addValidators(JValidator[] aValidators)
	{
		addValidators(Arrays.asList(aValidators));
	}

	public void addValidators(Collection aValidators)
	{
		for (Iterator i= aValidators.iterator(); i.hasNext();)
			addValidator((JValidator) i.next());
	}

	public JValidator removeValidator(JValidator aValidator)
	{
		validators.remove(aValidator);
		return aValidator;
	}

	protected boolean performValidation()
	{
		StringBuffer message= new StringBuffer();
		message.append("<html><b>").append(headMessage).append("</b><br>");
		boolean result= true;

		for (Iterator i= validators.iterator(); i.hasNext();)
		{
			JValidator validator= ((JValidator) i.next());
			boolean isValid= validator.doValidation();
			result&= isValid;
			if (!isValid && validator.getGroupMessage().length() > 0)
				message.append(validator.getGroupMessage()).append("<br>");
		}

		if (result)
			message= new StringBuffer();

		setGroupMessage(message.toString());
		setOwnMessage(message.toString());
		return result;
	}

	public List getValidators()
	{
		return validators;
	}

	public void setValidators(List aList)
	{
		validators= aList;
	}

	public String getHeadMessage()
	{
		return headMessage;
	}

	public void setHeadMessage(String aString)
	{
		headMessage= aString;
	}
}