// Parallax Object
// multiplanar scrollable space where other widgets and layers can be placed and accessed
// 2000.10.02

// by IlMaestro

// DynAPI Copyright (C) 1999 Dan Steinman
// Distributed under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynduo/
IbsParallax = function(worldw,worldh,bgcolor,borderColor) {
	// Inheritance
	this.DynLayer = DynLayer;
	this.DynLayer();
	this.id = "IbsParallaxObject"+IbsParallax.Count++;

	// Create object
	this.worldw = worldw;				// Virtual size of the '3D' world
	this.worldh = worldh;				// created inside the window
	// Behaviour variables
	this.margin = 2;				// thickness of the window's border
	this.bgcolor = bgcolor;				// window's background color
	this.borderColor = borderColor;			// border color ( so useful my comments are )

	this.lensx = 0.02;				// these values are used to calculate
	this.lensy = 0.02;				// how the Z-axis coord. of an obj
							// modifies the X and Y axis displaying.
							// The bigger it is the further the objs will
							// seem.
	this.maxdeep = 1000;				// Maximum deep of objs. Tiled bg is placed
							// at this deep
	this.background = null;				// If set to an image source, it will display
							// it tiled so it covers all screen
	this.reactTime = 4;				// How fast the camera catches up with the mouse
							// the smaller ( nonzero ) the faster
	this.timeout = 100;				// Timeout
	// Internal status variables
	this.camerax = this.w/2;			// Camera coords are what generate the view
	this.cameray = this.h/2;			// of our world
	this.cursorx = this.camx = 0;			// The place in the screen where the mouse was
	this.cursory = this.camy = 0;			// last seen. this.camera values try to catch up
							// with them all the time
	this.movement = false;				// In order to program the setTimeouts()

	// The layers
	this.canvas = this.IBPaddChild(new DynLayer());
	this.canvas.setBgColor(bgcolor);
	this.setBgColor(borderColor);

	// Event listeners
	this.cl = new EventListener(this);
	this.cl.onmousemove = function(e) {
		var p = e.getTarget();
		p.setCursor(e.getX(),e.getY());
		if(!p.movement) p.camera();
	}
	this.canvas.addEventListener(this.cl);
	this.chl = new EventListener(this);
	this.chl.onmousemove = function(e) {
		var p = e.getTarget();
		var s = e.getSource();
		p.setCursor(s.realX+e.getX(),s.realY+e.getY());
		if(!p.movement) p.camera();
		e.setBubble(false);
	}
	this.lc = new EventListener(this);
	this.onCreate (function() {
		if(is.ie || is.dom) this.css.overflow = 'hidden';
	});
	
	this.addEventListener(this.lc);
}
IbsParallax.prototype = new DynLayer();
IbsParallax.prototype.getClass = function() { return IbsParallax }
IbsParallax.prototype.IBPaddChild = IbsParallax.prototype.addChild;
IbsParallax.prototype.addChild = function() {
	var a;
	for(var i=0;i<arguments.length-1;i+=2) {
		a = arguments[i];
		a.oldMoveTo = a.moveTo;
		a.moveTo = IbsParallaxDynLayerMoveTo;
		a.pointerToParallax = this;
		a.deep = arguments[i+1];
		a.setZIndex(this.maxdeep-arguments[i].deep);
		this.canvas.addChild(a);
		a.addEventListener(this.chl);
	}
}
IbsParallax.prototype.removeChild = function() {
	var a;
	for(var i=0;i<arguments.length;i++) {
		a = arguments[i];
		a.moveTo = a.oldMoveTo;
		a.pointerToParallax = null;
		this.canvas.removeChild(a);
		a.removeEventListener(this.chl);
	}
}
IbsParallax.prototype.setMargin = function(m) {
	this.margin = m;
	this.setSize(this.getWidht(),this.getHeight());
}
IbsParallax.prototype.IBPSetSize = IbsParallax.prototype.setSize;
IbsParallax.prototype.setSize = function(w,h) {
	this.IBPSetSize(w,h);
	this.canvas.moveTo(this.margin,this.margin);
	this.canvas.setSize(w-2*this.margin,h-2*this.margin);
	this.redraw();
}
// These methods allow you to get the maximum visible coordinate for a given deep
// If deep is 0, they return the size of the viewport.
IbsParallax.prototype.minY = function(deep) { return -this.IscaleY(deep,this.h/2) }
IbsParallax.prototype.maxY = function(deep) { return this.IscaleY(deep,this.h/2) }
IbsParallax.prototype.minX = function(deep) { return -this.IscaleX(deep,this.w/2) }
IbsParallax.prototype.maxX = function(deep) { return this.IscaleX(deep,this.w/2) }
// Used to convert scales
IbsParallax.prototype.IscaleX = function(number,deep) { return (1+(deep*this.lensx))*number }
IbsParallax.prototype.IscaleY = function(number,deep) { return (1+(deep*this.lensy))*number }
IbsParallax.prototype.ScaleX = function(number,deep) { return number/(1+(deep*this.lensx)) }
IbsParallax.prototype.ScaleY = function(number,deep) { return number/(1+(deep*this.lensy)) }
// These don't need to be used outside unless you want to make the obj to scroll
// on your own events
IbsParallax.prototype.redraw = function() {
	// Most of the calculation is done inside moveTo
	for(i=0;i<this.canvas.children.length;i++) {
		var c = this.canvas.children[i];
	  	c.moveTo(c.getX(),c.getY());
	}
}
// Sets the cursor, the internal pointer to the mouse coords, to be a new value. Camera
// always tries to get to these values.
IbsParallax.prototype.setCursor = function(x,y) {
	this.cursorx = x - (this.w/2);
	this.cursory = y - (this.h/2);
}
// This method is called once the movement has started so the camera tries to get where
// the mouse pointer is. This way the movement is way smoother than just placing the
// camera where the mouse is.
IbsParallax.prototype.camera = function() {

	var facx = ((this.cursorx-this.camerax)/this.reactTime);
	var facy = ((this.cursory-this.cameray)/this.reactTime);

	var ncx = (facx+this.camerax);
	var ncy = (facy+this.cameray);

	if(facx>this.reactTime || facx<-this.reactTime || facy>this.reactTime || facy<-this.reactTime)
		{ this.setCamera(ncx,ncy);
		  this.redraw();
		  eval("setTimeout('"+this+".camera()',this.timeout)");
		  this.movement = true }
	else this.movement = false;
}
// This method is the one which moves tha camera pos. If anyone wants to set a new viewpoint
// from another script, without using the mouse event, must call
//
// myparallax.setCamera(coodx,coordy);   assuming 0,0 is the middle of the viewport
// myparallax.redraw();
// 
// I won't try to explain the maths involved either here and inside ParallaxDynLayerMoveTo.
// Let's say it works.
IbsParallax.prototype.setCamera = function(x,y) {
	this.camerax = x;
	this.cameray = y;
	var facx = Math.max(this.worldw,this.w)/this.w;
	var facy = Math.max(this.worldh,this.h)/this.h;
	this.camx = this.camerax*(facx-1);
	this.camy = this.cameray*(facy-1);
}
// All layers inside the parallax get its moveTo method overwritten by this one so they
// can still be moved from outside or whatever and the displaying is always related to
// their position and deep inside the parallax + camera conditions.
IbsParallaxDynLayerMoveTo = function(x,y) {
	var t = this.pointerToParallax;
	var d = this.deep || 0;
	// Layers are moved centered. I mean the x,y coords refer to the place where the 
	// middle point of the layer will go.
	this.oldMoveTo( (t.w/2) + (x-t.camx)/(1+(d*t.lensx)) - this.w/2,
			(t.h/2) + (y-t.camy)/(1+(d*t.lensy)) - this.h/2);
	// The above method sets the layer's x, y coords to the 'after scaling' values, so
	// we cheat and set them back to the values we want, without scaling
	this.realX = this.x; this.x = x;
	this.realY = this.y; this.y = y;
}
IbsParallax.Count = 0;
