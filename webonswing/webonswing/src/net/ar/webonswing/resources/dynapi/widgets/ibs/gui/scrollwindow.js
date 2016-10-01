// IbsScrollWindow Object
// simple method-scrollable window where content can be written
// 2000.03.13

// DynAPI Copyright (C) 2000 Dan Steinman
// Modified for DynAPI2 by IlMaestro ( ilmaestro@cantir.com )
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

// This is some Regular Expressionism that allows to extract the body bgcolor and
// body bgImage tags of a content string, so after the content has been set the layer
// is given the appropiate bgcolor and bgImage, if any specified.
IbsScrollBgColorTagRegExp = /.*<[\s]*body.*[\s]bgcolor[\s]*=[\s]*['|"]?[\s]*(#?\w*)['|"|\s|>].*/i
IbsScrollBgImageTagRegExp = /.*<[\s]*body.*[\s]background[\s]*=[\s]*['|"]?[\s]*([\.\w]*)['|"|\s|>].*/i

function getBgTags(h) {
	var r = new Object()
	m = IbsScrollBgColorTagRegExp.exec(h)
	if(m && m[1]) r.bgColor = m[1]; else r.bgColor = 'white'
	m = IbsScrollBgImageTagRegExp.exec(h)
	if(m && m[1]) r.bgImage = m[1]; else r.bgImage = 'n'
	return r
}

IbsScrollWindow = function() {
	// Init
	this.DynLayer = DynLayer
	this.DynLayer()	

	// The content layer where stuff is written. The one that moves
	// when you scroll
	this.content = new DynLayer()

	// Initialize some variables
	this.HTML = "";
	this.padding=10
	
	this.addEventListener(IbsScrollWindow.clistener)
	this.addChild(this.content)
	this.content.addEventListener(IbsScrollWindow.scrollEvent)
	
	this.onCreate(function() {
		// If Explorer, hide overflow
		if(dynapi.ua.ie && this.css) this.css.overflow = 'hidden'
	});
	
}

// Inherit from Dynlayer
IbsScrollWindow.prototype = new DynLayer
IbsScrollWindow.prototype.getSubClass=function() { return IbsScrollWindow }
IbsScrollWindow.prototype.setPadding = function(p) { 
	this.padding = p
	this.setHTML(this.HTML,true)
}
// Overwrite setsize method
IbsScrollWindow.prototype.oldSetSize = DynLayer.prototype.setSize
IbsScrollWindow.prototype.setSize = function(w,h) {
	this.oldSetSize(w,h)
	if(this.created)
	 this.setHTML(this.HTML,true)
}
// Overwrite setHTML method
IbsScrollWindow.prototype.setHTML = function(h,fromresize) { 
	var o
	this.HTML = h
	this.content.setHTML('<table width='+this.getWidth()+' cellpadding='+this.padding+' border=0><tr><td>'+h+'</td></tr></table>')

	if(!fromresize) {
		// IF this is a new content ( the rewrite is not caused by a resize ),
		// then the content must be parsed so to find the bgcolor and bgimage properties
		// and set them into the layer
		var res = h.indexOf("body", 0)
		o = getBgTags(h.substring(res, h.indexOf(">", res)))
		this.content.setBgColor(o.bgColor)
		this.content.setBgImage(o.bgImage)
	}
	this.findDimensions()
}

IbsScrollWindow.prototype.findDimensions = function() {
	var c = this.content
	var ww = this.getWidth()
	var wh = this.getHeight()

	c.setSize(Math.max(c.getContentWidth(),ww),Math.max(c.getContentHeight(),wh))
	this.availableScrollX = c.w-ww
	this.availableScrollY = c.h-wh

	var v = this.enableVScroll = this.availableScrollY>0
	var h = this.enableHScroll = this.availableScrollX>0

	if(c.x+c.w<ww || c.y+c.h<wh) c.setPosition(-c.w+ww,-c.h+wh)
	this.invokeEvent("newratio")
}

IbsScrollWindow.prototype.scrollTo = function(rx,ry) {
	this.content.setPosition((this.enableHScroll && rx!=null)?((-this.availableScrollX)*rx):null,(this.enableVScroll && ry!=null)?((-this.availableScrollY)*ry):null)
	this.invokeEvent("directscroll")
}
IbsScrollWindow.prototype.scrollUp = function() {this.scrollSlide(null,0)}
IbsScrollWindow.prototype.scrollDown = function() {this.scrollSlide(null,-this.availableScrollY)}
IbsScrollWindow.prototype.scrollLeft = function() {this.scrollSlide(0,null)}
IbsScrollWindow.prototype.scrollRight = function() {this.scrollSlide(-this.availableScrollX,null)}
IbsScrollWindow.prototype.scrollSlide = function(x,y) {
	if (x!=null && this.enableHScroll) {
		this.invokeEvent("scrollstart")
		this.content.slideTo(x,null)
	}
	if (y!=null && this.enableVScroll) {
		this.invokeEvent("scrollstart")
		this.content.slideTo(null,y)
	}
}
IbsScrollWindow.prototype.cancelScroll = function() {
	this.content.stopSlide()
	this.invokeEvent("scrollend")
}

// Events

// Our scroll event. Notice that it relys on the slide extensions,
// so they should be included as well
IbsScrollWindow.scrollEvent = new EventListener()
IbsScrollWindow.scrollEvent.onpathrun = function(e) {
	e.getSource().parent.invokeEvent('scroll')
}
