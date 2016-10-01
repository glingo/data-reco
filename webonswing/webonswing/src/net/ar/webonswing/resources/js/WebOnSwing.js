// WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
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

var theState= "";
var fireFinish= true;
var theLastEvent= null;
var theLastEventType= null;
var defaultRefreshValue= "";
var defaultFormTarget= "";

if(![].push)
{
  Array.prototype.push=function(i)
  {
	this[this.length]=i;
  }
}

var theComponents= new Array();

function fireSeverSideEvent(anEvent, anEventType)
{
	theLastEvent= anEvent;
	theLastEventType= anEventType;

	if (theState == "")
	{
		if (fireFinish == true)
			ed.dispatch(new FinishEvent("page"));

		if (theState == "")
		{
			theState= "firingEvent";

			document.mainForm.action= thePageName + "." + anEvent.getSource() + "." + anEventType + ".event";
			document.mainForm.submit();

			theLastEvent= null;
			theLastEventType= null;
		}
		else if (theState == "cancel")
			theState= "";
	}
	else
		setTimeout('fireSeverSideEvent(theLastEvent, theLastEventType) ',500);
}

function showRefreshFrame(frameDocument)
{
	var refreshFrame= document.getElementById("refreshFrame");
	var concreteBody= document.getElementById("concreteBody");
	refreshFrame.style.display="block";
	concreteBody.style.display="none";
}

function hideRefreshFrame(frameDocument)
{
	var refreshFrame= document.getElementById("refreshFrame");
	var concreteBody= document.getElementById("concreteBody");
	refreshFrame.style.display="none";
	concreteBody.style.display="block";
}

function copyComponentsContent(frameDocument, anScript)
{
	if (!frameDocument.body.children)
		frameDocument.body.children= frameDocument.body.childNodes;

	var theRefreshedComponents= frameDocument.body.children;
  
	if (theRefreshedComponents != null)
		for (var i= 0; i < theRefreshedComponents.length; i++)
			document.getElementById(theRefreshedComponents[i].id).innerHTML= theRefreshedComponents[i].innerHTML;

	eval(anScript);
	
	document.getElementById("refreshComponents").value= defaultRefreshValue;
	document.mainForm.target= defaultFormTarget;
	theState= "";
}
	
function doRefreshComponents()
{
	if (theState == "")
	{
		theState= "refreshingComponents";
		document.mainForm.target= "refreshFrame";
		document.mainForm.method= "POST";
		document.getElementById("refreshComponents").value= "true";
		document.getElementById("refreshFrame").src= thePageName + ".refresh.component.event";
		document.mainForm.action= thePageName + ".refresh.component.event";
		document.mainForm.submit();
	}
}

function getElement(anElementName)
{
	var theElement= document.getElementsByName(anElementName);

	if (theElement.length == 0)
		theElement= document.getElementById(anElementName);
	else
		theElement= theElement[0];

	return theElement;
}

function addHidden(aName, aValue)
{
	theInput= document.createElement("input");
	theInput.type= "hidden";
	theInput.name= aName;
	getElement("mainForm").appendChild(theInput);
	theInput.value= aValue;
	return theInput;
}

function wosescape(aString)
{
	return aString.replace(new RegExp("_", "g"), "__");
}

function addOrUpdateHidden(aName, aValue)
{
	var theInput= document.getElementById(aName);

	if (theInput == null)
		addHidden(aName, aValue).id= aName;
	else
		theInput.value= aValue;
}

function getComponent(aComponentName)
{
	if (theComponents[aComponentName] == null)
		theComponents[aComponentName]= new Component(aComponentName);

	return theComponents[aComponentName];
}

function EventListener()
{
}
function WosEventObject(aSource)
{
	this.theSource= aSource;
	this.id= "WosEventObject";
	this.getSource= function()
	{
		return this.theSource;
	}
	this.invokeListenerPrototype= function(aListener)
	{
	};
}

function MouseListener()
{
	this.inheritFrom= EventListener;
	this.inheritFrom();

	this.id= "MouseListener";
	this.mouseClicked= function(aMouseEvent)
	{
		fireSeverSideEvent(aMouseEvent, "mouseClicked");
	};
	this.mousePressed= function(aMouseEvent)
	{
		fireSeverSideEvent(aMouseEvent, "mousePressed");
	};
	this.mouseReleased= function(aMouseEvent)
	{
		fireSeverSideEvent(aMouseEvent, "mouseReleased");
	};
	this.mouseMoved= function(aMouseEvent)
	{
		fireSeverSideEvent(aMouseEvent, "mouseMoved");
	};
}

