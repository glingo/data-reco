//WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
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

package net.ar.webonswing.remote.js;

import net.ar.webonswing.helpers.*;

import org.apache.commons.lang.*;

public class ValueFromGetter implements JsStatement
{
	private Object object;
	private String propertyName;

	public ValueFromGetter(Object anObject, String aString)
	{
		object= anObject;
		propertyName= aString;
	}

	public String renderJs()
	{
		try
		{
			return adaptPropertyValue(object.getClass().getMethod("get" + StringUtils.capitalise(propertyName), null).invoke(object, null));
		}
		catch (Exception e)
		{
			throw new WebOnSwingException(e);
		}
	}

	protected String adaptPropertyValue(Object aPropertyValue)
	{
		if (aPropertyValue instanceof String)
			aPropertyValue= "'" + aPropertyValue + "'";

		return aPropertyValue + "";
	}
}