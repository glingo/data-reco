// Scrollbar
// a scrollbar/slider widget
// 2000.03.013

// DynAPI Copyright (C) 2000 Dan Steinman
// Modified by IlMaestro ( ilmaestro@cantir.com )
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/
IbsScrollBar = function(type) {
	// Init
	this.DynLayer = DynLayer
	this.DynLayer()

	// Internal variables
	this.ratiox = 0
	this.ratioy = 0
	
	// The slideable thingie
	this.knob = new IbsBoldLayer()

	// If an initial orientation was given as a parameter,
	// we set it
	if (type!=null) this.setOrientation(type)

	this.addChild(this.knob)
	this.knob.addEventListener(IbsScrollBar.dragEvents)
	this.addEventListener(IbsScrollBar.mouseEvents)
	this.knob.addEventListener(IbsScrollBar.slideEvents)

	DragEvent.enableDragEvents(this.knob)
	DragEvent.setDragBoundary(this.knob)

}

// Inherit from Dynlayer
IbsScrollBar.prototype = new DynLayer
IbsScrollBar.prototype.getSubClass=function() { return IbsScrollBar }
// Overwrite setSize
IbsScrollBar.prototype.IbsScrollBarOldSetSize = IbsScrollBar.prototype.setSize
IbsScrollBar.prototype.setSize = function(w,h) {
	if (w==null) w = this.getWidth()
	if (h==null) h = this.getHeight()
	var m = Math.min(w,h)

	this.IbsScrollBarOldSetSize(w,h)

	this.setKnobSize(this.vertical?m:Math.round(m*4/3),this.horizontal?m:Math.round(m*4/3))
	if (this.knob.x+this.knob.w>this.getWidth()) this.knob.setX(this.getWidth()-this.knob.w)
	if (this.knob.y+this.knob.h>this.getHeight()) this.knob.setY(this.getHeight()-this.knob.h)
	this.setRatio(this.ratiox,this.ratioy)
	}
IbsScrollBar.prototype.setKnobSize = function(w,h) {
	this.knob.setSize(w,h)
	}
IbsScrollBar.prototype.setOrientation = function(type) { // 0=horz,1=vert
	this.horizontal = !type
	this.vertical = type
	}
IbsScrollBar.prototype.setFgColor = function(c) {
	this.knob.setBgColor(c)
	}
IbsScrollBar.prototype.setRatio = function(rx,ry) {
	this.ratiox=rx
	this.ratioy=ry
	this.knob.setPosition(this.ratiox*(this.w-(this.knob.w)),this.ratioy*(this.h-(this.knob.h)))
	}
IbsScrollBar.prototype.findRatio = function() {
	var tx = (this.getWidth()-this.knob.w)
	var ty = (this.getHeight()-this.knob.h)
	this.ratiox = tx==0 ? 0 : (this.knob.x)/tx
	this.ratioy = ty==0 ? 0 : (this.knob.y)/ty
	}
IbsScrollBar.prototype.getRatioX = function() {return this.ratiox}
IbsScrollBar.prototype.getRatioY = function() {return this.ratioy}
IbsScrollBar.prototype.reset = function() { this.knob.setPosition(0,0); this.ratiox=this.ratioy=0; }

// Event control code for the draggable knob
IbsScrollBar.dragEvents = new EventListener()
IbsScrollBar.dragEvents.ondragmove = function (e) {
	var o = e.getSource().parent
	o.findRatio()
	o.invokeEvent("scroll")
	e.setBubble(false)
	}
IbsScrollBar.dragEvents.ondragstart = function (e) {
	var o = e.getSource().parent
	o.invokeEvent("scrollstart")
	e.setBubble(false)
	}
IbsScrollBar.dragEvents.ondragend = function (e) {
	var o = e.getSource().parent
	o.invokeEvent("scrollend")
	e.setBubble(false)
	}

// Event control code for the scrollbar itself. Note that it relys
// on the slide extension, so it should be included in any page using the
// scrollbar
IbsScrollBar.mouseEvents = new EventListener()
IbsScrollBar.mouseEvents.onmousemove = function(e) {
	var knob = e.getSource().knob
	if (knob.pathanim && knob.pathanim.playing) e.setBubble(false)
	}
IbsScrollBar.mouseEvents.onmousedown = function(e) {
	var o = e.getSource()
	if (!o.knob.pathanim || !o.knob.pathanim.playing) {
		var newx = e.getX()-o.knob.w/2
		var newy = e.getY()-o.knob.h/2
		var offW = o.getWidth()-o.knob.w
		var offH = o.getHeight()-o.knob.h
		if (newx<0) newx=0
		if (newx>=offW) newx=offW
		if (newy<0) newy=0
		if (newy>=offH) newy=offH
		o.invokeEvent("scrollstart")
		o.knob.slideTo(newx,newy)
		}
	e.setBubble(false)
	}
IbsScrollBar.mouseEvents.onmouseup = function(e) {
	e.getSource().knob.stopSlide();
	}

// These events are captured from the slide extension
// so to invoke our own when the knob is moving
IbsScrollBar.slideEvents = new EventListener()
IbsScrollBar.slideEvents.onpathrun = function(e) {
	var o = e.getSource().parent
	o.findRatio()
	o.invokeEvent("scroll")
	}
IbsScrollBar.slideEvents.onpathstart = function(e) {
	var o = e.getSource()
	// So after stoppping the slide, the knob
	// is about to be dragged
	var evt = new DynMouseEvent()
	evt.bubble = false
	evt.type = "mousedown"		
	evt.src = o
	evt.x = o.getWidth()/2
	evt.y = o.getHeight()/2
	evt.pageX = o.getPageX()+evt.x
	evt.pageY = o.getPageY()+evt.y
	o.invokeEvent("mousedown",evt)
	o.parent.invokeEvent("scrollend")
	}
