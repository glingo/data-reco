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

package net.ar.webonswing.managers.skins;

import java.util.*;

import net.ar.webonswing.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.swing.layouts.*;

import org.apache.commons.lang.*;

public class Skin
{
	protected String name;
	protected Hashtable aliases= new Hashtable();
	protected String imagesPath;
	protected String cssPath;
	protected String defaultImageExtension;

	public Skin(String aName, String aPath, String aDefaultImageExtension, String aCssPath)
	{
		name= aName;
		setImagesPath(aPath);
		setCssPath(aCssPath);
		defaultImageExtension= aDefaultImageExtension;
	}

	public void addAlias(String anAlias, String aName)
	{
		aliases.put(anAlias, aName);
	}

	public String getName(String anAlias)
	{
		String value= (String) aliases.get(anAlias);

		if (value == null)
		{
			value= name + "-" + anAlias;

			for (Iterator i= aliases.keySet().iterator(); i.hasNext();)
			{
				String key= (String) i.next();

				if (anAlias.startsWith(key))
					value= StringUtils.replace(anAlias, key, (String) aliases.get(key));
			}
		}

		return value;
	}

	public boolean equals(Object aObj)
	{
		if (aObj instanceof Skin)
		{
			Skin otherSkin= (Skin) aObj;
			return otherSkin.name.equals(name);
		}
		else
			return false;
	}

	public KeyPositionTemplate getCurrentTemplateFor(String anAlias)
	{
		return WosFramework.getKeyPositionTemplateForName(getName(anAlias));
	}

	public PropagateTemplateLayoutByName getCurrentPropagateTemplateFor(String anAlias)
	{
		return WosFramework.getPropagateTemplateLayoutByNameFor(getName(anAlias));
	}

	public void setImagesPath(String aPath)
	{
		imagesPath= aPath;
	}
	public String getImagesPath()
	{
		return imagesPath;
	}

	public String getDefaultImageExtension()
	{
		return defaultImageExtension;
	}
	public void setDefaultImageExtension(String aDefaultImageExtension)
	{
		defaultImageExtension= aDefaultImageExtension;
	}
	
	public Hashtable getAliases()
	{
		return aliases;
	}
	public void setAliases(Hashtable aAliases)
	{
		aliases= aAliases;
	}
	public String getCssPath()
	{
		return cssPath;
	}
	public void setCssPath(String aCssPath)
	{
		cssPath= aCssPath;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String aName)
	{
		name= aName;
	}
	
	public String toString()
	{
		return name;
	}
}