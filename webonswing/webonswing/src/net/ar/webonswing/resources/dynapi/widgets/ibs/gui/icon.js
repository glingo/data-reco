// Icon Object
// This object simulates a typical desktop icon. Launches an event ondoubleclick
// by IlMaestro ( ilmaestro@cantir.com )

// dynapi Copyright (C) 2000 Dan Steinman
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

// Very simple and primitive... :(
IbsIcon = function(text,img,imgw,imgh) {	
	// Init
	this.DynLayer = DynLayer
	this.DynLayer()

	// Layers
	this.textlyr = new DynLayer()
	this.cover = new DynLayer()
	this.imglyr = new DynLayer()

	// Can contain an image
	this.img = img?img:null
	this.text = text
	this.imgw = imgw
	this.imgh = imgh

	// Events
	this.addEventListener(IbsIcon.selectEvent)
	
	this.addChild(this.textlyr)
	this.addChild(this.cover)
	this.textlyr.setPosition(0,this.imgh)
	this.cover.setPosition(0,0)
	if(this.img) {
		this.addChild(this.imglyr)
		this.imglyr.setSize(this.imgw,this.imgh)
		this.imglyr.setHTML('<img src="'+this.img+'">')
	}
}
// Inherit from...
IbsIcon.prototype = new DynLayer
IbsIcon.prototype.text = ''
IbsIcon.prototype.align = 'center'
IbsIcon.prototype.setText = function(text) {
	this.text = text
	this.textFull = '<table width='+this.width+'><td align="'+this.align+'">'+text+'</td></table>'
	this.textlyr.setHTML(this.textFull)
}
// Overwrite setSize
IbsIcon.prototype.IbsIconOldSetSize = IbsIcon.prototype.setSize
IbsIcon.prototype.setSize = function(w,h) {
	this.IbsIconOldSetSize(w,h)
	this.cover.setSize(w,h)
	this.width = w
	this.height = h
	if(this.img)
	 { this.imglyr.setPosition((w-this.imgw)/2,0)
	   this.textlyr.setPosition(0,this.imgh)
	   this.textlyr.setSize(w,h-this.imgh) }
	else { 
	   this.textlyr.setPosition(0,h/2)
	   this.textlyr.setSize(w,h/2)
	}  
	this.setText(this.text)
}
IbsIcon.prototype.getText = function() {return this.text}

IbsIcon.selectEvent = new EventListener(this)
IbsIcon.selectEvent.ondblclick = function(e) {
	e.getSource().invokeEvent("select")
	e.setBubble(false)
}
IbsIcon.selectEvent.onCreate (function() {
	var t = this;
	t.setText(t.text)
});
