/* Editable Label class for text grid

Created 7-12-2001 by Matthew Bull for quantigma limited

   Requirements:
    dynapi.api [dynlayer, dyndocument, events]
    dynapi.gui [label]
*/
function EditLabel(text)    {
    this.Label=Label;
    this.Label(text);
    this.value = text;
    this.setSelectable(false);
    this.deselectable = true;
    this.isMouseOver = false;
    this.inEdit = false;
    this.clearOnEdit=false;
    this.itemStyle = {};
    this.labelCache='';
    var labelevents = new EventListener();
    labelevents.onmouseup = function (e) {
        e.bubble=false;
        var o = e.getSource();
        if(!o.inEdit)    {
            o.setEdit()
        } else  {
            o.fromEdit(true)
        }
    }
    this.addEventListener(labelevents);
}
EditLabel.prototype = new Label();
EditLabel.prototype.setEdit=function()  {
    this.inEdit=true;
    //this.input.style.display='block';
    if(this.clearOnEdit)    {
        this.setText('|')
    } else  {
        this.setText(this.text+'|')
    }
    this.setBgColor('#000099');
    this.setFontColor('#FFFFFF');
    dynapi.document.setEditTarget(this);
}
EditLabel.prototype.fromEdit=function(s)  {
    if(s)  {
        dynapi.document.setEditTarget(null);
    } else  {
        this.inEdit=false;
        var curVal=this.text
        var curInd=curVal.indexOf('|');
        var newVal=this.formatText(curVal.substring(0,curInd)+curVal.substring(curInd+1,curVal.length));
        this.setText(newVal);
        this.setBgColor(null);
        this.setFontColor('#000000');
    }
}
// overide this method to provide formatting on text set... ie money format change 20.2 to 20.20
EditLabel.prototype.formatText=function(text)    {return text}
// add in the keyController stuff to DynDocument
DynDocument.prototype.setEditTarget=function(o)   {
    if(this.isDynDocument)  {
        if(this.editTarget) {
            this.editTarget.fromEdit(false);
        }
        this.editTarget=o;
    } else  {
        this.parent.setEditTarget(o);
    }
}
DynDocument.prototype.getEditTarget=function()   {
    if(this.isDynDocument)  {
        return this.editTarget
    } else  {
        this.parent.getEditTarget(o);
    }
}
DynDocument.prototype.editTarget=null
DynKeyEvent.EventMethod = function(e) {
    var dynobject=this.lyrobj;
    if(is.def) {
        if (is.ie) var e=dynobject.frame.event;
        else if (e.eventPhase!=3) return false;
        e.cancelBubble=true;
    }
    if(is.def) var realsrc = Methods.getContainerLayerOf(is.ie?e.srcElement:e.target)||dynobject;
    else if(is.ns4) var realsrc=e.target.lyrobj;
    if (!realsrc) return true;
    var evt=DynKeyEvent._e;
    evt.type=e.type;
    evt.src=realsrc;
    evt.browserReturn=false;
    evt.bubble=true;
    evt.which=(is.ns4)?e.which:e.keyCode;
    var curKey = String.fromCharCode(evt.which).toLowerCase();
    if (((curKey>='a')&&(curKey<='z'))||((curKey>='0')&&(curKey<='9'))) evt.charKey=curKey;
    else evt.charKey=null;
    evt.ctrlKey=(is.ns4)?(e.modifiers & Event.CONTROL_MASK):(e.ctrlKey||e.ctrlLeft||e.keyCode==17);
    evt.shiftKey=(is.ns4)?(e.modifiers & Event.SHIFT_MASK):(e.shiftKey||e.shiftLeft||e.keyCode==16);
    evt.orig=e;
    realsrc.invokeEvent(evt.type,evt);
    evt.bubbleEvent();
    return evt.browserReturn;
};
DynDocument.prototype.createController= function()    {
    keyController = new EventListener(this)
    keyController.onkeyup=function(e) {
        if(dynapi.document.editTarget) {
            var o=dynapi.document.editTarget;
            //get text without cursor
            //alert(e.which)
            var cache1=o.text.substring(0,o.text.indexOf('|'))
            var cache2=o.text.substring(o.text.indexOf('|')+1,o.text.length)
            e.charKey=(e.shiftKey)?e.charKey.toUpperCase():e.charKey;
            e.charKey= (e.which==32)?' ':e.charKey;
            if(e.which==8)  {
                cache1=cache1.substring(0,cache1.length-1);
            }
            if(e.which==13)  {
                doc.setEditTarget(null);
                return
            }
            if(e.which==37) {
                cache2=cache1.charAt(cache1.length-1)+cache2;
                cache1=cache1.substring(0,cache1.length-1);
            }
            if(e.which==39) {
                cache1=cache1+cache2.charAt(0)
                cache2=cache2.substring(1,cache2.length);
            }
            //pick up any other null chars
            e.charKey=(e.charKey==null)?'':e.charKey;
            //insert new character and reinsert cursor
            o.setText(cache1+e.charKey+'|'+cache2);
        }   
    }
    keyController.onmouseup=function(e)   {
        doc=e.getSource();
        doc.setEditTarget(null);
    }
    this.addEventListener(keyController);
}
DynDocument.prototype._OldKC_specificCreate = DynDocument.prototype.specificCreate
DynDocument.prototype.specificCreate = function() {
    this._OldKC_specificCreate()
    this.createController()
}
