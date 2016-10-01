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

package net.ar.webonswing.remote;

import java.util.*;

import net.ar.webonswing.ui.*;
import net.ar.webonswing.wrapping.*;

public abstract class RemoteEventDispatcher
{
	public abstract void generateAndDispatchEvents (String anUrl, Map aParameters);
	
	public void dispatchEvents (List anEventList)
	{
		for (Iterator i= anEventList.iterator(); i.hasNext(); )
		{
			Object[] thePair= (Object[]) i.next();

			List theComponentEvents= (List) thePair[1];
			if (!theComponentEvents.isEmpty())
				((ComponentUIContributor) ((VisualComponent) thePair[0]).getContributor()).dispatchEvents(theComponentEvents);
		}
	}
}