function MouseMotionListener()
{
	this.inheritFrom= MouseListener;
	this.inheritFrom();

	this.id= "MouseMotionListener";
}

function WosMouseEvent(aSource)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.id= "WosMouseEvent";
	this.listener= "MouseListener";
	this.invokeListenerPrototype= function(aListener)
	{
	};
}
function MouseClickedEvent(aSource)
{
	this.inheritFrom= WosMouseEvent;
	this.inheritFrom(aSource);
	this.id= "MouseClickedEvent";
	this.invokeListenerPrototype= function(aMouseClickedListener)
	{
		aMouseClickedListener.mouseClicked(this);
	};
}
function MousePressedEvent(aSource)
{
	this.inheritFrom= WosMouseEvent;
	this.inheritFrom(aSource);
	this.id= "MousePressedEvent";
	this.invokeListenerPrototype= function(aMousePressedListener)
	{
		aMousePressedListener.mousePressed(this);
	};
}
function MouseReleasedEvent(aSource)
{
	this.inheritFrom= WosMouseEvent;
	this.inheritFrom(aSource);
	this.id= "MouseReleasedEvent";
	this.invokeListenerPrototype= function(aMouseReleasedListener)
	{
		aMouseReleasedListener.mouseReleased(this);
	};
}
function MouseMovedEvent(aSource)
{
	this.inheritFrom= WosMouseEvent;
	this.inheritFrom(aSource);
	this.id= "MouseMovedEvent";
	this.invokeListenerPrototype= function(aMouseMoveListener)
	{
		aMouseMoveListener.mouseMoved(this);
	};
}

function ActionListener()
{
	this.id= "ActionListener";
	this.inheritFrom= EventListener;
	this.inheritFrom();
	this.actionPerformed= function(anActionEvent)
	{
		fireSeverSideEvent(anActionEvent, "actionPerformed");
	};
}
function ActionEvent(aSource, aCommand)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.id= "ActionEvent";
	this.listener= "ActionListener";
	this.theCommand= aCommand;
	this.getActionCommand= function()
	{
		return this.theCommand;
	}
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.actionPerformed(this)
		};
}

function ItemListener()
{
	this.id= "ItemListener";
	this.inheritFrom= EventListener;
	this.inheritFrom();
	this.itemStateChanged= function(anItemEvent)
	{
		fireSeverSideEvent(anItemEvent, "itemStateChange");
	};
}
function ItemEvent(aSource)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.id= "ItemEvent";
	this.listener= "ItemListener";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.itemStateChanged(this)
		};
}

function ListSelectionListener()
{
	this.id= "ListSelectionListener";
	this.inheritFrom= EventListener;
	this.inheritFrom();
	this.valueChanged= function(aListSelectionEvent)
	{
		fireSeverSideEvent(aListSelectionEvent, "valueChanged");
	};
}
function ListSelectionEvent(aSource)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.id= "ListSelectionEvent";
	this.listener= "ListSelectionListener";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.valueChanged(this)
		};
}

function TreeSelectionListener()
{
	this.id= "TreeSelectionListener";
	this.inheritFrom= EventListener;
	this.inheritFrom();
	this.valueChanged= function(aTreeSelectionEvent)
	{
		fireSeverSideEvent(aTreeSelectionEvent, "valueChanged");
	};
}
function TreeSelectionEvent(aSource, aTreeNode)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.theTreeNode= aTreeNode;
	this.id= "TreeSelectionEvent";
	this.listener= "TreeSelectionListener";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.valueChanged(this)
		};
}

function ChangeListener()
{
	this.id= "ChangeListener";
	this.inheritFrom= EventListener;
	this.inheritFrom();
	this.stateChanged= function(aChangeListener)
	{
		fireSeverSideEvent(aChangeListener, "stateChanged");
	};
}
function ChangeEvent(aSource)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.id= "ChangeEvent";
	this.listener= "ChangeListener";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.stateChanged(this)
		};
}

