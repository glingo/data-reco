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

package net.ar.webonswing.managers.pages;

import java.util.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.pages.*;
import net.ar.webonswing.wrapping.*;

/**
 * Implementacion de administrador de paginas bastante simple, que mantiene una
 * relacion de 1:1 entre paginas, ventanas y paths. Esto puede traer
 * limitaciones con respecto al reuso de paginas y/o ventanas.
 * 
 * @author Fernando Damian Petrola
 */
public class HtmlPageManager implements PageManager
{
	protected String defaultPageClass;
	protected String defaultWindowPlace;
	protected Hashtable pages;

	public HtmlPageManager()
	{
		this(new Hashtable());
	}

	public HtmlPageManager(Hashtable aPagesSpecification)
	{
		pages= aPagesSpecification;
	}

	public Page getPageForPath(String aPath)
	{
		try
		{
			return createPage(getEntryLike(new HtmlPageManagerEntry(aPath, null, null, null)));
		}
		catch (WebOnSwingException e)
		{
			throw new PageNotFoundException(aPath, e);
		}
	}

	public Page getPageForWindowName(String aWindowName)
	{
		try
		{
			return createPage(getEntryLike(new HtmlPageManagerEntry(null, null, aWindowName, null)));
		}
		catch (WebOnSwingException e)
		{
			throw new PageNotFoundException(aWindowName, e);
		}
	}

	public String getPathForPageName(String aPageName)
	{
		return getEntryLike(new HtmlPageManagerEntry(null, aPageName, null, null)).getPath();
	}

	public VisualWindow getWindowForPageName(String aPageName)
	{
		return createWindow(getEntryLike(new HtmlPageManagerEntry(null, aPageName, null, null)));
	}

	public String getPathForWindowName(String aWindowName)
	{
		return getEntryLike(new HtmlPageManagerEntry(null, null, aWindowName, null)).getPath();
	}

	public VisualWindow getWindowForPath(String aPath)
	{
		return createWindow(getEntryLike(new HtmlPageManagerEntry(aPath, null, null, null)));
	}

	protected VisualWindow createWindow(HtmlPageManagerEntry anEntry)
	{
		try
		{
			Object theNewInstance= Class.forName(anEntry.getWindowClassName()).newInstance();

			HierarchyWrapper theHierarchyWrapper= WosFramework.getInstance().getHierarchyWrapper();
			if (theHierarchyWrapper != null)
				return theHierarchyWrapper.wrapWindow(theNewInstance);
			else if (theNewInstance instanceof VisualWindow)
				return (VisualWindow)theNewInstance;
			else
				throw new Exception("Not available window type for: " + theNewInstance);
		}
		catch (Exception e)
		{
			throw new PageNotFoundException(anEntry.toString(), e);
		}
	}

	protected Page createPage(HtmlPageManagerEntry anEntry)
	{
		try
		{
			HtmlPage thePage= null;
			String thePageClassName= anEntry.getPageClassName();

			if (thePageClassName.equals(""))
				thePageClassName= getDefaultPageClass();

			thePage= (HtmlPage)Class.forName(thePageClassName).newInstance();
			thePage.setWindowClassName(anEntry.getWindowClassName());
			thePage.setWindowInSession(anEntry.getWindowPlace().equals("session"));

			return thePage;
		}
		catch (Throwable e)
		{
			throw new WebOnSwingException("Cannot find a page for entry like '" + anEntry + "'", e);
		}
	}

	private HtmlPageManagerEntry getEntryLike(HtmlPageManagerEntry anEntry)
	{
		HtmlPageManagerEntry foundEntryLike= findEntryLike(anEntry);

		if (foundEntryLike != null)
			return foundEntryLike;
		else
		{
			HtmlPageManagerEntry theDefaultEntryLike= anEntry.getDefaultEntryLike();
			pages.put(theDefaultEntryLike, theDefaultEntryLike);
			return theDefaultEntryLike;
		}
	}

	private HtmlPageManagerEntry findEntryLike(HtmlPageManagerEntry anEntry)
	{
		for (Iterator i= pages.values().iterator(); i.hasNext();)
		{
			HtmlPageManagerEntry theEntry= (HtmlPageManagerEntry)i.next();
			if (theEntry.like(anEntry))
				return theEntry;
		}

		return null;
	}

	public void removePageWithPath(String aPath)
	{
		for (Iterator i= pages.values().iterator(); i.hasNext();)
		{
			HtmlPageManagerEntry theEntry= (HtmlPageManagerEntry)i.next();

			if (theEntry.getPath().equals(aPath))
				i.remove();
		}
	}

	public void addPage(String aPath, String aPage, String aWindow, String aPlace)
	{
		pages.put(aPath, new HtmlPageManagerEntry(aPath, aPage, aWindow, aPlace));
	}

	public Iterator getIterator()
	{
		return pages.values().iterator();
	}

	public Hashtable getPagesTable()
	{
		return pages;
	}

	public void setPagesTable(Hashtable unHashtable)
	{
		pages= unHashtable;
	}

	public Class getPageClassForWindowName(String aWindowName)
	{
		try
		{
			HtmlPageManagerEntry entryLike= getEntryLike(new HtmlPageManagerEntry(null, null, aWindowName, null));
			
			String pageClassName= entryLike.getPageClassName();
			if (pageClassName.trim().length() == 0)
				pageClassName= getDefaultPageClass();
			
			return Class.forName(pageClassName);
		}
		catch (Exception e)
		{
			throw new PageNotFoundException(aWindowName, e);
		}
	}

	public String getDefaultPageClass()
	{
		return defaultPageClass;
	}

	public String getDefaultWindowPlace()
	{
		return defaultWindowPlace;
	}
}