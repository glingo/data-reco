// Button Object
// generic clicable button with shadow effect. Extends IbsBoldLayer
// by IlMaestro ( ilmaestro@cantir.com ) from Pascal Bestebroer's code

// dynapi Copyright (C) 2000 Dan Steinman
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

IbsButton = function(x,y,w,h,image,caption,hint) {
	// Init
	this.IbsBoldLayer = IbsBoldLayer
	this.IbsBoldLayer()

	this.myCanvas = new DynLayer()
	// Inicial
	this.setSize(w||16,h||16)
	this.setPosition(x||0,y||0)

	// Variables
	this.caption=caption||''
	this.hint=hint||''
	
	this.cover=this.icon=null
	if (image) this.icon=dynapi.getImage(image,w,h)
	else this.cover = new DynLayer()

	// thisj Layer
	this.addChild(this.myCanvas)

	// Events
	this.addEventListener(IbsButton.action)

	if(this.cover) this.addChild(this.cover)

	if (this.icon) this.myCanvas.setHTML('<center><img src="'+this.icon.src+'" width="'+(this.myCanvas.getWidth()-0)+'" height="'+(this.myCanvas.getHeight()-0)+'" alt="'+this.hint+'"></center>')
	else this.myCanvas.setHTML('<center>'+this.caption+'</center>')
	this.myCanvas.setPosition(2,2)

}
// Inherit from BoldLayer, taking advantage of its capabilities
IbsButton.prototype = new IbsBoldLayer
IbsButton.prototype.getSubClass=function() { return IbsButton }
// Overwrite setSize
IbsButton.prototype.ButtonOldSetSize = IbsButton.prototype.setSize
IbsButton.prototype.setSize=function(w,h) {
	if(!w) return
	this.ButtonOldSetSize(w,h)
	if(this.myCanvas) this.myCanvas.setSize(w-4,h-4)
	if(!this.icon) this.myCanvas.setHTML('<center>'+this.caption+'</center>')
	if(this.cover) this.cover.setSize(w-4,h-4)
}
// One listener for all buttons. Optimize, optimize, optimize
IbsButton.action = new EventListener(this)
IbsButton.action.onmousedown = function (e) {
	var o = e.getSource()
	o.setShadowEffect(BOLDLAYER_INVERSE)
	o.myCanvas.setPosition(3,3)
	e.setBubble(false)
}
IbsButton.action.onmouseup = function (e) {
	var o = e.getSource()
	e.setBubble(false)
	o.setShadowEffect(BOLDLAYER_NORMAL)
	o.myCanvas.setPosition(2,2)
	o.invokeEvent("select")
}
IbsButton.action.onmouseout = function (e) {
	var o = e.getSource()
	o.myCanvas.setPosition(2,2)
	o.setShadowEffect(BOLDLAYER_NORMAL)
	e.setBubble(false)
}
