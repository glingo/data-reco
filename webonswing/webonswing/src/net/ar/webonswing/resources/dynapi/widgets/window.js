//WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

function VisualWindow(x, y, width, height, title, content) 
{
	this.inheritFrom= DynLayer;
	this.inheritFrom();

	this.setContent= function (aContent)
	{
	    this.viewPane.setContent (aContent);
		this.setVisible(true);
	}

    this.setPosition(x, y);
    this.setSize(width, height);
    this.minimized= false;
    this.maximized= false;
    this.buttonSize= 16;
    this.headerHeight= 20;

    this.header= new DynLayer();
    this.header.setSize(this.getHeight(), this.headerSize);
	this.setHTML ('<font face="arial" size="1+">' + title + '</font>');
    
    this.minimizeButton= new Button("-");
    this.minimizeButton.setSize(this.buttonSize, this.buttonSize);
    this.maximizeButton= new Button("+");
    this.maximizeButton.setSize(this.buttonSize, this.buttonSize);
    this.closeButton= new Button("x");
    this.closeButton.setSize(this.buttonSize, this.buttonSize);

    this.updateButtonLocation= function ()
	{
        this.minimizeButton.setLocation(this.getWidth() - 3 * (this.buttonSize +2) , 2);
        this.maximizeButton.setLocation(this.getWidth() - 2 * (this.buttonSize +2), 2);
        this.closeButton.setLocation(this.getWidth() - 1 * (this.buttonSize +2), 2);
	}

    this.updateButtonLocation();
    
    this.setBgColor('#5CB6F2');
    
    this.viewPane= new ViewPane(null, 0, this.headerHeight, width, height - this.headerHeight);
    this.setContent (content);
	this.viewPane.setLocalStyleAttribute('imageCorner',Styles.getImage('corner.gif',16,16));    
	
	DragEvent.enableDragEvents(this.viewPane.lyrCorner);
	
    this.viewPane.lyrCorner.addEventListener(VisualWindow.CornerResizeEvent);

	this.contentEvent = new EventListener(this);
	this.contentEvent.onmouseover = function(e) { e.preventBubble(); };
	this.viewPane.addEventListener(this.contentEvent);

	this.addChild(this.minimizeButton);
	this.addChild(this.maximizeButton);
	this.addChild(this.closeButton);
	this.addChild(this.viewPane);
	

	//DragEvent.setDragBoundary(this, 0, this.getWidth(), this.header.getHeight(), 0);
	DragEvent.enableDragEvents(this);

	this.headerClickEvent = 
	{
    	onmousedown : function(e)
		{
    		var window= e.getSource();
    		window.setZIndex({topmost:true});
		}
	};
	this.addEventListener (this.headerClickEvent);

	this.minimizeEvent = 
	{
    	onmousedown : function(e)
		{
    		var window= e.getSource().parent;
			window.minimize();
		}
	};
	this.minimizeButton.addEventListener (this.minimizeEvent);

	this.maximizeEvent = 
	{
    	onmousedown : function(e)
		{
    		var window= e.getSource().parent;
			window.maximize();
		}
	};
	this.maximizeButton.addEventListener (this.maximizeEvent);

	this.restoreLastSize= function ()
	{
		this.setSize (this.lastWidth, this.lastHeight);
	}
	this.saveLastSize= function ()
	{
		this.lastWidth= this.getWidth();
		this.lastHeight= this.getHeight();
	}
	
	this.minimize= function ()
	{
	    if (this.minimized)
			this.restoreLastSize();
	    else
	    {
	    	this.saveLastSize();
			this.setSize (this.getWidth(), this.headerHeight);
	    }

	    this.minimized= !this.minimized;
	}

	this.maximize= function ()
	{
		/*
		if (this.maximized) this.restoreLastSize(); else { this.saveLastSize();
		 * //this.setSize(this.parent.getWidth(),this.parent.getHeight()) }
		 */
		this.maximized= !this.maximized;
	}
	
	this.saveLastSize();	
	this.setVisible(true);
	
    return this;
}

VisualWindow.CornerResizeEvent= 
{
	ondragmove:function(e)
	{
		var o = e.getSource();
		var vp = o.parent; 

		if (o.getVisible())
		{
			var w = o.getPageX() + o.getWidth() - vp.getPageX()
			var h = o.getPageY() + o.getHeight() - vp.getPageY()
			
			vp.setSize (w, h);
			vp.parent.setSize (w, h + vp.parent.headerHeight);
			vp.parent.updateButtonLocation();
		}
	}
};


VisualWindow.prototype=new DynLayer;
