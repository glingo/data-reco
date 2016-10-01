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

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.swing.components.validators.contributors.*;

public class JRequiredFieldValidator extends JValidator
{
	public JRequiredFieldValidator()
	{
		super(null, "*", "", true);

		WosFramework.assignContributor(this, RequiredFieldValidatorUIContributor.class);
	}

	public JRequiredFieldValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation)
	{
		super(aComponentToValidate, aMessage, aGroupMessage, hasRemoteValidation);

		WosFramework.assignContributor(this, RequiredFieldValidatorUIContributor.class);
	}

	public JRequiredFieldValidator(JComponent aComponentToValidate, String aMessage, boolean hasRemoteValidation)
	{
		this(aComponentToValidate, aMessage, "", hasRemoteValidation);
	}

	public JRequiredFieldValidator(JComponent aComponentToValidate)
	{
		this(aComponentToValidate, "*", "", true);
	}

	public JRequiredFieldValidator(JComponent aComponentToValidate, String aGroupMessage)
	{
		this(aComponentToValidate, "*", aGroupMessage, true);
	}

	protected boolean performValidation()
	{
		if (componentToValidate instanceof JTextField || componentToValidate instanceof JPasswordField || componentToValidate instanceof JTextArea)
			return getComponentValue(componentToValidate).length() > 0;
		else if (componentToValidate instanceof JList)
			return ((JList) componentToValidate).getSelectedIndex() != -1;
		else if (componentToValidate instanceof AbstractButton)
		{
			AbstractButton button= (AbstractButton) componentToValidate;
			DefaultButtonModel buttonModel= (DefaultButtonModel) button.getModel();

			return buttonModel.getGroup() != null ? buttonModel.getGroup().getSelection() != null : button.isSelected();
		}
		else if (componentToValidate instanceof JComboBox)
		{
			JComboBox comboBox= (JComboBox) componentToValidate;
			int selectedIndex= comboBox.getSelectedIndex();

			if (selectedIndex != -1)
				return !comboBox.getModel().getElementAt(selectedIndex).equals("");
			else
				return false;
		}

		throw new WebOnSwingException("Component not supported by RequiredFieldValidator: " + componentToValidate);
	}
}