// IbsButtonImage Object
// a generic image toggle button widget
// 2000.03.11

// Copyright (C) 2000 Dan Steinman
// Slighly modified by IlMaestro ( ilmaestro@cantir.com )
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

IbsButtonImage = function () {
	// Init
	this.DynLayer=DynLayer
	this.DynLayer()

	// The events are the only needed
	this.addEventListener(IbsButtonImage.events)
}
// Inherit from Dynlayer
IbsButtonImage.prototype = new DynLayer
IbsButtonImage.prototype.getSubClass=function() { return IbsButtonImage }
IbsButtonImage.prototype.setImages = function(defaultImage,defaultRoll,selectedImage,selectedRoll) {
	if (arguments.length==4) this.checkbox = true
	this.defaultImage = defaultImage
	this.defaultRoll = defaultRoll
	this.selectedImage = selectedImage
	this.selectedRoll = selectedRoll	
	this.setHTML('<img name="'+this.id+'Image" src="'+this.defaultImage[0]+'" width='+this.defaultImage[1]+' height='+this.defaultImage[2]+'>')
	this.setSize(this.defaultImage[1],this.defaultImage[2])
}
IbsButtonImage.prototype.setSelected = function(b) {
	if ((this.selected=b)) {
		this.change(this.selectedImage)
		this.invokeEvent("select")
	}
	else {
		this.change(this.defaultImage)
		this.invokeEvent("deselect")
	}
}
IbsButtonImage.prototype.change = function(img) {
	if (img) this.doc.images[this.id+"Image"].src = img[0]
}
IbsButtonImage.prototype.checkbox = false

// Events
IbsButtonImage.events = new EventListener()
IbsButtonImage.events.onmousedown = function (e) {
	var o = e.getSource()
	if(!o.checkbox) o.setSelected(true)
	e.setBubble(false)
}
IbsButtonImage.events.onmouseover = function (e) {
	var o = e.getSource()
	if(o.checkbox && o.selected) o.change(o.selectedRoll)
	else o.change(o.defaultRoll)
	e.setBubble(false)
}
IbsButtonImage.events.onmouseout = function (e) {
	var o = e.getSource()
	o.change(o.defaultImage)
	e.setBubble(false)
}
IbsButtonImage.events.onmouseup = function (e) {
	var o = e.getSource()
	if(o.checkbox) o.setSelected(!o.selected)
	else o.setSelected(false)
	e.setBubble(false)
}
