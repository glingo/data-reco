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
import java.lang.reflect.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;

public class WebOnSwingServlet extends HttpServlet
{
	public void init() throws ServletException
	{
		try
		{
			Object controller= getClassLoader().loadClass("net.ar.webonswing.servlets.WebOnSwingController").newInstance();
			Method initMethod= controller.getClass().getMethod("init", null);
			initMethod.invoke(controller, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void service(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException
	{
		try
		{
			Object controller= getClassLoader().loadClass("net.ar.webonswing.servlets.WebOnSwingController").newInstance();
			Method serviceMethod= controller.getClass().getMethod("service", new Class[] { HttpServletRequest.class, HttpServletResponse.class });
			serviceMethod.invoke(controller, new Object[] { aRequest, aResponse });
		}
		catch (Exception e)
		{
			throw new ServletException(e);
		}
	}

	protected ClassLoader getClassLoader()
	{
		return WosFramework.useWosClassLoader() ? WosClassLoader.getInstance().setNewPackage(System.getProperty(WosFramework.TRANSFORMATION_PACKAGE)) : Thread.currentThread().getContextClassLoader();
	}
}