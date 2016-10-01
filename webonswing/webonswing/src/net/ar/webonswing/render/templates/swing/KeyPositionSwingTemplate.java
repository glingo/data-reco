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

package net.ar.webonswing.render.templates.swing;

import java.awt.*;
import java.util.*;
import java.util.List;

import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public class KeyPositionSwingTemplate implements KeyPositionTemplate
{
	HashMap theKeys= new HashMap();
	Container container;

	public KeyPositionSwingTemplate(Container aContainer)
	{
		container= aContainer;
	}

	public Rectangle addKey(String aKey, VisualComponent aComponent)
	{
		for (int i= 0; i < container.getComponents().length; i++)
		{
			Component placeholder= container.getComponents()[i];

			if (placeholder.getName().equals(aKey))
				return placeholder.getBounds();
		}

		throw new TemplateNotFoundException("Cannot find a placeholder for '" + aKey + "'");
	}

	public void addElement(TemplateElement anElement)
	{
	}

	public TemplateBody getBody()
	{
		return null;
	}

	public void setBody(TemplateBody aBody)
	{
	}

	public void removeElement(TemplateElement anElement)
	{
	}

	public TemplateElement getElementAt(int i)
	{
		return null;
	}

	public int getElementCount()
	{
		return 0;
	}

	public List getElements()
	{
		return null;
	}

	public void accept(Visitor aTemplateVisitor)
	{
	}
}
