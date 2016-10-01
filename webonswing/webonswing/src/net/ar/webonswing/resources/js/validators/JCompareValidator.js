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

function JCompareValidator(aValidatorName, aComponentToValidate, anOwnMessage, aGroupMessage, isGrouped, aComponentToCompare, aValueToCompare, aType, aMatchValues, shooters)
{
	this.inheritFrom= JValidator;
	this.inheritFrom(aValidatorName, aComponentToValidate, anOwnMessage, aGroupMessage, isGrouped, shooters);

	this.componentToCompare= aComponentToCompare;
	this.valueToCompare= aValueToCompare;
	this.matchValues= aMatchValues;
	this.type= aType;

	this.doValidation= function()
	{
		this.isValid= true;
		
		if (this.mustValidate())
		{
			var element= getElement(this.componentToValidate);
			
			if (element != null)
			{
				var value= this.getComponentValue(this.componentToValidate);
				
				if ((this.valueToCompare.length  + this.componentToCompare.length) > 0)
					var compareValue= this.valueToCompare.length > 0 ? this.valueToCompare : this.getComponentValue(this.componentToCompare);
				else
					var compareValue= value;
	
				var comparison= this.compareTo(value, compareValue, this.type);
				this.isValid= (comparison == this.matchValues[0] || comparison == this.matchValues[1] || comparison == this.matchValues[2]) && (comparison != -2);
				this.updateTagMessage();
			}
		}

		return this.isValid;
	}

	this.compareTo= function(aValue, aCompareValue, aType)
	{
		var v1= aValue;
		var v2= aCompareValue;

		if (aType == "integer")
		{
			v1= parseInt(v1, 10);
			v2= parseInt(v2, 10);

			if (v1 != parseFloat(aValue) || v2 != parseFloat(aCompareValue))
				return -2;
		}
		else if (aType == "double")
		{
			v1= parseFloat(v1);
			v2= parseFloat(v2);
		}
		else if (aType.indexOf("date") == 0)
		{
			pattern= aType.substring(5, aType.length);
			v1= getDateFromFormat(v1, pattern);
			v2= getDateFromFormat(v2, pattern);

			if (v1 == 0 || v2 == 0)
				return -2;
		}

		if (aType != "string" && (isNaN(v1) || isNaN(v2)))
			return -2;
		
		if (v1 < v2)
			return -1;
		if (v1 > v2)
			return 1;
		else
			return 0;
	}
}
