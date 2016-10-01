// IbsWindowManager Object
// interface to a full OS-like windowing system. Well, sort of.
// 2000.03.11

// Copyright (C) 2000 Dan Steinman
// Modified for dynapi2 by IlMaestro (ilmaestro@cantir.com)
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

// A preset is a definition for window properties
LogicalWindowPreSet = function(x,y,w,h,z,i) {
	this.x = x
	this.y = y
	this.w = w
	this.h = h 
	this.z = z
	this.i = i
}

// A logical window object encapsulates a window, scroll and icon objects
// to produce a generic iconizable window
LogicalWindow = function() {
	// Window's properties and modifiers
	this.presets = null
	this.title = "--- ? ---"
	this.iconized = false
	this.HTML = ''
	this.newcontent = false
	this.created = false
	this.indoc = false
	// Reference to the window manager 
	this.manager = null

	// References to involved widgets
	this.win = null
	this.scr = null
	this.icon = null

	// Programable events. Any method can be attached to this function. When
	// the close button in the window is pressed, this method is called. If
	// it returns true, the window will close, if false, the window will not close.
	// This allows to program some validations or confirmation popups before
	// window closing
	this.onClose = null
}
LogicalWindow.prototype.PreSet = function(p) { this.presets = p }
LogicalWindow.prototype.setTitle = function(t) {
	this.title = t
	if(this.win) this.win.setTitle(t)
	if(this.icon) this.icon.setText(t)
}
LogicalWindow.prototype.closeclick = function() {
	if(!this.onClose || this.onClose()) this.setVisible(false)
}
LogicalWindow.prototype.setIconized = function(b) {
	this.iconized = b

	if(!this.created) return;

	if(!b && this.newcontent) { this.scr.setHTML(this.HTML);  this.newcontent = false }
	this.win.setVisible(!b)
	this.win.minimized = b
	
	this.icon.setVisible(b)
	
	if(this.manager) {
		if(b) { this.manager.onShow(this.icon); this.manager.onHide(this.win) }
		else { this.manager.onShow(this.win); this.manager.onHide(this.icon) }
	}
}
LogicalWindow.prototype.setVisible = function(b) {
	if(!this.created) return;

	if(b) this.setIconized(this.iconized)
	else {  this.win.setVisible(false);
		this.icon.setVisible(false);
		if(this.manager) {
			this.manager.onHide(this.icon)
			this.manager.onHide(this.win)
		}

     	     }
}
LogicalWindow.prototype.setHTML = function(h) {
	this.HTML = h
	if(!this.created) return;
	if(!this.iconized) this.scr.setHTML(h)
	else this.newcontent = true
}
LogicalWindow.prototype.assignObjects = function(win,scr,icon) {	
	this.scr = scr
	this.win = win
	this.win.setTitle(this.title) 
	this.win.setVisible(!this.iconized)
 	this.win.setZIndex(10)

	if(this.presets)
	 { this.win.setPosition(this.presets.x,this.presets.y)
	   this.win.setSize(this.presets.w,this.presets.h)
	   if(this.presets.z) this.win.setZIndex(this.presets.z) }
	else
	 { this.win.setPosition(100,100)
	   this.win.setSize(LogicalWindow.defaultW,LogicalWindow.defaultH) }
	
	this.icon = icon 
 	this.icon.setZIndex(1)
	this.icon.setVisible(this.iconized)

	this.iE = new EventListener(this)
	this.iE.onselect = function(e) { e.getTarget().setIconized(false) }
	this.icon.addEventListener(this.iE)

	this.wE = new EventListener(this)
	this.wE.onminimize = function(e) { e.getTarget().setIconized(true) }
	this.wE.onclose = function(e) { e.getTarget().closeclick() }
	this.win.addEventListener(this.wE)

	this.created = true
}
LogicalWindow.prototype.add = function(where) {	
	
	if(!this.created) return

	loading(true)
	where.addChild(this.win)
	where.addChild(this.icon)
	DragEvent.enableDragEvents(this.win)
	DragEvent.setDragBoundary(this.win)
	DragEvent.enableDragEvents(this.icon)
	DragEvent.setDragBoundary(this.icon)

	if(this.newcontent) { this.scr.window.setHTML(this.HTML);  this.newcontent = false }
	loading(false)

	this.indoc = true
}
LogicalWindow.defaultW = 400
LogicalWindow.defaultH = 300

