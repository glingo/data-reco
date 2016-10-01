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

package net.ar.webonswing.render;

import java.util.*;

import net.ar.webonswing.managers.cache.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public class CachingComponentRenderer extends DefaultComponentRenderer
{
	protected String theKey;
	protected String theWindowName;
	protected ComponentCacheStateContainer theCacheManager= new ComponentCacheStateContainer();

	public CachingComponentRenderer(String aWindowName, String aKey)
	{
		super();
		theWindowName= aWindowName;
		theKey= aKey == null ? "" : aKey;
	}

	public Visitable render(VisualComponent aComponent)
	{
		if (theCacheManager.isCached(theWindowName, aComponent.getName()))
		{
			Object[] theState= theCacheManager.getCacheData(theWindowName, aComponent.getName(), theKey);

			String theRendering;

			if (theState == null || (theRendering= (String)theState[0]) == null)
			{
				theRendering= new ContentRenderer(super.render(aComponent)).getResult();

				List theProps= theRenderingContribManager.getComponentProps(aComponent);
				theProps.set(0, null);

				theCacheManager.setCacheData(theWindowName, aComponent.getName(), theKey, new Object[] { theRendering, theProps });
			}
			else
				theRenderingContribManager.setComponentProps(aComponent, (List)theState[1]);

			return new TextContent(theRendering);
		}

		return super.render(aComponent);
	}

	public String getKey()
	{
		return theKey;
	}

	public void setKey(String aString)
	{
		theKey= aString;
	}

}