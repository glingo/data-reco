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

function Slider(width, height, minimun, maximun, value, orientation) 
{
	this.inheritFrom= DynLayer;
	this.inheritFrom();
	
    this.setSize(width, height);
	this.maximun= maximun;
	this.minimun= minimun;
	this.value= value;
	this.orientation= orientation;
	this.horizontalOrientation= 0;
	this.updateListener="null";

	this.dragablePart= new Button ();
	this.dragablePart.setSize(10, 10);
	this.dragablePart.setPosition(10, 0);
	this.dragablePart.setBgColor('grey');
	 
	this.dragEvents = new EventListener(this);

	this.dragEvents.ondragmove = function (e) 
	{
		var slider= e.getSource().parent;
		slider.setStaticPosition();
		slider.setValueFromPosition();
		
		e.preventBubble();
	};
	 
	this.setStaticPosition= function ()
	{
		if (this.orientation == this.horizontalOrientation)
			this.dragablePart.setY(this.getHeight()/2-this.dragablePart.getHeight()/2);
		else
			this.dragablePart.setX(this.getWidth()/2-this.dragablePart.getWidth()/2);
	}

	this.setSlidingPosition= function ()
	{
		if (this.orientation == this.horizontalOrientation)
			this.dragablePart.setX(this.value / this.maximun * this.getWidth());
		else
			this.dragablePart.setY(this.value / this.maximun * this.getHeight());
	}

	this.setValueFromPosition= function ()
	{
		if (this.orientation == this.horizontalOrientation)
			this.value= this.dragablePart.getX() * this.maximun  / (this.getWidth() - 10);
		else
			this.value= this.dragablePart.getY() * this.maximun  / (this.getHeight() - 10);
		
		this.value= Math.round(this.value);
		
		this.updateListener();
	}
	
	this.setStaticPosition();
	this.setSlidingPosition();
	
	this.dragEvents.ondragstart = function (e) 
	{
		e.preventBubble();
	};
	
	this.dragEvents.ondragend = function (e) 
	{
		e.preventBubble();
	};
	
	DragEvent.setDragBoundary(this.dragablePart);
	DragEvent.enableDragEvents(this.dragablePart);
	
	this.dragablePart.addEventListener(this.dragEvents);
	
	this.addChild(this.dragablePart);
	this.setVisible(true);
	
	return this;
}

Slider.prototype=new DynLayer;

