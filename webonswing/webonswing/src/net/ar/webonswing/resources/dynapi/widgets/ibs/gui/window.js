// IbsWindow Object
// a widget-object that creates a draggable window
// 2000.11.28

// Copyright (C) 2000 Dan Steinman
// Edited by IlMaestro ( ilmaestro@cantir.com )

// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

// The theme object
IbsWindowTheme = function(dir,bgcolor,bgtext) {
	var i = this.images = new Object()
	i['icon'] = [dir+'icon.gif',20,20]
	i['grip'] = [dir+'title-grip.gif',0,21]
	i['max0'] = [dir+'max0.gif',20,20]
	i['max1'] = [dir+'max1.gif',20,20]
	i['restore0'] = [dir+'restore0.gif',20,20]
	i['restore1'] = [dir+'restore1.gif',20,20]
	i['min0'] = [dir+'min0.gif',20,20]
	i['min1'] = [dir+'min1.gif',20,20]
	i['close0'] = [dir+'close0.gif',20,20]
	i['close1'] = [dir+'close1.gif',20,20]
	this.border = 3
	this.minW = 90
	this.minH = 25+this.border
	this.dir = dir
	this.bgcolor = bgcolor
	this.bgtext = bgtext
}
// The window object
IbsWindow = function(title) {
	// Init
	this.DynLayer = IbsBoldLayer
	this.DynLayer()
	this.id = "IbsWindow"+(IbsWindow.Count++)

	// Variables
	this.title = title

	// Layers
	this.titleBar = new IbsBoldLayer()
	this.buttonIcon = new DynLayer()
	this.titleText = new DynLayer()
	this.titleCover = new DynLayer()
	this.cornerResize = new DynLayer('',0,0,10,10)
	this.buttonClose = new IbsButtonImage()
	this.buttonMax = new IbsButtonImage()
	this.buttonMin = new IbsButtonImage()
	this.content = new DynLayer()
	
	this.onCreate(function() {
		var ob=this;
		// Objects
		ob.addChild(ob.titleBar)
		ob.titleBar.addChild(ob.buttonIcon)
		ob.titleBar.addChild(ob.titleText)
		ob.titleBar.addChild(ob.titleCover)
		ob.addChild(ob.cornerResize)
		ob.addChild(ob.buttonClose)
		ob.buttonMax.checkbox = true
		ob.addChild(ob.buttonMax)
		ob.addChild(ob.buttonMin)
		ob.addChild(ob.content)
		ob.content.setBgColor('#ffffff')
		
		// Events
		ob.buttonPrintEvent = new EventListener(ob)
			ob.buttonPrintEvent.onmousedown = function(e) {
				e.preventBubble()
				}
			ob.buttonPrintEvent.onmouseup = function(e) {
				ob.invokeEvent("icon")
				e.preventBubble()
			}
			ob.buttonIcon.addEventListener(ob.buttonPrintEvent)

		ob.buttonCloseEvent = new EventListener(ob)
			ob.buttonCloseEvent.onselect = function(e) {
				ob.invokeEvent("close")
			}
			ob.buttonClose.addEventListener(ob.buttonCloseEvent)

		ob.buttonMaxEvents = new EventListener(ob)
			ob.buttonMaxEvents.onselect = function(e) {
				if (!ob.maximized) ob.setMaximized(true)
			}
			ob.buttonMaxEvents.ondeselect = function(e) {
				if (ob.maximized) ob.setMaximized(false)
			}
			ob.buttonMax.addEventListener(ob.buttonMaxEvents)

		ob.buttonMinEvent = new EventListener(ob)
			ob.buttonMinEvent.onmousedown = function(e) {
				ob.setMinimized(true)
				e.preventBubble()
			}
			ob.buttonMin.addEventListener(ob.buttonMinEvent)

		ob.contentEvent = new EventListener(ob)
			ob.contentEvent.onmousedown = function(e) {
				ob.setFocused(true)
				ob.invokeEvent("focus")
				e.preventBubble()
			}
			ob.content.addEventListener(ob.contentEvent)

		ob.focusEvent = new EventListener(ob)
			ob.focusEvent.onmousedown = function(e) {
				ob.setFocused(true)
				ob.invokeEvent("focus")
			}
			ob.addEventListener(ob.focusEvent)
		
		ob.shadeEvent = new EventListener(ob)
			ob.shadeEvent.ondblclick = function(e) {
				var o = ob
				o.setShaded(!o.shaded)
				e.preventBubble()
			}
			ob.titleBar.addEventListener(ob.shadeEvent)
		
		ob.dragResizeEvent = new EventListener(ob)
			ob.dragResizeEvent.ondragmove = function(e) {
				var o = ob
				var w = o.cornerResize.getPageX()+10-o.getPageX()
				var h = o.cornerResize.getPageY()+10-o.getPageY()
				if (w<o.minW) w = o.minW
				if (h<o.minH) h = o.minH
				o.maximized = false
				o.shaded = false
				o.resizeOutter(w,h)
			}
			ob.dragResizeEvent.ondragend = function(e) {
				var w = ob
				w.setSize(w.w,w.h)
				w.content.setVisible(true)
			}
			ob.dragResizeEvent.ondragstart = function(e) {
				ob.content.setVisible(false)
			}
			ob.cornerResize.addEventListener(ob.dragResizeEvent)
		ob.setResizable(ob.resizable)
		ob.setTitle(ob.title)
		}
	);
}

