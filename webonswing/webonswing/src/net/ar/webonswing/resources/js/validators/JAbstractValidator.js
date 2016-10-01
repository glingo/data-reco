//WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
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

function JAbstractValidator(aValidatorName, aComponentToValidate, anOwnMessage, aGroupMessage, isGrouped)
{
	if (isGrouped == 'false')
		getComponent('page').addListener(new ValidatorFinishListener(this));

	this.validatorName= aValidatorName;
	this.componentToValidate= aComponentToValidate;
	this.ownMessage= anOwnMessage;
	this.groupMessage= aGroupMessage;
	this.isGrouped= isGrouped;
	this.isValid= true;

	this.doValidation= function()
	{
	}

	this.updateTagMessage= function()
	{
		var validatorTag= document.getElementById(this.validatorName);
		if (validatorTag != null)
			validatorTag.innerHTML= this.isValid ? "" : this.ownMessage;
	}

	this.getComponentValue= function (aComponent)
	{
		var componentElement= getElement(aComponent);

		if (componentElement != null)
		{
			var tagName= componentElement.tagName;
			
			if (tagName == "INPUT")
			{
				var inputType= componentElement.type;
				
				if (inputType == "radio")
				{
					var radios= document.getElementsByName(componentElement.name);
					
					for (var i= 0; i < radios.length; i++)
						if (radios[i].checked)
							return radios[i].value;
						
					return "";
				}
			}
			else if (tagName == "SELECT")
			{
				for (var i= 0; i < componentElement.options.length; i++)
					if (componentElement.options[i].selected && componentElement.options[i].text != "")
						return componentElement.options[i].text;
				
				return ""; 
			}
			
			return componentElement.value;
		}
	}

	function ValidatorFinishListener(aValidator)
	{
		this.inheritFrom= FinishListener;
		this.inheritFrom();
		this.validator= aValidator;

		this.finishPerformed= function(aFinishEvent)
		{
			if (this.validator.doValidation() == false)
				theState= "cancel";
		};
	}
}
