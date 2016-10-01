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

package net.ar.webonswing.managers.state;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;

import org.apache.commons.logging.*;

public class WindowTreeStateManagerFactory
{
	public static WindowTreeStateManager createWindowTreeStateFromSerialized(String aResource)
	{
		try
		{
			return (WindowTreeStateManager) WosHelper.getObjectFromFile(WosHelper.getResourceAsStream(aResource));
		}
		catch (Exception e)
		{
			LogFactory.getLog(WosFramework.class).error("Cannot init WindowTreeStateManager, creating default...", e);
			return new WindowTreeStateManager();
		}
	}
}
