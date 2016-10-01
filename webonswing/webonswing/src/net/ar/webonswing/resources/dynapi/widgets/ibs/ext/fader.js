// colorfader.js
// allows to set two colors and have a DynLayer set its bgColor to fade from one to another
// 2000.10.01

// by IlMaestro ( ilmaestro@cantir.com)

// DynAPI Copyright (C) 2000 Dan Steinman
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/
///////////////

// Extend DynLayer
DynLayer.prototype.setFadeCols = function(f,t) { this.fader = new Fader(f,t) }
DynLayer.prototype.setFade = function(percent) { if(this.fader) this.setBgColor(this.fader.getCol(percent)) }
DynLayer.prototype.glow = function(inc,time,loop) {
	this.glowObj = new Object();
	this.glowObj.status = 0;
	this.glowObj.limit = false;
	this.glowObj.active = true;
	this.glowObj.inc = inc>1?inc/100:inc;
	this.glowObj.loop = loop!=null?loop:true;
	this.invokeEvent("glowstart");
	this.glowObj.interval = setInterval(this+".doGlow()",time);
}
DynLayer.prototype.stopGlow = function() { this.invokeEvent("glowend"); this.glowObj.active = false}
DynLayer.prototype.doGlow = function() {
	var o = this.glowObj;
	if(!o.active) { clearInterval(o.interval); return };
	o.status += o.inc;
	if(o.status<0) o.status = 0;
	if(o.status>1) o.status = 1;
	this.setFade(o.status);
	this.invokeEvent("glow");
	if(o.status==0 || o.status==1 || (this.limit!=false && ( (o.inc>0 && o.status>=this.limit) || (o.inc<0 && o.status<=this.limit) ) )) { o.inc = -o.inc; if(!o.loop) { this.invokeEvent("glowend"); o.active = false } }
}
DynLayer.prototype.glowTo = function(where,inc,time) {
	var o = this.glowObj;
	if(o.status>where) inc = -inc;
	o.limit = where;
	this.glow(inc,time,false);
}
DynLayer.prototype.fader = null;

// Create the fader obj
Fader = function(from,to) {
	this.from = (from.constructor==Color?from:new Color(from));
	this.to = (to.constructor==Color?to:new Color(to));
	this.incs = { r:this.to.r-this.from.r , g:this.to.g-this.from.g , b:this.to.b-this.from.b };
}
Fader.prototype.getCol = function(percent) {
	var i = this.incs;
	var f = this.from;
	return (new Color(f.r+Math.round(percent*i.r),f.g+Math.round(percent*i.g),f.b+Math.round(percent*i.b))).toString();
}

// Overload Math object with a module method
Math.mod = function(a,b)
 { return a%b; }

// Color: constructor. if only one parameter is received, it assumes it is
// and HTML color ("#aa98ea"). Otherwise ( 3 parameters ) they are suposed to be
// valid r,g,b values from 0 to 255
Color = function(r,g,b) {
        if(arguments.length==1) { 
                if (r.charAt(0)!='#') r='#'+r;
                g = parseInt(r.substring(3,5),16);
                b = parseInt(r.substring(5,7),16);
                r = parseInt(r.substring(1,3),16);
        }
        this.r = r;
        this.g = g;
        this.b = b;
}

// Used to export the color into HTML format
Color.prototype.toString = function() {
        var n = Math.round(this.b); 
        n += Math.round(this.g) << 8;
        n += Math.round(this.r) << 16;  
        return DectoHex(n);
}

// Auxiliar methods. Place them anywhere else, if you prefer
////////////////////////////////////////////////////////////

// Converts a numerical value into its HEX equivalent, so to export our
// color to HTML format. I think there must be a proper way, using JS core, to
// do such conversions
function DectoHex (num) {
        var i = 0; var j = 20;
        var str = "#";
        while(j >= 0) {
                i = Math.mod((num >> j),16);
                if(i >= 10) {
                        if(i == 10) str += "A";
                        else if(i == 11) str += "B";
                        else if(i == 12) str += "C";
                        else if(i == 13) str += "D";
                        else if(i == 14) str += "E";
                        else str += "F";
                } else str += i;
                j -= 4;
        }       
        return str;
}
