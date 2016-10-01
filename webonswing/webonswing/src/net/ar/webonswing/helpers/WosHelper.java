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

package net.ar.webonswing.helpers;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.http.*;

import net.ar.webonswing.*;
import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.managers.pages.*;
import net.ar.webonswing.managers.skins.*;
import net.ar.webonswing.managers.templates.*;

import org.apache.commons.lang.*;
import org.apache.commons.logging.*;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.alias.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.converters.collections.*;
import com.thoughtworks.xstream.core.*;
import com.thoughtworks.xstream.io.*;
import com.thoughtworks.xstream.io.xml.*;

public class WosHelper
{
	protected static XStream xstream;

	public static void copyStreams(final InputStream input, final OutputStream output, final int bufferSize) throws IOException
	{
		int n= 0;
		final byte[] buffer= new byte[bufferSize];
		while (-1 != (n= input.read(buffer)))
			output.write(buffer, 0, n);
	}

	public static String getFileNameFromResource(String aResource)
	{
		URL theFile= WosHelper.class.getResource(aResource);

		if (theFile == null)
			throw new WebOnSwingException("Cannot find the resource '" + aResource + "'");
		else
			return theFile.getFile();
	}

	public static Object restoreObjectFromXml(String anXmlResource)
	{
		InputStream resource= WosHelper.class.getResourceAsStream(anXmlResource);
		if (resource == null)
			throw new WebOnSwingException("Cannot find this resource: " + anXmlResource);

		return getXStream().fromXML(new InputStreamReader(resource));
	}

	public static XStream getXStream()
	{
		if (xstream == null)
		{
			xstream= new XStream(JVM.bestReflectionProvider(), new ConfigClassMapper(), new XppDriver());
			xstream.registerConverter(new ConfigMapConverter(xstream.getClassMapper(), ((DefaultConverterLookup) xstream.getConverterLookup()).getClassAttributeIdentifier()));

			xstream.alias("webonswing-framework", WosFramework.class);
			xstream.alias("web-page", HtmlPageManagerEntry.class);
			xstream.alias("page-manager", HtmlPageManager.class);
			xstream.alias("template", HtmlTemplateManagerEntry.class);
			xstream.alias("template-manager", HtmlTemplateManager.class);
			xstream.alias("contributor", ComponentContributorRelationship.class);
			xstream.alias("contributor-manager", ContributorManager.class);
			xstream.alias("skin", Skin.class);
			xstream.alias("skin-manager", SimpleSkinManager.class);
			xstream.alias("path", String.class);
		}

		return xstream;
	}

	public static void persistObjectToXml(Object anObject, String anXmlResource)
	{
		try
		{
			getXStream().toXML(anObject, new OutputStreamWriter(new FileOutputStream(WosHelper.class.getResource(anXmlResource).getFile())));
		}
		catch (Exception e)
		{
			LogFactory.getLog(WosFramework.class).error("Cannot persist the object", e);
		}
	}

	public static Object getObjectFromFile(InputStream aFile)
	{
		try
		{
			ObjectInputStream oos= new ObjectInputStream(aFile);
			return oos.readObject();
		}
		catch (Exception e)
		{
			throw new WebOnSwingException(e);
		}
	}

	public static void serializeObjectToFile(Object anObject, String aResource)
	{
		try
		{
			FileOutputStream out= new FileOutputStream(WosHelper.getFileNameFromResource(aResource));
			ObjectOutputStream oos= new ObjectOutputStream(out);
			oos.writeObject(anObject);
			oos.flush();
		}
		catch (Exception e)
		{
			LogFactory.getLog(WosFramework.class).error("Cannot serialize", e);
		}
	}

	public static Map RequestParametersToMap(HttpServletRequest aRequest)
	{
		Map theResult= new HashMap();

		Enumeration theParametersNames= aRequest.getParameterNames();

		while (theParametersNames.hasMoreElements())
		{
			String theComponentName= theParametersNames.nextElement().toString();
			String[] theValues= aRequest.getParameterValues(theComponentName);

			theResult.put(theComponentName, theValues);
		}

		return theResult;
	}

	public static InputStream getResourceAsStream(String aResource)
	{
		return WosHelper.class.getResourceAsStream(aResource);
	}

	public static StringBuffer getResourceAsStringBuffer(String aResourceName)
	{
		InputStream theInputStream= WosHelper.class.getResourceAsStream(aResourceName);
		BufferedReader resourceReader= new BufferedReader(new InputStreamReader(theInputStream));
		StringBuffer resourceBuffer= new StringBuffer();

		int i, charsRead= 0, size= 16384;
		char[] charArray= new char[size];

		try
		{
			while ((charsRead= resourceReader.read(charArray, 0, size)) != -1)
				resourceBuffer.append(charArray, 0, charsRead);

			while ((i= resourceReader.read()) != -1)
				resourceBuffer.append((char) i);

			resourceReader.close();

			return resourceBuffer;
		}
		catch (Exception e)
		{
			throw new WebOnSwingException("Cannot load the resource: " + aResourceName, e);
		}
	}

	public static String getNoPackageClassName(Object anObject)
	{
		return getNoPackageClassName(anObject.getClass());
	}

	public static String getNoPackageClassName(Class aClass)
	{
		String t= aClass.getName();
		return t.substring(t.lastIndexOf(".") + 1);
	}

