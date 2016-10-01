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

package net.ar.webonswing.wrapping;

import java.awt.*;
import java.util.*;

import net.ar.webonswing.managers.persistence.*;

public class DefaultVisualWindow extends DefaultVisualComponent implements VisualWindow, Virtualizable
{
	protected VisualComponent theRootComponent;
	
	public DefaultVisualWindow()
	{
		theClienteProps= new Hashtable();
	}

	public Object getLockObject()
	{
		return this;
	}

	public VisualWindow newInstance()
	{
		return new DefaultVisualWindow();
	}

	public VisualComponent getRootComponent()
	{
		return theRootComponent;
	}

	public void setRootComponent(VisualComponent aRootComponent)
	{
		theRootComponent= aRootComponent;
		aRootComponent.setParent(this);
	}

	public void doLayout()
	{
		getRootComponent().doLayout();
	}

	public String getUIClassID()
	{
		return "";
	}

	public VisualComponent findComponent(String aComponentName)
	{
		return getRootComponent().findComponent(aComponentName);
	}

	public Rectangle getBounds()
	{
		return theRootComponent.getBounds();
	}

	public void setBounds(Rectangle aRectangle)
	{
		theRootComponent.setBounds(aRectangle);
	}

	public Object getVirtualInstance(PersistenceContributionContainer aPersistenceContainer)
	{
		return new VirtualWindow(this, getName(), getTypeId(), aPersistenceContainer);
	}
}
