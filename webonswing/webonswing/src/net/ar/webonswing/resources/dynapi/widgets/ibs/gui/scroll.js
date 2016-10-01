// Scroll Object
// encapsulate ScrollWindow, ButtonImages and ScrollBars to generate our beloved Scroll object
// by IlMaestro ( ilmaestro@cantir.com )
//
// DynAPI Copyright (C) 2000 Dan Steinman
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

IbsScroll = function(margin,color,margincolor) {
	// Init
	this.DynLayer = DynLayer
	this.DynLayer()

	// Values
	this.margin = (margin==null)?0:margin
	this.color = color||"white"
	this.margincolor = margincolor

	// Layers
	this.screenlyr=new DynLayer()
	this.window = new IbsScrollWindow(color||"white")
	this.scrollH = new IbsScrollBar(0)
	this.scrollV = new IbsScrollBar(1)
	this.cornerlyr = new DynLayer()
	this.Bup = new IbsButtonImage()
	this.Bdown = new IbsButtonImage()
	this.Bleft = new IbsButtonImage()
	this.Bright = new IbsButtonImage()
	
	// Add elements
	this.addChild(this.screenlyr)
	this.IbsScrollOldSetBgColor(this.margincolor)
	this.screenlyr.setBgColor(this.color)	
	this.screenlyr.addChild(this.window)	
	this.window.setPosition(0,0)
	this.window.setZIndex(5)
	this.screenlyr.addChild(this.scrollH)	
	this.scrollH.setZIndex(3)
	this.screenlyr.addChild(this.scrollV)	
	this.scrollV.setZIndex(3)
	this.screenlyr.addChild(this.cornerlyr)
	this.cornerlyr.setZIndex(3)
	this.screenlyr.addChild(this.Bup)
	this.Bup.setZIndex(3)
	this.screenlyr.addChild(this.Bdown)
	this.Bdown.setZIndex(3)
	this.screenlyr.addChild(this.Bleft)
	this.Bleft.setZIndex(3)
	this.screenlyr.addChild(this.Bright)
	this.Bright.setZIndex(3)

	// Listeners
	this.window.addEventListener(IbsScroll.windowListener)
	this.Bup.addEventListener(IbsScroll.BupListener)
	this.Bdown.addEventListener(IbsScroll.BdownListener)
	this.Bleft.addEventListener(IbsScroll.BleftListener)
	this.Bright.addEventListener(IbsScroll.BrightListener)
	this.scrollV.addEventListener(IbsScroll.VbarListener)
	this.scrollH.addEventListener(IbsScroll.HbarListener)
	
}
// Inherit from Dynlayer
IbsScroll.prototype = new DynLayer
IbsScroll.prototype.getSubClass=function() { return Scroll }
IbsScroll.prototype.setMargin = function(m) {this.margin=m; this.setSize(this.w,this.h)}
IbsScroll.prototype.setTheme = function(dir) {
        this.cornerlyr.setSize(16,16)
	this.Bup.setImages([dir+'scroll-up0.gif',16,16],[dir+'scroll-up0.gif',16,16],[dir+'scroll-up1.gif',16,16])
	this.Bdown.setImages([dir+'scroll-dn0.gif',16,16],[dir+'scroll-dn0.gif',16,16],[dir+'scroll-dn1.gif',16,16])
	this.Bleft.setImages([dir+'scroll-lt0.gif',16,16],[dir+'scroll-lt0.gif',16,16],[dir+'scroll-lt1.gif',16,16])
	this.Bright.setImages([dir+'scroll-rt0.gif',16,16],[dir+'scroll-rt0.gif',16,16],[dir+'scroll-rt1.gif',16,16])
}
// Overwrite setSize method
IbsScroll.prototype.IbsScrollOldSetSize = IbsScroll.prototype.setSize
IbsScroll.prototype.setSize = function(w,h) {

        this.setVisible(false)
        this.IbsScrollOldSetSize(w,h)
        this.screenlyr.setSize(w-2*this.margin,h-2*this.margin)
        this.screenlyr.setPosition(this.margin,this.margin)

 	var ww = this.w-2*this.margin-16
	var wh = this.h-2*this.margin-16
        var sw = this.w-(2*this.margin)-16-16-16+1
	var sh = this.h-(2*this.margin)-16-16-16+1
	var c = this.window.content
	var cw = c.getWidth()
	var ch = c.getHeight()
	var cx = c.x
	var cy = c.y

	this.scrollH.setSize(sw,16)
	this.scrollV.setSize(16,sh)

	this.Bup.setPosition(ww,0)
	this.Bdown.setPosition(ww,wh-16+1)
	this.Bleft.setPosition(0,wh)
	this.Bright.setPosition(ww-16+1,wh)

        this.scrollV.setPosition(ww,16)
        this.scrollH.setPosition(16,wh)
        this.cornerlyr.setPosition(ww,wh)

 	this.window.setSize(w-2*this.margin-16,h-2*this.margin-16)
        this.setVisible(true)
}
IbsScroll.prototype.reset = function() { this.window.scrollTo(0,0) }
IbsScroll.prototype.onWindowScrollStart = function() {
	this.invokeEvent("scrollstart")
}
IbsScroll.prototype.onWindowDScroll = function() {
	this.invokeEvent("scroll")
}
IbsScroll.prototype.onWindowScroll = function() {
	if(this.window.enableVScroll) 
	 this.scrollV.setRatio(0,-this.window.content.y/this.window.availableScrollY)
	if(this.window.enableHScroll)
         this.scrollH.setRatio(-this.window.content.x/this.window.availableScrollX,0)
	this.invokeEvent("scroll")
}
IbsScroll.prototype.onWindowScrollEnd = function() {
	// Redraw if forms inside
	if(is.ns && this.window.content.doc && this.window.content.doc.forms && this.window.content.doc.forms.length) 
	 { this.window.setVisible(false); 
	   setTimeout(this.object+".window.setVisible(true)",0) }

	this.invokeEvent("scrollend")
}
IbsScroll.prototype.scrollTo = function(rx,ry) {
	this.window.scrollTo(rx,ry)
}
IbsScroll.prototype.setHTML = function(h) { 
	this.window.setHTML(h)
}
IbsScroll.prototype.IbsScrollOldSetBgColor = IbsScroll.prototype.setBgColor
IbsScroll.prototype.setBgColor = function(c) {
	if(!c) return;
	this.scrollH.setBgColor(c)
	this.scrollV.setBgColor(c)
	this.cornerlyr.setBgColor(c)
}
IbsScroll.prototype.setFgColor = function(c) {
	this.scrollH.setFgColor(c)
	this.scrollV.setFgColor(c)
}
IbsScroll.prototype.checkBars = function() { 
	var v=this.window.enableVScroll
	var h=this.window.enableHScroll
	this.scrollV.knob.setVisible(v)
	this.scrollH.knob.setVisible(h)
	this.onWindowScroll()
}

