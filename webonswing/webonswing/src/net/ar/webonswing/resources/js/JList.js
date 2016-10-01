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

function ListFinishListener(aList)
{
	this.inheritFrom= FinishListener;
	this.inheritFrom();
	this.theList= aList;
	this.theResult= "";

	this.finishPerformed= function(aFinishEvent)
	{
		this.theResult= "";
		var theListElement= getElement(this.theList);
			
		if (theListElement != null)
		{
			var theSeparator= "";

			for (var i= 0; i < theListElement.options.length; i++)
			{
				var theOption= theListElement.options[i];
				this.theResult += theSeparator + wosescape(theOption.text);
				if (theOption.selected)
					this.theResult += "_s";
				theSeparator= "_,";
			}
				
			addOrUpdateHidden(aList + ".values", this.theResult);
		}
	};
}
