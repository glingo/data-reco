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

package net.ar.webonswing.toolkit;

import java.awt.*;
import java.util.*;

public class WosEventQueue extends EventQueue
{
	protected void dispatchEvent(AWTEvent event)
	{
	}

	public AWTEvent getNextEvent() throws InterruptedException
	{
		throw new InterruptedException();
	}

	public AWTEvent peekEvent()
	{
		return null;
	}

	public AWTEvent peekEvent(int id)
	{
		return null;
	}

	protected void pop() throws EmptyStackException
	{
	}

	public void postEvent(AWTEvent theEvent)
	{
	}

	public void push(EventQueue newEventQueue)
	{
	}

}
