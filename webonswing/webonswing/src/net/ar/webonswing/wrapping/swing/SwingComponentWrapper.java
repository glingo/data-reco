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

package net.ar.webonswing.wrapping.swing;

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.wrapping.*;

public class SwingComponentWrapper extends AbstractComponentWrapper
{
	public SwingComponentWrapper(JComponent aJComponent)
	{
		super(aJComponent);

		Object contributor= getClientProperty(ComponentContributorAssigner.CONTRIBUTOR_NAME_PROPERTY);
		if (contributor != null && contributor instanceof ComponentContributor)
			setContributor((ComponentContributor)contributor);
	}

	public JComponent getJComponent()
	{
		return ((JComponent)getWrappedComponent());
	}

	public Rectangle getBounds()
	{
		return getJComponent().getBounds();
	}

	public void putClientProperty(Object aKey, Object aValue)
	{
		getJComponent().putClientProperty(aKey, aValue);
	}

	public Object getClientProperty(Object aKey)
	{
		return getJComponent().getClientProperty(aKey);
	}

	public void doLayout()
	{
		getJComponent().doLayout();
	}

	public String getUIClassID()
	{
		return getJComponent().getUIClassID();
	}

	public void setBounds(Rectangle aRectangle)
	{
		getJComponent().setBounds(aRectangle);
	}
}