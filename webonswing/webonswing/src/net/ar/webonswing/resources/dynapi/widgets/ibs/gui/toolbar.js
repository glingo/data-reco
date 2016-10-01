// ToolBar Object
// Generic toolbar where elements ( typically buttons ) can be added. Extends IbsBoldLayer
// by IlMaestro ( ilmaestro@cantir.com ) from Pascal Bestebroer's code

// DynAPI Copyright (C) 2000 Dan Steinman
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

/* Still lacks lots of things. Should re-organize its contents upon resize. Now
   only will place them properly if the bar's size is set before adding any
   widgets */

TOOLBAR_LEFT = 0
TOOLBAR_RIGHT = 1
TOOLBAR_CENTER = 2
IbsToolBar = function(x,y,w,h) {
	// Init
	this.DynLayer = IbsBoldLayer
	this.DynLayer()

	// Set size and position	
	this.setSize(w||50,h||26)
	this.setPosition(x||0,y||0)

	// Properties	
	this.items = new Array()
	this.newItemXL = 8
	this.newItemXR = this.w - 4
	this.border = 2
	// These properties allow to know the size of the bigger widget
	// inside, so the toolbar can be resized to them in order
	// to accomodate everything
	this.maxW = 0		
	this.maxH = 0
	}
// Inherits from...
IbsToolBar.prototype = new IbsBoldLayer

IbsToolBar.prototype.addSeperator = function(where) {
	var a = new IbsBoldLayer()
	a.setSize(this.border,this.getHeight()-3*this.border)
	a.setShadowEffect(BOLDLAYER_INVERSE)
	this.addWidget(a,where)
	}
IbsToolBar.prototype.addWidget=function(widget,where) {
	var i = this.items.length

	if(widget.getWidth()>this.maxW) this.maxW = widget.getWidth()
	if(widget.getHeight()>this.maxH) this.maxH = widget.getHeight()

	if(!where || where==TOOLBAR_LEFT)
	 { this.items[i]=widget
	   this.items[i].setPosition(this.newItemXL,this.border) 
	   this.newItemXL+=this.items[i].getWidth()+4 }
	else if(where==TOOLBAR_RIGHT)
	 { this.items[i]=widget
	   this.newItemXR-=this.items[i].getWidth()+4
	   this.items[i].setPosition(this.newItemXR,this.border) }

	this.addChild(this.items[i])
	}
IbsToolBar.prototype.setFgColor = function(c) {
	for (var i=0; i<this.items.length; i++) this.items[i].setBgColor(c)
	}