// BoldLayer
// a layer simulating a pseudo-3d effect by using shadows and highlights. In order
// to do so the colorops.js file must be included
// 2001.03.28

// by IlMaestro ( ilmaestro@cantir.com )

// DynAPI Copyright (C) 2000 Dan Steinman
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

// These constants are used to set the shadow's direction
var BOLDLAYER_TOP_LEFT = 1
var BOLDLAYER_TOP_RIGHT = 2
var BOLDLAYER_BOTTOM_LEFT = 3
var BOLDLAYER_BOTTOM_RIGHT = 4

// These constants are used to set the layer's shadow
var BOLDLAYER_NORMAL = 1        
var BOLDLAYER_INVERSE = 2

IbsBoldLayer = function(border) {
	// Init
	this.DynLayer = DynLayer
	this.DynLayer()

        // Layers that simulate the border effect
        this.border = border==null ? 1 : border

        // To simulate most accurate shadow effect a series of linear layers
        // are created. This makes the object bigger in code and much more
        // processor demanding. A user interested only in 1px wide border
        // might as well remove all the array stuff and have only four margin
        // layers
        this.ibsBLup = new Array()
        this.ibsBLdown = new Array()
        this.ibsBLleft = new Array()
        this.ibsBLright = new Array()

        for(var i=0;i<this.border;i++) {
                this.ibsBLup[i] = new DynLayer()
                this.ibsBLdown[i] = new DynLayer()
                this.ibsBLleft[i] = new DynLayer()
                this.ibsBLright[i] = new DynLayer()
        }

        // Layer-shadow info
        this.shadowDirection = BOLDLAYER_TOP_LEFT
	this.shadowEffect = BOLDLAYER_NORMAL

        for(var i=0;i<this.border;i++) {
                this.addChild(this.ibsBLup[i])
                this.addChild(this.ibsBLdown[i])
                this.addChild(this.ibsBLleft[i])
                this.addChild(this.ibsBLright[i])
        }
        
}
// Inherit from Dynlayer
IbsBoldLayer.prototype = new DynLayer
IbsBoldLayer.prototype.getSubClass=function() { return IbsBoldLayer }
// Overwrites setBgColor to create the shadow colors, store them and
// apply to the layer
IbsBoldLayer.prototype.IbsBoldLayerOldSetBgColor = IbsBoldLayer.prototype.setBgColor
IbsBoldLayer.prototype.setBgColor = function(c) {
	if(!c) return;

        this.col = new Color(c)
        this.ncol = new Color(c)
        this.darker = new Color(c)
        this.ndarker = new Color(c)
        this.lighter = new Color(c)
        this.nlighter = new Color(c)
        this.darker.dark(20)
        this.ndarker.dark(30)
        this.lighter.light(20)
        this.nlighter.light(30)
        this.ncol.light(10)
        this.repaint()
}

// Repaints layer using current shadow config and colors
IbsBoldLayer.prototype.repaint = function() {

	// If the setBgColor or setSize are called before layer creation,
	// the border layers won't exist so the repaint doesn't have to be
	// done
	if(!this.col || !this.ibsBLup) return;

        var c,l,d,nl,nd
        var sd = this.shadowDirection
        var i

        // This is so ugly. I know. It could have been done using
        // shorter ternary operators ( ... ? ... : ... ) but I will
        // eventually add more shadow modes, so I'll stick with this
        // 'if' 'then' syntax for now
        if(this.shadowEffect == BOLDLAYER_NORMAL) {
                c = this.col.toString()
                l = this.lighter.toString()
                nl = this.nlighter.toString()
                d = this.darker.toString()
                nd = this.ndarker.toString()
        }
        else {
                c = this.ndarker.toString()
                l = this.darker.toString()
                d = this.nlighter.toString()
                nl = this.darker.toString()
                nd = this.lighter.toString()
        }

        // Sets the background color
        this.IbsBoldLayerOldSetBgColor(c)

        // Now do the borders
        if(sd == BOLDLAYER_TOP_LEFT || sd == BOLDLAYER_TOP_RIGHT) {
                for(i=0;i<this.ibsBLup.length;i++) {
                        this.ibsBLup[i].setBgColor(nl)
                        this.ibsBLdown[i].setBgColor(nd)
                }
        }
        else {  for(i=0;i<this.ibsBLup.length;i++) {
                        this.ibsBLup[i].setBgColor(nd)
                        this.ibsBLdown[i].setBgColor(nl)
                }
        }

        if(sd == BOLDLAYER_TOP_LEFT || sd == BOLDLAYER_BOTTOM_LEFT) {
                for(i=0;i<this.ibsBLup.length;i++) {
                        this.ibsBLleft[i].setBgColor(l)
                        this.ibsBLright[i].setBgColor(d)
                }
        }
        else {  for(i=0;i<this.ibsBLup.length;i++) {
                        this.ibsBLleft[i].setBgColor(d)
                        this.ibsBLright[i].setBgColor(l)
                }
        }
}

// Overwrite setSize so appart from our main layer, the borders are moved and resized
// accordingly
IbsBoldLayer.prototype.BoldLayerOldSetSize = IbsBoldLayer.prototype.setSize
IbsBoldLayer.prototype.setSize = function(w,h) {

        this.BoldLayerOldSetSize(w,h)

        for(i=0;i<this.ibsBLup.length;i++) {
                this.ibsBLup[i].setSize(w-2*i,1,false);this.ibsBLup[i].setPosition(i,i)
                this.ibsBLleft[i].setSize(1,h-2*i,false); this.ibsBLleft[i].setPosition(i,i)
                this.ibsBLright[i].setSize(1,h-1-2*i,false); this.ibsBLright[i].setPosition(w-1-i,1+i)
                this.ibsBLdown[i].setSize(w-1-2*i,1+i,false); this.ibsBLdown[i].setPosition(1+i,h-1-i)
        }
}

// Sets the shadow direction. The available values are those defined at the top
// of the file
IbsBoldLayer.prototype.setShadowDirection = function(v) {
        this.shadowDirection = v
        this.repaint()
}

// Sets the shadow effect type. See top definitions for available values
IbsBoldLayer.prototype.setShadowEffect = function(v) {
        this.shadowEffect = v
        this.repaint()
}
