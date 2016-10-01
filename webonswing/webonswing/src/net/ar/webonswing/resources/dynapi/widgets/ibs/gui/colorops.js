// Color
// heavy overloaded and unoptimized yet useful color object.
// Allows addition, substraction, rgs + hls ops ( not many ).
// 2000.04.17

// by IlMaestro ( ilmaestro@cantir.com, jordim@eia.udg.es ) & Nano ( coll@eia.udg.es )
// some code was taken from and old Netscape color selection demo

// DynAPI Copyright (C) 2000 Dan Steinman
// Redistributable under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/
///////////////

// Overload Math object with a module method
Math.mod = function(a,b)
 { return a%b; }

// Constructor. if only one parameter is received, it assumes it is
// and HTML color ("#aa98ea"). Otherwise ( 3 parameters ) they are suposed to be
// valid r,g,b values from 0 to 255
Color = function(r,g,b) {
        if(arguments.length==1) { 
                if (r.charAt(0)!='#') r='#'+r           
                g = parseInt(r.substring(3,5),16)
                b = parseInt(r.substring(5,7),16)
                r = parseInt(r.substring(1,3),16)
        }

        this.r = r
        this.g = g
        this.b = b

        // Initialize HLS values
        this.syncHLS()

}

// Used to export the color into HTML format
Color.prototype.toString = function() {
        var n = Math.round(this.b); 
        n += Math.round(this.g) << 8;
        n += Math.round(this.r) << 16;  
        return DectoHex(n);
}

// Internal method: synchronizes RGB values to hls after a luminance operation has been
// performed
Color.prototype.syncRGB = function() {
        var p1,p2;
        if (this.l<=0.5) p2=this.l*(1+this.s);
        else p2=this.l+this.s-(this.l*this.s);
        p1=2*this.l-p2;
        if (this.s==0) {
                this.r=this.l; 
                this.g=this.l;
                this.b=this.l;
        } else {
                this.r=RGB(p1,p2,this.h+120);
                this.g=RGB(p1,p2,this.h);
                this.b=RGB(p1,p2,this.h-120);
        }
        this.r *= 255;
        this.g *= 255;
        this.b *= 255;
        this.r=Math.floor(this.r);
        this.g=Math.floor(this.g);
        this.b=Math.floor(this.b);
        if (this.r>255) this.r=255;
        if (this.g>255) this.g=255;
        if (this.b>255) this.b=255;
}

// Internal method: synchronizes HLS values to rgb after a color operation has taken place
Color.prototype.syncHLS = function() {
        var R=this.r/255;
        var G=this.g/255;
        var B=this.b/255;
        var max, min,diff,r_dist,g_dist,b_dist;
        max = MAX(R,G,B);
        min = MIN(R,G,B);
        diff = max-min;
        this.l = (max+min)/2;
        if (diff==0) {
                this.h = 0;
                this.s = 0;
        } else {
                if (this.l<0.5) this.s = diff/(max+min);
                else this.s = diff/(2-max-min);      
                r_dist = (max-R)/diff;
                g_dist = (max-G)/diff;
                b_dist = (max-B)/diff;
                if (R == max)  this.h = b_dist-g_dist;
                else if (G == max) this.h = 2+r_dist-b_dist;
                else if (B == max) this.h = 4+g_dist-r_dist;
                this.h *= 60;
                if (this.h<0) this.h += 360;
                if (this.h>=360) this.h -= 360;
                }
}

// Adds another color to this one
Color.prototype.add = function(c) {
        this.r += c.r
        this.g += c.g
        this.b += c.b
        this.syncHLS()
}


// Substracts another color from this one
Color.prototype.subs = function(c) {
        this.r -= c.r
        this.g -= c.g
        this.b -= c.b
        this.syncHLS()
}

// Makes color N% darker
Color.prototype.dark = function(n) {
        this.l -= ((this.l)*n/100)
        this.syncRGB()
}

// Makes color N% lighter
Color.prototype.light = function(n) {
        this.l += ((1-this.l)*n/100)
        this.syncRGB()
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

// Auxiliar method
function MIN() {
        var min = 255;
        for(var i = 0; i < arguments.length; i++)
        if(arguments[i] < min)
         min = arguments[i];
        return min;
}

// Auxiliar method
function MAX() {
        var max = 0;
        for(var i = 0; i < arguments.length; i++)
        if(arguments[i] > max)
         max = arguments[i];
        return max;
}

// Auxiliar method
function RGB(q1,q2,hue) {
        if (hue>360) hue=hue-360;
        if (hue<0) hue=hue+360;
        if (hue<60) return (q1+(q2-q1)*hue/60);
        else if (hue<180) return(q2);
        else if (hue<240) return(q1+(q2-q1)*(240-hue)/60);
        else return(q1);
}