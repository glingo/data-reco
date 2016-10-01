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

package net.ar.webonswing.wrapping.swing;

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.wrapping.*;

public class SwingWindowWrapper extends WindowWrapper implements Virtualizable
{
	public SwingWindowWrapper()
	{
		super();
	}

	public SwingWindowWrapper(Object aComponent)
	{
		super(aComponent);
	}

	public void setBounds(Rectangle aRectangle)
	{
		JRootPane theRootPane= ((RootPaneContainer) getWrappedComponent()).getRootPane();
		theRootPane.getTopLevelAncestor().setBounds(aRectangle);
		theRootPane.getContentPane().setBounds(aRectangle);
		theRootPane.setBounds(aRectangle);
	}

	public VisualWindow newInstance()
	{
		return new SwingWindowWrapper();
	}

	public Rectangle getBounds()
	{
		return ((RootPaneContainer) getWrappedComponent()).getContentPane().getBounds();
	}

	public Object getVirtualInstance(PersistenceContributionContainer aPersistenceContainer)
	{
		return new VirtualWindowWrapper(newInstance(), getName(), getTypeId(), aPersistenceContainer);
	}
}
