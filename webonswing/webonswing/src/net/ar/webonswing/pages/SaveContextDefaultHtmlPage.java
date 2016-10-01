// WebOnSwing - Web Application Framework
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

package net.ar.webonswing.pages;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ar.webonswing.WosFramework;

public class SaveContextDefaultHtmlPage extends DefaultHtmlPage
{
	protected HttpSession session;
	protected HttpServletRequest request;

	public void dispatchEvents()
	{
		super.dispatchEvents();

		copyAttributesToHttpSession(session);
	}

	public static void copyAttributesToHttpSession(HttpSession aSession)
	{
		removeLastAttributes(aSession);
		Map sessionContext= WosFramework.getSessionContext();
		for (Iterator i= sessionContext.entrySet().iterator(); i.hasNext();)
		{
			Map.Entry entry= (Entry) i.next();
			aSession.setAttribute((String) entry.getKey(), entry.getValue());
		}
	}

	protected static void removeLastAttributes(HttpSession aSession)
	{
		Enumeration names2= aSession.getAttributeNames();
		while (names2.hasMoreElements())
			aSession.removeAttribute((String) names2.nextElement());
	}

	public void renderPage(HttpServletResponse aResponse) throws Exception
	{
		super.renderPage(aResponse);
		copyAttributesToHttpSession(session);
	}
	
	public void prepareFromRequest(HttpServletRequest aRequest)
	{
		session= aRequest.getSession();
		
		copyAttributesFromHttpSession(session);
		super.prepareFromRequest(aRequest);
	}

	public void setRequest(HttpServletRequest aRequest)
	{
		session= aRequest.getSession();
	}
	
	public static void copyAttributesFromHttpSession(HttpSession aSession)
	{
		Map sessionContext= WosFramework.getSessionContext();
		sessionContext.clear();
		Enumeration sessionAttributesNames= aSession.getAttributeNames();
		while (sessionAttributesNames.hasMoreElements())
		{
			String name= (String) sessionAttributesNames.nextElement();
			Object attribute= aSession.getAttribute(name);
			if (attribute != null)
				sessionContext.put(name, attribute);
		}
	}
}