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

package net.ar.webonswing.wrapping;

import java.io.*;

public abstract class AbstractComponentWrapper extends AbstractVisualComponent implements Serializable, ComponentWrapper
{
	transient protected Object theWrappedComponent;

	public AbstractComponentWrapper()
	{
		this(null);
	}

	public AbstractComponentWrapper(Object aComponent)
	{
		super();
		theWrappedComponent= aComponent;
		theName= "";
	}

	public int hashCode()
	{
		return theWrappedComponent.hashCode();
	}

	public Object getWrappedComponent()
	{
		return theWrappedComponent;
	}

	public void setWrappedComponent(Object object)
	{
		theWrappedComponent= object;
	}

	public String getTypeId()
	{
		return getWrappedComponent().getClass().getName();
	}
}