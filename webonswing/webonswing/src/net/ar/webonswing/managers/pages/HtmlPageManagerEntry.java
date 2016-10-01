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

package net.ar.webonswing.managers.pages;

import java.io.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;

import org.apache.commons.lang.*;

public class HtmlPageManagerEntry implements Serializable
{
	protected String pageClass;
	protected String windowClass;
	protected String webPath;
	protected String windowPlace;

	public HtmlPageManagerEntry()
	{
	}

	public HtmlPageManagerEntry(String aPath, String aPageClassName, String aWindowClassName, String aWindowPlace)
	{
		webPath= aPath;
		pageClass= aPageClassName;
		windowClass= aWindowClassName;
		windowPlace= aWindowPlace;
	}

	public String getPageClassName()
	{
		return pageClass;
	}

	public boolean like(HtmlPageManagerEntry anEntry)
	{
		boolean theResult= anEntry.getPageClassName() == null || anEntry.getPageClassName().equals(getPageClassName());
		theResult &= anEntry.getWindowClassName() == null || anEntry.getWindowClassName().equals(getWindowClassName());
		theResult &= anEntry.getPath() == null || anEntry.getPath().equals(getPath());

		return theResult;
	}

	public HtmlPageManagerEntry getDefaultEntryLike()
	{
		if (pageClass != null)
		{
			if (pageClass.endsWith("Page"))
				windowClass= pageClass.substring(0, pageClass.length() - 4);

			webPath= "/" + StringUtils.replace(windowClass, ".", "/");
		}
		else if (windowClass != null)
		{
			pageClass= windowClass + "Page";
			webPath= "/" + StringUtils.replace(windowClass, ".", "/");
		}
		else if (webPath != null)
		{
			windowClass= StringUtils.replace(webPath, "/", ".").substring(1);
			pageClass= windowClass + "Page";
		}

		windowPlace= WosFramework.getInstance().getPageManager().getDefaultWindowPlace();

		if (pageClass == null || windowClass == null || webPath == null)
			throw new WebOnSwingException("Cannot find an entry like '" + this +"'");

		try
		{
			Class.forName(pageClass);
		}
		catch (ClassNotFoundException e)
		{
			pageClass= WosFramework.getInstance().getPageManager().getDefaultPageClass();
		}

		return this;
	}

	public String getPath()
	{
		return webPath;
	}

	public String getWindowClassName()
	{
		return windowClass;
	}

	public void setPageClassName(String unString)
	{
		pageClass= unString;
	}

	public void setPath(String unString)
	{
		webPath= unString;
	}

	public void setWindowClassName(String unString)
	{
		windowClass= unString;
	}

	public String toString()
	{
		return "Path= " + getPath() + " : Page= " + getPageClassName() + " : Window= " + getWindowClassName();
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof HtmlPageManagerEntry)
			return getPath().equals(((HtmlPageManagerEntry) obj).getPath());
		else
			return getPath().equals(obj.toString());
	}

	public int hashCode()
	{
		return getPath().hashCode();
	}

	public String getWindowPlace()
	{
		return windowPlace;
	}
	public void setWindowPlace(String aWindowPlace)
	{
		windowPlace= aWindowPlace;
	}
}