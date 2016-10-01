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
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.swing.components.validators.contributors.*;

public abstract class JValidator extends JLabel
{
	protected JComponent componentToValidate;
	protected String ownMessage= "";
	protected String groupMessage= "";
	protected boolean remoteValidation;
	protected boolean grouped;
	protected List validationShooters= new Vector();

	public JValidator()
	{
		remoteValidation= true;
		WosFramework.assignContributor(this, ValidatorUIContributor.class);
	}

	public JValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation)
	{
		ownMessage= aMessage;
		groupMessage= aGroupMessage;
		componentToValidate= aComponentToValidate;
		remoteValidation= hasRemoteValidation;
		grouped= false;
		WosFramework.assignContributor(this, ValidatorUIContributor.class);
	}

	public boolean doValidation()
	{
		boolean valid= !isEnabled() || performValidation();

		if (!valid)
			setText(getOwnMessage());
		else
			setText("");

		return valid;
	}

	protected String getComponentValue(JComponent aComponent)
	{
		if (aComponent instanceof JTextField)
			return ((JTextField) aComponent).getText();
		if (aComponent instanceof JPasswordField)
			return new String(((JPasswordField) aComponent).getPassword());
		else if (aComponent instanceof JTextArea)
			return ((JTextArea) aComponent).getText();
		else if (aComponent instanceof JList)
			return ((JList) aComponent).getSelectedValue().toString();
		else if (aComponent instanceof JComboBox)
			return ((JComboBox) aComponent).getSelectedItem().toString();
		else if (aComponent instanceof AbstractButton)
		{
			AbstractButton button= (AbstractButton) componentToValidate;
			DefaultButtonModel buttonModel= (DefaultButtonModel) button.getModel();
			if (buttonModel.getGroup() != null && buttonModel.getGroup().getSelection() != null)
				buttonModel= (DefaultButtonModel) buttonModel.getGroup().getSelection();

			return buttonModel.getActionCommand();
		}

		throw new WebOnSwingException("Component not supported by this validator: " + componentToValidate);
	}

	protected abstract boolean performValidation();

	public void addShooter(JComponent aComponent)
	{
		validationShooters.add(aComponent);
	}

	public boolean isRemoteValidation()
	{
		return remoteValidation;
	}

	public void setRemoteValidation(boolean b)
	{
		remoteValidation= b;
	}

	public JComponent getComponentToValidate()
	{
		return componentToValidate;
	}

	public void setComponentToValidate(JComponent component)
	{
		componentToValidate= component;
	}

	public String getGroupMessage()
	{
		return groupMessage;
	}

	public void setGroupMessage(String aString)
	{
		groupMessage= aString;
	}

	public String getOwnMessage()
	{
		return ownMessage;
	}

	public void setOwnMessage(String aString)
	{
		ownMessage= aString;
	}

	public boolean isGrouped()
	{
		return grouped;
	}

	public void setGrouped(boolean b)
	{
		grouped= b;
	}

	public List getValidationShooters()
	{
		return validationShooters;
	}

	public void setValidationShooters(List aValidationShooters)
	{
		this.validationShooters= aValidationShooters;
	}
}