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

package net.ar.webonswing.servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.windows.*;
import net.ar.webonswing.pages.*;
import net.ar.webonswing.wrapping.*;

import org.apache.commons.lang.*;
import org.apache.commons.logging.*;

public class WebOnSwingController 
{
	private static final String WINDOW_ID= "theWindowId";
	private static final String WINDOW_MANAGER_ATTRIBUTE= "theWindowManager";

	public void init() throws ServletException
	{
		WosFramework.getInstance().init();
	}

	public void service(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException
	{
		try
		{
			WosFramework.getInstance().setContextPath(aRequest.getContextPath().length() == 0 ? "" : aRequest.getContextPath());

			if (WosFramework.getInstance().getPageManager() == null)
				throw new Exception("PageManager not found");

			String thePagePath= StringUtils.mid(aRequest.getServletPath(), 0, aRequest.getServletPath().indexOf("."));

			HtmlPage theRequestPage= (HtmlPage)WosFramework.getInstance().getPageManager().getPageForPath(thePagePath);
			theRequestPage.prepareFromRequest(aRequest);

			WindowManager theWindowManager= getWindowManager(aRequest, theRequestPage);

			VisualWindow theRequestWindow= processRequest(aRequest, thePagePath, theWindowManager, theRequestPage);
			doResponse(aResponse, theWindowManager, theRequestPage, theRequestWindow, aRequest);

		}
		catch (Exception e)
		{
			if (WosFramework.getInstance().isCatchExceptions())
				showException(aResponse, e);
			else
				throw new ServletException(e);
		}
	}

	protected void showException(HttpServletResponse aResponse, Exception e) throws IOException
	{
		aResponse.getWriter().write("<html>\n<head>\n</head>\n<body>\n" + WosHelper.exceptionToHtml(e) + "</body>\n</html>\n");
		LogFactory.getLog(WebOnSwingController.class).error("Response error", e);
	}

	private WindowManager getWindowManager(HttpServletRequest aRequest, HtmlPage aRequestPage)
	{
		WindowManager theWindowManager= null;

		if (aRequestPage.isWindowInSession())
			theWindowManager= (WindowManager)aRequest.getSession().getAttribute(WINDOW_MANAGER_ATTRIBUTE);
		else
			theWindowManager= (WindowManager)aRequestPage.restoreValue(WINDOW_MANAGER_ATTRIBUTE);

		if (theWindowManager == null)
			theWindowManager= WosFramework.getInstance().getWindowManager();

		return theWindowManager;
	}

	protected VisualWindow processRequest(HttpServletRequest aRequest, String thePagePath, WindowManager theWindowManager, HtmlPage theRequestPage)
	{
		String theRequestWindowId= (String)theRequestPage.restoreValue(WINDOW_ID);

		VisualWindow theRequestWindow= theWindowManager.getWindow(theRequestWindowId);

		if (theRequestWindow == null)
			theRequestWindow= WosFramework.getInstance().getPageManager().getWindowForPath(thePagePath);

		theWindowManager.setCurrentWindow(theRequestWindow);

		if (!WosFramework.getInstance().getPageManager().getPageClassForWindowName(theRequestWindow.getTypeId()).equals(theRequestPage.getClass()))
		{
			theRequestPage= (HtmlPage)WosFramework.getInstance().getPageManager().getPageForWindowName(theRequestWindow.getTypeId());
			theRequestPage.prepareFromRequest(aRequest);
		}
		
		synchronized (theRequestWindow.getLockObject())
		{
			theRequestPage.setWindow(theRequestWindow);
			theRequestPage.processRequest(aRequest);
			new ComponentStandardizer().standardizeWindowHierarchy(theRequestPage.getWindow());
			theRequestPage.dispatchEvents();
		}

		return theRequestWindow;
	}

	protected void doResponse(HttpServletResponse aResponse, WindowManager theWindowManager, HtmlPage theRequestPage, VisualComponent theRequestWindow, HttpServletRequest aRequest) throws Exception
	{
		VisualWindow theCurrentWindow= theWindowManager.getCurrentWindow();

		HtmlPage theResponsePage= theRequestPage;
		if (theCurrentWindow != theRequestWindow)
			theResponsePage= (HtmlPage)WosFramework.getInstance().getPageManager().getPageForWindowName(theCurrentWindow.getTypeId());

		synchronized (theCurrentWindow.getLockObject())
		{
			aResponse.setContentType("text/html");

			theResponsePage.setWindow(new ComponentStandardizer().standardizeWindowHierarchy(theCurrentWindow));
			theResponsePage.persistValue(WINDOW_ID, theCurrentWindow.getName());
			theResponsePage.setRequest(aRequest);
			theResponsePage.prepareResponse(aResponse);

			if (theResponsePage.isWindowInSession())
			{
				theResponsePage.renderPage(aResponse);
				aRequest.getSession().setAttribute(WINDOW_MANAGER_ATTRIBUTE, theWindowManager);
			}
			else
			{
				theResponsePage.persistValue(WINDOW_MANAGER_ATTRIBUTE, theWindowManager);
				theWindowManager.replaceCurrentWindow(theResponsePage.getPersitableWindow());
				theResponsePage.renderPage(aResponse);
			}

		}
	}
}