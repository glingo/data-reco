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
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

function Spinner(aWidth, aHeight, aValue, aMinimum, aMaximum, aName) 
{
	this.inheritFrom= DynLayer;
	this.inheritFrom();
	 
	this.spinner= this;
    this.setSize(aWidth, aHeight);
	this.value= aValue;
	this.updateListener="null";
	this.name= aName; 
	this.minimum= aMinimum;
	this.maximum= aMaximum;

	this.imageSize= 22;
	this.upButton= new Button('+',aWidth - this.imageSize, 0,this.imageSize, aHeight / 2);
	
	this.upEvent= new EventListener(this);
	this.upEvent.onclick = function (e) 
	{
		var spinner= e.getSource().parent;
		
		if (spinner.maximum == null || spinner.value < spinner.maximum)
		{
			spinner.value++;
			spinner.valueUpdated();
		}
	};
	this.upButton.addEventListener (this.upEvent);

	this.downButton= new Button('-',aWidth - this.imageSize, aHeight /2,this.imageSize, aHeight / 2);
	this.downEvent= new EventListener(this);
	this.downEvent.onclick = function (e) 
	{
		var spinner= e.getSource().parent;

		if (spinner.minimum == null || spinner.value > spinner.minimum)
		{
			spinner.value--;
			spinner.valueUpdated();
		}
	};
	
	this.valueUpdated = function () 
	{
		var inputField= document.getElementsByName(this.name+".input")[0].value= this.value;
	};
	
	this.downButton.addEventListener (this.downEvent);
	
	this.inputElement= "<input type=\"text\" name=\""+aName+".input\" style=\"width:"+(aWidth - this.imageSize) +"; height:"+aHeight+"\" value=\""+this.value+"\" />";
	this.textBox= new DynLayer(this.inputElement, 0, 0, aWidth - this.imageSize , aHeight);
	
	this.addChild(this.upButton);
	this.addChild(this.downButton);
	this.addChild(this.textBox);
	this.setVisible(true);
	
	return this;
}

Spinner.prototype=new DynLayer;