// Events
/////////////////
IbsScroll.windowListener = new EventListener()
IbsScroll.windowListener.ondirectscroll = function(e)
	{ e.getSource().parent.parent.onWindowDScroll() }
IbsScroll.windowListener.onscroll = function(e)
	{ e.getSource().parent.parent.onWindowScroll() }
IbsScroll.windowListener.onscrollstart = function(e)
	{ e.getSource().parent.parent.onWindowScrollStart() }
IbsScroll.windowListener.onscrollend = function(e)
	{ e.getSource().parent.parent.onWindowScrollEnd() }
IbsScroll.windowListener.onnewratio = function(e)
	{ e.getSource().parent.parent.checkBars() }

IbsScroll.BupListener = new EventListener()
IbsScroll.BupListener.onselect = function(e)
	{ e.getSource().parent.parent.window.scrollUp() }
IbsScroll.BupListener.ondeselect = function(e)
	{ e.getSource().parent.parent.window.cancelScroll() }

IbsScroll.BdownListener = new EventListener()
IbsScroll.BdownListener.onselect = function(e)
	{ e.getSource().parent.parent.window.scrollDown() }
IbsScroll.BdownListener.ondeselect = function(e)
	{ e.getSource().parent.parent.window.cancelScroll() }
	
IbsScroll.BleftListener = new EventListener()
IbsScroll.BleftListener.onselect = function(e)
	{ e.getSource().parent.parent.window.scrollLeft() }
IbsScroll.BleftListener.ondeselect = function(e)
	{ e.getSource().parent.parent.window.cancelScroll() }

IbsScroll.BrightListener = new EventListener()
IbsScroll.BrightListener.onselect = function(e)
	{ e.getSource().parent.parent.window.scrollRight() }
IbsScroll.BrightListener.ondeselect = function(e)
	{ e.getSource().parent.parent.window.cancelScroll() }

IbsScroll.VbarListener = new EventListener()
IbsScroll.VbarListener.onscroll = function(e) {
	var src = e.getSource()
	src.parent.parent.window.scrollTo(null,src.getRatioY())
}
IbsScroll.VbarListener.onscrollend = function(e) {
	e.getSource().parent.parent.onWindowScrollEnd()
}

IbsScroll.HbarListener = new EventListener()
IbsScroll.HbarListener.onscroll = function(e) {
	var src = e.getSource()
	src.parent.parent.window.scrollTo(src.getRatioX(),null)
}
IbsScroll.HbarListener.onscrollend = function(e) {
	e.getSource().parent.parent.onWindowScrollEnd()
}
