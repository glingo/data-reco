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

package examples.owncomponents;

import java.util.*;

import javax.swing.event.*;

import net.ar.webonswing.wrapping.*;

public class Button extends DefaultVisualComponent
{
	protected EventListenerList theEvents;
	protected String theText;
	
	public Button (String aText)
	{
		theText= aText;
		theEvents= new EventListenerList();
	}
	
	public String getUIClassID()
	{
		return "ExampleButton";
	}
	
	public void addClickListener(ClickListener aClickListener)
	{
		theEvents.add(ClickListener.class, aClickListener);
	}
	
	public void doClick()
	{
		for (int i= 0; i < theEvents.getListeners(ClickListener.class).length; i++)
			((ClickListener)theEvents.getListeners(ClickListener.class)[i]).clickPerformed(new EventObject(this));
	}
	
	public EventListenerList getEvents()
	{
		return theEvents;
	}

	public void setEvents(EventListenerList aList)
	{
		theEvents= aList;
	}

	public String getText()
	{
		return theText;
	}

	public void setText(String aString)
	{
		theText= aString;
	}

}