// Inherit from
IbsWindow.prototype = new IbsBoldLayer
IbsWindow.prototype.getClass = function() { return IbsWindow }


IbsWindow.prototype.themeSet = false
IbsWindow.prototype.resizable = true
IbsWindow.prototype.addScroll = function(s) {
	this.content.addChild(s)
	this.scr = s
	this.setSize(this.w,this.h)
	}
IbsWindow.prototype.setFgColor = function(c) {
	this.tcol = new Color(c)
	this.tcolselect = new Color(c)
	this.tcolselect.light(20)

	if (this.focused) this.titleBar.setBgColor(this.tcolselect.toString())
	else this.titleBar.setBgColor(this.tcol.toString())
	
	if(this.scr) this.scr.setFgColor(c)
}
IbsWindow.prototype.setTheme = function(theme) {
	this.theme = theme || new IbsWindowTheme("./","blue","cyan")
	this.images = this.theme.images
	this.style = this.theme.style
	this.minW = this.theme.minW
	this.minH = this.theme.minH
	var b = this.borderwidth = this.theme.border
	
	var titleH = this.images['icon'][2]+4
	var cx = b+1
	var cy = b+titleH
	var cw = this.w-2*b-2
	var ch = this.h-cy-3*b+1

	this.titleBar.setPosition(cx,b+1)

	this.buttonIcon.setSize(this.images['icon'][1],this.images['icon'][2])
	this.buttonIcon.setHTML('<img width=20 src="'+this.images['icon'][0]+'">')
	this.titleText.setPosition(this.images['icon'][1],0)
	this.titleCover.setPosition(this.images['icon'][1],0)

	this.buttonClose.setImages(this.images['close0'],this.images['close1'])
	this.buttonMax.setImages(this.images['max0'],this.images['max1'],this.images['restore0'],this.images['restore1'])
	this.buttonMin.setImages(this.images['min0'],this.images['min1'])
		
	this.content.setPosition(cx,cy)
	this.content.setSize(cw,ch)

	this.setBgColor(this.theme.bgcolor)
	this.setFgColor(this.theme.bgtext)

	this.themeSet = true
}
IbsWindow.prototype.resizeOutter = function(w,h) {
	if (!this.themeSet) return

	this.IbsWindowOldSetSize(w,h)

	var b = this.borderwidth

	var titleH = this.images['icon'][2]
	var cx = b
	var cy = b+titleH
	var cw = this.w-2*b-2
	var ch = this.h-cy-3*b
	
	this.content.setSize(cw,ch)
	
	this.titleBar.setSize(cw,titleH)

	this.titleText.setSize(Math.min(this.titleText.getContentWidth(),cw-this.images['icon'][1]-this.images['close0'][1]-this.images['max0'][1]-this.images['min0'][1]),titleH)
	this.titleCover.setSize(Math.min(this.titleText.getContentWidth(),cw-this.images['icon'][1]-this.images['close0'][1]-this.images['max0'][1]-this.images['min0'][1]),titleH)
	
	this.buttonClose.setPosition(this.w-b-this.images['close0'][1]-4,b)
	this.buttonMax.setPosition(this.w-b-this.images['close0'][1]-this.images['max0'][1]-4,b)
	this.buttonMin.setPosition(this.w-b-this.images['close0'][1]-this.images['max0'][1]-this.images['min0'][1]-4,b)
	
	this.cornerResize.setPosition(this.w-10,this.h-10)
}
IbsWindow.prototype.setInnerSize = function(w,h) {
	this.resizeOutter(w+this.images['l'][1]+this.images['r'][1],h+this.images['t'][1]+this.images['b'][1])
	this.onResize()
}
IbsWindow.prototype.IbsWindowOldSetSize = IbsWindow.prototype.setSize
IbsWindow.prototype.setSize = function(w,h) {
	this.resizeOutter(w,h)
	if(this.scr) this.scr.setSize(this.content.getWidth(),this.content.getHeight())
	this.invokeEvent("resize")
	this.invokeEvent("resizenodrag")
}
IbsWindow.prototype.setMinimized = function(b) {
	if (this.minimized=b) this.invokeEvent("minimize")
	else this.invokeEvent("restore")
}
IbsWindow.prototype.setMaximized = function(b) {
	if (this.maximized=b) {
		this.origX = this.x
		this.origY = this.y
		this.origW = this.w
		this.origH = this.h
		this.setPageX(0)
		this.setPageY(0)
		if (!this.buttonMax.selected) this.buttonMax.setSelected(true)
		this.setSize(dynapi.document.getWidth(),dynapi.document.getHeight())
		this.invokeEvent("maximize")
		if(this.scr) this.scr.setSize(this.content.getWidth(),this.content.getHeight())
	} else if (!this.minimized) {
		this.setPosition(this.origX,this.origY)
		this.setSize(this.origW,this.origH)
		this.maximized = false
		this.invokeEvent("restore")
		if(this.scr) this.scr.setSize(this.content.getWidth(),this.content.getHeight())
	}
}
IbsWindow.prototype.setShaded = function(b) {
	if (this.shaded=b) {
		this.origH = this.h
		this.resizeOutter(this.w,this.minH)
	} else this.resizeOutter(this.w,this.origH)
}
IbsWindow.prototype.getInnerWidth = function() {return this.content.w}
IbsWindow.prototype.getInnerHeight = function() {return this.content.h}
IbsWindow.prototype.setTitle = function (title) {
	this.title = title||''
	if (!this.themeSet) return
	this.titleText.setHTML('<nobr><font face=Arial color=black><b>&nbsp;'+this.title+'&nbsp;</b></font></nobr>')
	this.titleText.setSize(Math.min(this.titleText.getContentWidth(),this.w-(2*this.borderwidth)-this.images['min0'][1]-this.images['min0'][1]-this.images['max0'][1]-this.images['close0'][1]),this.images['icon'][2]+4)
	this.titleCover.setSize(Math.min(this.titleText.getContentWidth(),this.w-(2*this.borderwidth)-this.images['min0'][1]-this.images['min0'][1]-this.images['max0'][1]-this.images['close0'][1]),this.images['icon'][2]+4)
	this.titleText.setPosition(null,(this.images['icon'][2]-this.titleText.getContentHeight())/2)
	this.titleCover.setPosition(null,(this.images['icon'][2]-this.titleText.getContentHeight())/2)
}
IbsWindow.prototype.getTitle = function(title) {return this.title}
IbsWindow.prototype.setResizable = function (b) {
	if (this.resizable=b) DragEvent.enableDragEvents(this.cornerResize)
	else DragEvent.disableDragEvents(this.cornerResize)
}
IbsWindow.prototype.getResizable = function() {return this.resizable}
IbsWindow.prototype.setAlwaysOnTop = function (b) {
	this.alwaysOnTop = b
}
IbsWindow.prototype.setFocused = function (b) {
	if (this.focused=b) this.titleBar.setBgColor(this.tcolselect.toString())
	else this.titleBar.setBgColor(this.tcol.toString())
}
IbsWindow.prototype.destroyWindow = function() {
	this.deleteFromParent()
}
IbsWindow.prototype.setHTML = function(h) {
	if(this.scr) this.scr.setHTML(h)
	else this.content.setHTML(h)
}
