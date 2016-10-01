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
import java.util.List;

import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.visitor.*;

public interface VisualComponent extends Visitable
{
	public List getChilds();
	public void setChilds(List list);
	public void addChild(VisualComponent aChild);
	public void removeChild(VisualComponent aChild);
	public VisualComponent getChildAt(int aPosition);
	public int getChildCount();
	
	public String getName();
	public void setName(String string);
	
	public ComponentContributor getContributor();
	public void setContributor(ComponentContributor contributor);

	public VisualComponent getParent();
	public void setParent(VisualComponent id);
	public VisualComponent getTopParent();
	public VisualComponent findComponent(String aComponentName);

	public String getTypeId();

	public void putClientProperty(Object aKey, Object aValue);
	public Object getClientProperty(Object aKey);

	public Rectangle getBounds();
	public void setBounds(Rectangle aRectangle);
	public String getUIClassID();
	public void doLayout();
}