function ChangeCheckStateListener()
{
	this.inheritFrom= ItemListener;
	this.inheritFrom();
	this.itemStateChanged= function(anActionEvent)
	{
		var aComponent= getElement(anActionEvent.getSource());
		aComponent.value= aComponent.value == "on" ? "off" : "on";
	};
}

function FinishListener()
{
	this.id= "FinishListener";
	this.inheritFrom= EventListener;
	this.inheritFrom();
	this.finishPerformed= function(aFinishEvent)
	{
	};
}
function FinishEvent(aSource)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.id= "FinishEvent";
	this.listener= "FinishListener";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.finishPerformed(this)
		};
}

function RefreshListener()
{
	this.id= "RefreshListener";
	this.inheritFrom= EventListener;
	this.inheritFrom();
	this.refreshPerformed= function(aFinishEvent)
	{
	};
}
function RefreshEvent(aSource)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.id= "RefreshEvent";
	this.listener= "RefreshListener";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.refreshPerformed(this)
		};
}

function PopupMenuEvent(aSource)
{
	this.inheritFrom= WosEventObject;
	this.inheritFrom(aSource);
	this.id= "PopupMenuEvent";
	this.listener= "PopupMenuListener";
	this.invokeListenerPrototype= function(aListener)
	{
	};
}

function PopupMenuWillBecomeVisibleEvent(aSource)
{
	this.inheritFrom= PopupMenuEvent;
	this.inheritFrom(aSource);
	this.id= "PopupMenuWillBecomeVisibleEvent";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.popupMenuWillBecomeVisible(this)
		};
}

function PopupMenuWillBecomeInvisibleEvent(aSource)
{
	this.inheritFrom= PopupMenuEvent;
	this.inheritFrom(aSource);
	this.id= "PopupMenuWillBecomeInvisibleEvent";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.popupMenuWillBecomeInvisible(this)
		};
}

function PopupMenuCanceledEvent(aSource)
{
	this.inheritFrom= PopupMenuEvent;
	this.inheritFrom(aSource);
	this.id= "PopupMenuCanceledEvent";
	this.invokeListenerPrototype= function(aListener)
	{
		aListener.popupMenuCanceled(this)
		};
}

function PopupMenuListener()
{
	this.id= "PopupMenuListener";
	this.inheritFrom= EventListener;
	this.inheritFrom();
	this.popupMenuWillBecomeVisible= function(aPopupMenuEvent)
	{
		fireSeverSideEvent(aPopupMenuEvent, "popupMenuWillBecomeVisible")
		};
	this.popupMenuWillBecomeInvisible= function(aPopupMenuEvent)
	{
		fireSeverSideEvent(aPopupMenuEvent, "popupMenuWillBecomeInvisible")
		};
	this.popupMenuCanceled= function(aPopupMenuEvent)
	{
		fireSeverSideEvent(aPopupMenuEvent, "popupMenuCanceled")
		};
}
 
function Component(aComponentName)
{
	this.theComponentName= aComponentName;
	this.theListeners= new Array();

	this.addListener= function(aListener)
	{ 
		if (this.theListeners[aListener.id] == null)
			this.theListeners[aListener.id]= new Array();

		this.theListeners[aListener.id].push(aListener);
	}
}

function EventDispatcher()
{
	this.dispatch= function(anEvent)
	{
		var theComponent= getComponent(anEvent.getSource());
		var theListeners= theComponent.theListeners[anEvent.listener];

		for (var i= 0; theListeners != null && i < theListeners.length; i++)
			anEvent.invokeListenerPrototype(theListeners[i]);
	}
}

var ed= new EventDispatcher();

function JComponent(aName)
{
	this.theName= aName;
	this.theComponents= new Array();
	this.theElement= document.getElementById(aName);
	this.isVisible= false;

	this.add= function(aComponent)
	{
		this.theComponents.push(aComponent);
		return this;
	};
	this.getName= function(aComponent)
	{
		return this.theName;
	};
	this.getComponents= function(aComponent)
	{
		return this.theComponents;
	};
	this.doShow= function()
	{
		if (this.theElement != null)
			this.theElement.style.display= "block";

		this.isVisible= true;
	}
	this.doHide= function()
	{
		if (this.theElement != null)
			this.theElement.style.display= "none";

		this.isVisible= false;
	}
	this.show= function()
	{
		if (this.isVisible == false)
			this.doShow();
	}
	this.hide= function()
	{
		if (this.isVisible == true)
			this.doHide();
	}
}
