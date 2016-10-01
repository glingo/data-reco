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

function JGroupValidator(aValidatorName, aHeadMessage, isGrouped, componentsToValidate, shooters)
{
	this.inheritFrom= JValidator;
	this.inheritFrom(aValidatorName, null, "", "", isGrouped, shooters);
	this.componentsToValidate= componentsToValidate;
	this.headMenssage= aHeadMessage;

	this.doValidation= function()
	{
		this.isValid= true;
		this.ownMessage= "";

		if (this.mustValidate())
		{
			for (var i= 0; i < this.componentsToValidate.length; i++)
			{
				var validator= eval(this.componentsToValidate[i]);
				var isOneValid= validator.doValidation();
	
				if (!isOneValid && validator.groupMessage.length > 0)
					this.ownMessage= this.ownMessage + validator.groupMessage + "<br>";
	
				this.isValid &= isOneValid;
			}
	
			if (this.ownMessage != "")
				this.ownMessage= "<b>" + this.headMenssage + "</b><br>" + this.ownMessage;
	
			this.updateTagMessage();
		}
		
		return this.isValid;
	}
}
