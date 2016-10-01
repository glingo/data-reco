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

package net.ar.webonswing.servlets;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.ar.webonswing.helpers.*;

import org.apache.commons.lang.*;
import org.apache.commons.logging.*;

public class SimpleResourceManager extends HttpServlet
{
	Hashtable typesTable;

	protected void service(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException
	{
		BufferedInputStream bis= null;
		BufferedOutputStream bos= null;
		ServletOutputStream outputStream= null;

		try
		{
			String theServletPath= aRequest.getServletPath() + aRequest.getPathInfo();
			
			String theExtension= StringUtils.right(theServletPath, theServletPath.length() - theServletPath.lastIndexOf(".") - 1);

			URL resource= SimpleResourceManager.class.getResource(theServletPath);
			if (resource != null)
			{
				URLConnection urlConnection= resource.openConnection();
				int length= urlConnection.getContentLength();

				aResponse.setContentType(getMimeType(theExtension));
				aResponse.setContentLength(length);

				bis= new BufferedInputStream(urlConnection.getInputStream());
				bos= new BufferedOutputStream(outputStream= aResponse.getOutputStream());

				WosHelper.copyStreams(bis, bos, 4096);
			}
			else
				aResponse.sendError(404);
		}
		catch (Exception e)
		{
			LogFactory.getLog(SimpleResourceManager.class).warn("Cannot load resource", e);
			aResponse.sendError(404);
		}
		finally
		{
			if (bis != null)
				bis.close();

			if (bos != null)
				bos.close();

			if (outputStream != null)
			{
				outputStream.flush();
				outputStream.close();
			}
		}
	}

	private String getMimeType(String theExtension)
	{
		return (String) typesTable.get(theExtension.trim().toLowerCase());
	}

	public void init() throws ServletException
	{
		typesTable= new Hashtable();

		typesTable.put("js", "text/javascript");
		typesTable.put("css", "text/css");
		typesTable.put("gif", "image/gif");
		typesTable.put("jpg", "image/jpeg");
		typesTable.put("jpeg", "image/jpeg");
		typesTable.put("jpe", "image/jpeg");
		typesTable.put("png", "image/x-png");
	}
}
