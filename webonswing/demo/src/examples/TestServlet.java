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

package examples;
import java.io.*;
import java.security.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.ar.webonswing.servlets.*;

public class TestServlet
{
	public static void main(String[] args) throws ServletException, IOException
	{
		WebOnSwingServlet theServlet= new WebOnSwingServlet();
		theServlet.init();
		theServlet.service(new MyRequest("", "/WosExamples/CheckBoxDemo.page", new HashMap()), new MyResponse(new PrintWriter(System.out, true)));
	}

	static class MyResponse implements HttpServletResponse
	{
		private PrintWriter theWriter;

		public MyResponse(PrintWriter aWriter)
		{
			theWriter= aWriter;
		}

		public void addCookie(Cookie arg0)
		{

		}

		public boolean containsHeader(String arg0)
		{

			return false;
		}

		public String encodeURL(String arg0)
		{

			return null;
		}

		public String encodeRedirectURL(String arg0)
		{

			return null;
		}

		public String encodeUrl(String arg0)
		{

			return null;
		}

		public String encodeRedirectUrl(String arg0)
		{

			return null;
		}

		public void sendError(int arg0, String arg1) throws IOException
		{

		}

		public void sendError(int arg0) throws IOException
		{

		}

		public void sendRedirect(String arg0) throws IOException
		{

		}

		public void setDateHeader(String arg0, long arg1)
		{

		}

		public void addDateHeader(String arg0, long arg1)
		{

		}

		public void setHeader(String arg0, String arg1)
		{

		}

		public void addHeader(String arg0, String arg1)
		{

		}

		public void setIntHeader(String arg0, int arg1)
		{

		}

		public void addIntHeader(String arg0, int arg1)
		{

		}

		public void setStatus(int arg0)
		{

		}

		public void setStatus(int arg0, String arg1)
		{

		}

		public String getCharacterEncoding()
		{

			return null;
		}

		public ServletOutputStream getOutputStream() throws IOException
		{

			return null;
		}

		public PrintWriter getWriter() throws IOException
		{
			return theWriter;
		}

		public void setContentLength(int arg0)
		{

		}

		public void setContentType(String arg0)
		{

		}

		public void setBufferSize(int arg0)
		{

		}

		public int getBufferSize()
		{

			return 0;
		}

		public void flushBuffer() throws IOException
		{

		}

		public void resetBuffer()
		{

		}

		public boolean isCommitted()
		{

			return false;
		}

		public void reset()
		{

		}

		public void setLocale(Locale arg0)
		{

		}

		public Locale getLocale()
		{

			return null;
		}

	}

	static class MyRequest implements HttpServletRequest
	{
		private Map theParameters;
		private String theServletPath;
		private String theContextPath;

		public MyRequest(String aContextPath, String aServletPath, Map aParameters)
		{
			theContextPath= aContextPath;
			theServletPath= aServletPath;
			theParameters= aParameters;
		}

		public String getAuthType()
		{

			return null;
		}

		public Cookie[] getCookies()
		{

			return null;
		}

		public long getDateHeader(String arg0)
		{

			return 0;
		}

		public String getHeader(String arg0)
		{

			return null;
		}

		public Enumeration getHeaders(String arg0)
		{

			return null;
		}

		public Enumeration getHeaderNames()
		{

			return null;
		}

		public int getIntHeader(String arg0)
		{

			return 0;
		}

		public String getMethod()
		{

			return null;
		}

		public String getPathInfo()
		{

			return null;
		}

		public String getPathTranslated()
		{

			return null;
		}

		public String getContextPath()
		{
			return theContextPath;
		}

		public String getQueryString()
		{

			return null;
		}

		public String getRemoteUser()
		{

			return null;
		}

		public boolean isUserInRole(String arg0)
		{

			return false;
		}

		public Principal getUserPrincipal()
		{

			return null;
		}

		public String getRequestedSessionId()
		{

			return null;
		}

		public String getRequestURI()
		{

			return null;
		}

		public StringBuffer getRequestURL()
		{

			return null;
		}

		public String getServletPath()
		{
			return theServletPath;
		}

		public HttpSession getSession(boolean arg0)
		{

			return null;
		}

		public HttpSession getSession()
		{

			return null;
		}

		public boolean isRequestedSessionIdValid()
		{

			return false;
		}

		public boolean isRequestedSessionIdFromCookie()
		{

			return false;
		}

		public boolean isRequestedSessionIdFromURL()
		{

			return false;
		}

		public boolean isRequestedSessionIdFromUrl()
		{

			return false;
		}

		public Object getAttribute(String arg0)
		{

			return null;
		}

		public Enumeration getAttributeNames()
		{

			return null;
		}

		public String getCharacterEncoding()
		{

			return null;
		}

		public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException
		{

		}

		public int getContentLength()
		{

			return 0;
		}

		public String getContentType()
		{

			return null;
		}

		public ServletInputStream getInputStream() throws IOException
		{

			return null;
		}

		public String getParameter(String arg0)
		{

			return null;
		}

		public Enumeration getParameterNames()
		{
			return new Enumeration()
			{
				public boolean hasMoreElements()
				{
					return false;
				}

				public Object nextElement()
				{
					return null;
				}
			};
		}

		public String[] getParameterValues(String arg0)
		{

			return null;
		}

		public Map getParameterMap()
		{
			return theParameters;
		}

		public String getProtocol()
		{

			return null;
		}

		public String getScheme()
		{

			return null;
		}

		public String getServerName()
		{

			return null;
		}

		public int getServerPort()
		{

			return 0;
		}

		public BufferedReader getReader() throws IOException
		{

			return null;
		}

		public String getRemoteAddr()
		{

			return null;
		}

		public String getRemoteHost()
		{

			return null;
		}

		public void setAttribute(String arg0, Object arg1)
		{

		}

		public void removeAttribute(String arg0)
		{

		}

		public Locale getLocale()
		{

			return null;
		}

		public Enumeration getLocales()
		{

			return null;
		}

		public boolean isSecure()
		{

			return false;
		}

		public RequestDispatcher getRequestDispatcher(String arg0)
		{

			return null;
		}

		public String getRealPath(String arg0)
		{

			return null;
		}
	}

}
