// Color Selector
// Widget allows to choose a color by using scrollbars, trying to resemble
// a tool in an Image editor
// by IlMaestro ( ilmaestro@cantir.com )
//
// DynAPI Copyright (C) 2000 Dan Steinman
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/
IbsColorSelector = function(color) {
	// Init
	this.DynLayer = DynLayer
	this.DynLayer()

	// Variables
	this.editing = null
	this.current = null
	this.bcolor = color||"white"	

	// Included widgets that will be added later
	this.scrollR = new IbsScrollBar(1)
	this.scrollG = new IbsScrollBar(1)
	this.scrollB = new IbsScrollBar(1)	
	this.bok = new IbsButton(0,0,10,10,null,"<font size=1 face=Arial>OK</font>",null)
	this.bcancel = new IbsButton(0,0,10,10,null,"<font size=1 face=Arial>NO</font>",null)
	this.before = new DynLayer()
	this.after = new DynLayer()

	// Layers and objects
	this.addChild(this.scrollR)	
	this.addChild(this.scrollG)	
	this.addChild(this.scrollB)	
	this.addChild(this.before)
	this.addChild(this.after)
	this.addChild(this.bok)	
	this.addChild(this.bcancel)	

	this.RbarListener = new EventListener(this)
	this.RbarListener.onscroll = function(e) {
		var src = e.getSource()
		var tgt = e.getTarget()
		tgt.setR(src.getRatioY())
	}
	this.scrollR.addEventListener(this.RbarListener)

	this.GbarListener = new EventListener(this)
	this.GbarListener.onscroll = function(e) {
		var src = e.getSource()
		var tgt = e.getTarget()
		tgt.setG(src.getRatioY())
	}
	this.scrollG.addEventListener(this.GbarListener)

	this.BbarListener = new EventListener(this)
	this.BbarListener.onscroll = function(e) {
		var src = e.getSource()
		var tgt = e.getTarget()
		tgt.setB(src.getRatioY())
	}
	this.scrollB.addEventListener(this.BbarListener)
	
	this.bokListener = new EventListener(this)
	this.bokListener.onselect = function(e) {
		e.getTarget().accept()
	}
	this.bok.addEventListener(this.bokListener)
	
	this.bcancelListener = new EventListener(this)
	this.bcancelListener.onselect = function(e) {
		e.getTarget().cancel()
	}
	this.bcancel.addEventListener(this.bcancelListener)

	this.setFgColor();
	this.setBarColor("#EEEEEE");
	this.setBgColor(this.bcolor);
}
IbsColorSelector.prototype = new DynLayer
IbsColorSelector.prototype.getClass = function() { return IbsColorSelector }
IbsColorSelector.prototype.accept = function() {
	this.edit(this.current.toString())
	this.invokeEvent("accept")
	}
IbsColorSelector.prototype.cancel = function() {
	this.edit(this.editing.toString())
	this.invokeEvent("cancel")
	}
IbsColorSelector.prototype.edit = function(col) {
	this.editing = new Color(col)
	this.current = new Color(col)
	this.before.setBgColor(col)
	this.after.setBgColor(col)
	this.scrollR.setRatio(null,this.current.r/255)
	this.scrollG.setRatio(null,this.current.g/255)
	this.scrollB.setRatio(null,this.current.b/255)
	}
IbsColorSelector.prototype.setR = function(c) { this.current.r = Math.round(c*255); this.update(); }
IbsColorSelector.prototype.setG = function(c) { this.current.g = Math.round(c*255); this.update(); }
IbsColorSelector.prototype.setB = function(c) { this.current.b = Math.round(c*255); this.update(); }
IbsColorSelector.prototype.update = function() {
	this.currentColor = this.current.toString()
	this.after.setBgColor(this.currentColor)
	this.invokeEvent("change");
	}
IbsColorSelector.prototype.IbsColorSelectorOldSetSize = IbsColorSelector.prototype.setSize
IbsColorSelector.prototype.setSize = function(w,h) {
	var inc = Math.round(w/8)
	var m = Math.round(w/20)

        this.IbsColorSelectorOldSetSize(w,h)

        this.scrollR.setSize(inc-m,h-2*m-2)
        this.scrollG.setSize(inc-m,h-2*m-2)
        this.scrollB.setSize(inc-m,h-2*m-2)
        
	this.scrollR.setPosition(m,m)
	this.scrollG.setPosition(m+inc,m)
	this.scrollB.setPosition(m+2*inc,m)
	
	this.before.setPosition(w/2-2,m)
	this.before.setSize(w/2-m,(h-2*m)/3)
	this.after.setPosition(w/2-2,this.before.getY()+this.before.getHeight())
	this.after.setSize(w/2-m,(h-2*m)/3-1)
	
	this.bok.setPosition(w/2-2,this.after.getHeight()+this.after.getY()+m)
	this.bcancel.setPosition(3*w/4-3,this.after.getHeight()+this.after.getY()+m)
	this.bok.setSize(2*inc-m,h-2*m-this.bok.getY())
	this.bcancel.setSize(2*inc-m,h-2*m-this.bcancel.getY())
	}
IbsColorSelector.prototype.setBarColor = function(c) {
	this.scrollR.setBgColor(c)
	this.scrollG.setBgColor(c)
	this.scrollB.setBgColor(c)
	this.bok.setBgColor(c)
	this.bcancel.setBgColor(c)
	}
IbsColorSelector.prototype.setFgColor = function() {
	this.scrollR.setFgColor("#FF0000")
	this.scrollG.setFgColor("#00FF00")
	this.scrollB.setFgColor("#0000FF")
	}