/////////////
// This object is the window manager. Will control, create and generate appropiate
// events for each window the user needs
////////////////////////
IbsWindowManager = function(parentdoc,name) {
	if (!parentdoc) this.parent = dynapi.document
	 else this.parent = parentdoc

	this.focusListener = new EventListener(this)
	this.focusListener.onfocus = function(e) {
		e.getTarget().deFocus(e.getSource())
	}

	this.resizeListener = new EventListener(this)
	this.resizeListener.onresize = function(e) {
		e.getTarget().onResize(e.getSource())
	} 

	this.printListener = new EventListener(this)
	this.printListener.onprint = function(e) {
		Vglobals().imprimeix(e.getSource().scr.window.HTML)
	} 

	this.dragListener = new EventListener(this)
	this.dragListener.ondragmove = 
	this.dragListener.onmaximize = 
	this.dragListener.onrestore = function(e) {
		e.getTarget().onMove(e.getSource())
	} 

	this.onMove = new Function()
	this.onResize = new Function()
	this.onCreate = new Function()
	this.onShow = new Function()
	this.onHide = new Function()
}
IbsWindowManager.prototype.windows = []
IbsWindowManager.prototype.icons = []
IbsWindowManager.prototype.hash = []
IbsWindowManager.prototype.bgcolor = '#567890'
IbsWindowManager.prototype.bgtext = '#900023'

IbsWindowManager.prototype.loadTheme = function(theme,dir) {
	this[theme] = new IbsWindowTheme(dir,this.bgcolor,this.bgtext)
	this["defaultTheme"] = this[theme]
}

IbsWindowManager.prototype.add = function(Lwin,name,theme) {
	if (!theme) theme = this["defaultTheme"]

	var win = this.addWindow(theme)
	var scr = new IbsScroll("white","black")
        scr.setTheme(theme)
	scr.setBgColor("#AAAAAA")
	scr.setFgColor(theme.bgtext)
	win.addScroll(scr)

	var icon = this.addIcon(
	            new IbsIcon(Lwin.title,
	                        dynapi.getImage(("../images/examples/"+
	                                         ((Lwin.presets && Lwin.presets.i)?(Lwin.presets.i):"document.gif")
	                                        ),
	                        		32,
	                        		32)
	                       )
	                       )

	Lwin.manager = this
	Lwin.assignObjects(win,scr,icon)
	this.hash[name] = Lwin
}
IbsWindowManager.prototype.addWindow = function(theme) {
	if (!theme) theme = this["defaultTheme"]

	var win = new IbsWindow()

	this.windows[this.windows.length] = win

	win.addEventListener(this.focusListener)
	win.component.addEventListener(this.dragListener)
	win.component.addEventListener(this.resizeListener)
	win.addEventListener(this.printListener)
	win.setTheme(theme)

	return win
}
IbsWindowManager.prototype.destroyWindow = function(win) {
	win.removeEventListener(this.focusListener)
	DragEvent.disableDragEvents(win)
	
	win.deleteFromParent()
	
	this.windows = dynapi.removeFromArray(this.windows,win)
}

IbsWindowManager.prototype.removeAll = function() {
	while(this.windows.length>0) {
		this.destroyWindow(this.windows[this.windows.length-1])
	}
}
IbsWindowManager.prototype.deFocus = function(omit) {
	for (i in this.windows) {
		var win = this.windows[i]
		if (win!=omit && !win.alwaysOnTop && win.focused) {
			win.setFocused(false)
		}
	}
}
IbsWindowManager.prototype.minimizeAll = function() {
	for (i in this.windows) {
		this.windows[i].setMinimized(true)
	}
}
IbsWindowManager.prototype.setBgColor = function(c) {
	for (i in this.windows) {
		this.windows[i].setBgColor(c)
	}
}
IbsWindowManager.prototype.setFgColor = function(c) {
	for (i in this.windows) {
		this.windows[i].setFgColor(c)
	}
}
IbsWindowManager.prototype.deminimizeAll = function() {
	for (i in this.windows) {
		this.windows[i].setMinimized(false)
	}
}
IbsWindowManager.prototype.getWindow = function(name) {
	var w = this.hash[name]
	if(typeof w =="undefined" ) w = this.create(name)
	if(!w.indoc) {
		w.add(this.parent)
		this.onCreate(w.win)
		this.onCreate(w.icon)
		}
	return w
}
IbsWindowManager.prototype.load = function(u,f) {
	this.waiting = this.getWindow(f)
	networking(true)
	top.estat.location.href = u
}
IbsWindowManager.prototype.onPageLoad = function() {
	var w = this.waiting
	networking(false)
	if(!w) return

	if(w.created) w.scr.reset()
	w.setVisible(true)
	w.setHTML(top.estat.document.forms[0].t.value)

	this.waiting = null
}
IbsWindowManager.prototype.create = function(f,hidden) { 
	var l = new LogicalWindow()
	this.add(l,f)
	return l
}
IbsWindowManager.prototype.addIcon = function(ic) {
	var putrum = Math.round((dynapi.document.getHeight()-40)/75);
	var fila = 1+Math.floor((2+this.icons.length)/putrum);

	ic.setPosition(-50+90*(fila)-10,30+70*(Math.mod((2+this.icons.length),putrum)));
	ic.setSize(80,65);
	this.icons[this.icons.length] = ic;

	ic.addEventListener(this.dragListener)

	return ic
}
IbsWindowManager.prototype.write = function(t,f) {
	this.getWindow(f).setHTML(t)
}
IbsWindowManager.prototype.show = function(f) {
	this.getWindow(f).setVisible(true)
}
IbsWindowManager.prototype.hide = function(f) {
	this.getWindow(f).setVisible(false)
}