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

import javax.swing.JComponent;

import net.ar.webonswing.WosFramework;
import net.ar.webonswing.swing.components.validators.contributors.RangeValidatorUIContributor;

public class JRangeValidator extends JGroupValidator
{
	protected JCompareValidator lowValidator, highValidator;
	protected JCompareValidator.Type type= JCompareValidator.Type.STRING;

	public JRangeValidator()
	{
		setContributor();
	}

	public JRangeValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation, Object aLowValue, Object aHighValue, JCompareValidator.Type aType)
	{
		super(aComponentToValidate, aMessage, aGroupMessage, hasRemoteValidation);

		setType(aType);
		setLowValue(aLowValue);
		setHighValue(aHighValue);

		setContributor();
	}

	public JRangeValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation, JComponent aLowComponent, JComponent aHighComponent, JCompareValidator.Type aType)
	{
		super(aComponentToValidate, aMessage, aGroupMessage, hasRemoteValidation);

		setType(aType);
		setLowComponent(aLowComponent);
		setHighComponent(aHighComponent);

		setContributor();
	}

	protected void setContributor()
	{
		WosFramework.assignContributor(this, RangeValidatorUIContributor.class);
	}

	protected boolean performValidation()
	{
		return lowValidator.doValidation() && highValidator.doValidation();
	}

	public JComponent getHighComponent()
	{
		return highValidator.getComponentToCompare();
	}

	public JComponent getLowComponent()
	{
		return lowValidator.getComponentToCompare();
	}

	public Object getHighValue()
	{
		return highValidator.getValueToCompare();
	}

	public Object getLowValue()
	{
		return lowValidator.getValueToCompare();
	}

	public void setHighComponent(JComponent highComponent)
	{
		highValidator= changeValidator(highValidator, new JCompareValidator(getComponentToValidate(), "", "", isRemoteValidation(), highComponent, JCompareValidator.Operation.lessThanEqual, getType()));
	}

	public void setHighValue(Object highValue)
	{
		highValidator= changeValidator(highValidator, new JCompareValidator(getComponentToValidate(), "", "", isRemoteValidation(), highValue, JCompareValidator.Operation.lessThanEqual, getType()));
	}

	public void setLowComponent(JComponent lowComponent)
	{
		lowValidator= changeValidator(lowValidator, new JCompareValidator(getComponentToValidate(), "", "", isRemoteValidation(), lowComponent, JCompareValidator.Operation.greaterThanEqual, getType()));
	}

	public void setLowValue(Object lowValue)
	{
		lowValidator= changeValidator(lowValidator, new JCompareValidator(getComponentToValidate(), "", "", isRemoteValidation(), lowValue, JCompareValidator.Operation.greaterThanEqual, getType()));
	}

	protected JCompareValidator changeValidator(JCompareValidator oldValidator, JCompareValidator newValidator)
	{
		if (oldValidator != null)
			remove(removeValidator(oldValidator));

		if (newValidator != null)
			add(addValidator(newValidator));
		
		return newValidator;
	}

	public JCompareValidator.Type getType()
	{
		return type;
	}

	public void setType(JCompareValidator.Type aType)
	{
		this.type= aType;

		if (lowValidator != null)
			lowValidator.setType(type);

		if (highValidator != null)
			highValidator.setType(type);
	}
}