	public static String exceptionToHtml(Exception e)
	{
		StringBuffer theResult= new StringBuffer();

		if (System.getProperty("java.version").charAt(2) > '3')
			theResult= new StringBuffer(exceptionToHtmlJDK14(e));
		else
		{
			theResult.append("<table style='font-family:verdana;font-size:13px;'>\n<tr>\n<td>\n");

			ByteArrayOutputStream theOutput= new ByteArrayOutputStream(10000);
			e.printStackTrace(new PrintStream(theOutput));
			String theStackText= StringUtils.replace(theOutput.toString(), "\n", "<br>");

			theResult.append("<br><span style='color:#FFFFFF;background-color:#EE0000'>Exception</span>:&nbsp;&nbsp;<span style='color: #BB0000'>" + escapeToWeb(theStackText) + "</span>\n");
			theResult.append("</td>\n</tr>\n</table>\n");
		}

		return theResult.toString();
	}

	public static String exceptionToHtmlJDK14(Throwable e)
	{
		StringBuffer theResult= new StringBuffer();
		theResult.append("<table style='font-family:verdana;font-size:13px;'>\n<tr>\n<td>\n");

		StringBuffer theStackResult= new StringBuffer();
		do
		{
			StackTraceElement[] theStackElements= e.getStackTrace();
			theStackResult.append("<br><span style='color:#FFFFFF;background-color:#EE0000'>Exception</span>:&nbsp;&nbsp;<span style='color: #BB0000'>" + escapeToWeb(e.toString()) + "</span>\n");
			theStackResult.append("<table border='0' style='font-family:verdana;font-size:13px;'>\n");

			for (int i= 0; i < theStackElements.length; i++)
			{
				theStackResult.append("<tr>\n");

				theStackResult.append("<td>\n");
				theStackResult.append("&nbsp;&nbsp;&nbsp;<span style='color: #005500'>" + theStackElements[i].getClassName() + "</span>.<span style='color: #00AA00;'>" + theStackElements[i].getMethodName() + "</span>");
				theStackResult.append("</td>\n");
				theStackResult.append("<td>\n");
				theStackResult.append("&nbsp;(in <span style='color:#0000BB'>" + theStackElements[i].getFileName() + "</span>&nbsp;line&nbsp;<span style='color:#0000FF'>" + theStackElements[i].getLineNumber() + "</span>)");
				theStackResult.append("</td>\n");

				theStackResult.append("</tr>\n");
			}

			theStackResult.append("</table>\n");

		}
		while ((e= e.getCause()) != null);

		theResult.append(theStackResult);
		theResult.append("</td>\n</tr>\n</table>\n");

		return theResult.toString();
	}

	public static String escapeToWeb(String aString)
	{
		return StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(aString, "<", "&lt;"), ">", "&gt;"), "\n", "<br>"), "\t", "&nbsp;&nbsp;&nbsp;");
	}

	public static Object createClassInstance(String aClassName)
	{
		try
		{
			return Class.forName(aClassName).newInstance();
		}

		catch (ClassNotFoundException e)
		{
			throw new WebOnSwingException("Cannot create an instance of '" + aClassName + "', class not found", e);
		}
		catch (InstantiationException e)
		{
			throw new WebOnSwingException("Cannot create an instance of '" + aClassName + "', trying to instantiate an interface	or an object that does not have a default constructor ", e);
		}
		catch (IllegalAccessException e)
		{
			throw new WebOnSwingException("Cannot create an instance of '" + aClassName + "', illegal access", e);
		}
	}

	public static class ConfigClassMapper extends DefaultClassMapper
	{
		public String mapNameFromXML(String xmlName)
		{
			if (nameToTypeMap.containsKey(xmlName))
				return xmlName;
			else
			{
				StringBuffer result= new StringBuffer();
				for (int i= 0; i < xmlName.length(); i++)
				{
					char character= xmlName.charAt(i);
					if (character == '-')
						character= Character.toUpperCase(xmlName.charAt(++i));
					result.append(character);
				}
				return result.toString();
			}
		}

		public String mapNameToXML(String javaName)
		{
			if (typeToNameMap.containsKey(javaName))
				return javaName;
			else
			{
				StringBuffer result= new StringBuffer();
				for (int i= 0; i < javaName.length(); i++)
				{
					char character= javaName.charAt(i);
					if (Character.isUpperCase(character))
						result.append("-");
					result.append(Character.toLowerCase(character));
				}

				return result.toString();
			}
		}
	}

	public static class ConfigMapConverter extends MapConverter
	{
		public ConfigMapConverter(ClassMapper arg0, String arg1)
		{
			super(arg0, arg1);
		}

		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
		{
			Map map= (Map) source;
			for (Iterator iterator= map.entrySet().iterator(); iterator.hasNext();)
			{
				Map.Entry entry= (Map.Entry) iterator.next();
				Object key= entry.getKey();
				Object value= entry.getValue();

				if (key == value)
					writeItem(value, context, writer);
				else
				{
					writer.startNode("entry");
					writeItem(key, context, writer);
					writeItem(value, context, writer);
					writer.endNode();
				}
			}
		}

		protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Map map)
		{
			while (reader.hasMoreChildren())
			{
				reader.moveDown();

				if (reader.getNodeName().equals("entry"))
				{
					reader.moveDown();
					Object key= readItem(reader, context, map);
					reader.moveUp();

					reader.moveDown();
					Object value= readItem(reader, context, map);
					reader.moveUp();

					map.put(key, value);
				}
				else
				{
					Object value= readItem(reader, context, map);
					map.put(value, value);
				}

				reader.moveUp();
			}
		}
	}
}