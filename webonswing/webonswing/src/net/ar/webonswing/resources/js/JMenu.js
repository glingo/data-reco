//WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

function JMenuItem(aName)
{
	this.inheritFrom= JComponent;
	this.inheritFrom(aName);
}

function JCheckBoxMenuItem(aName)
{
	this.inheritFrom= JMenuItem;
	this.inheritFrom(aName);
}

function JRadioButtonMenuItem(aName)
{
	this.inheritFrom= JMenuItem;
	this.inheritFrom(aName);
}

var theTimer;
var theActiveMenuBar= null;

function JMenuBar(aName)
{
	this.inheritFrom= JComponent;
	this.inheritFrom(aName);

	this.theTabs= new Array();
	this.theTabs.push(document.getElementById(aName+".begin.disabled"));
	this.theTabs.push(document.getElementById(aName+".begin.enabled"));
	this.theActiveMenu= null;
	this.theActiveTabIndex= null;

	this.setStateToTab= function (anIndex, aState)
	{
		var theState1= aState ? "block" : "none";
		var theState2= aState ? "none" : "block";
		var theBeginInc= anIndex == 2 ? 0 : 1;

		this.theTabs[anIndex-1-theBeginInc].style.display= theState1;
		this.theTabs[anIndex-2-theBeginInc].style.display= theState2;
		this.theTabs[anIndex+5].style.display= theState1;
		this.theTabs[anIndex+3].style.display= theState2;
		this.theTabs[anIndex+1].style.display= theState2;
		this.theTabs[anIndex+2].style.display= theState1;
	}
	
	this.show= function ()
	{
		if (this.isVisible == false)
		{
			this.doShow();

			if (this.theActiveTabIndex != null)
			{
				this.setStateToTab (this.theActiveTabIndex, false);
				this.theActiveMenu.hide();
				this.theActiveTabIndex= null;
			}
		}
	}
	
	this.add= function (aComponent)
	{
		this.getComponents().push(aComponent);
		var theTabName= aComponent.getName()+".tab";
		var theTab= document.getElementById(theTabName);
		
		var theTabIndex= this.theTabs.length;
		this.theTabs.push(theTab);
		this.theTabs.push(document.getElementById(aComponent.getName()+".disabled"));
		this.theTabs.push(document.getElementById(aComponent.getName()+".enabled"));

		var theSeparator= document.getElementById(aComponent.getName()+".separator");
		if (theSeparator != null)
		{
			this.theTabs.push(theSeparator);
			this.theTabs.push(document.getElementById(aComponent.getName()+".separator.left"));
			this.theTabs.push(document.getElementById(aComponent.getName()+".separator.right"));
		}
		else
		{
			this.theTabs.push(document.getElementById(this.getName()+".end.disabled"));
			this.theTabs.push(document.getElementById(this.getName()+".end.enabled"));
		}
		
		var theMenuBar= this;
		var theMenu= aComponent;
	
		if (theTab != null)
		{
			theTab.onmouseover= function() 
			{
				window.clearTimeout(theTimer);

				if (theMenuBar.theActiveTabIndex != theTabIndex)
				{
					if (theMenuBar.theActiveMenu != null && theMenuBar.theActiveTabIndex != null)
					{
						theMenuBar.setStateToTab (theMenuBar.theActiveTabIndex, false);
						theMenuBar.theActiveMenu.hide();
					}
					
					theMenuBar.setStateToTab (theTabIndex, true);
					theMenu.show();
					
					theMenuBar.theActiveTabIndex= theTabIndex;
					theMenuBar.theActiveMenu= theMenu;
				}
			};

			theTab.onmouseout= function() 
			{
				window.clearTimeout(theTimer);
				
				theActiveMenuBar= theMenuBar;
				theTimer= window.setTimeout("theActiveMenuBar.setStateToTab (theActiveMenuBar.theActiveTabIndex, false);theActiveMenuBar.theActiveMenu.hide();theActiveMenuBar.theActiveTabIndex= null;", 300);
			};
		}
		
		return this;
	};
	
}

function JMenu(aName) 
{
	this.inheritFrom= JComponent;
	this.inheritFrom(aName);
	
	this.show= function ()
	{
		this.getComponents()[0].show();
		this.doShow();
	}
	this.hide= function ()
	{
		this.getComponents()[0].hide();
		this.doHide();
	}
}

function JPopupMenu (aName)
{
	this.inheritFrom= JMenuBar;
	this.inheritFrom(aName);
}
