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

package net.ar.webonswing.managers.templates;

import java.io.*;

import net.ar.webonswing.render.templates.*;

public class HtmlTemplateManagerEntry implements Serializable, Comparable
{
	protected String alias;
	protected String resourcePath;
	protected Template template;

	public HtmlTemplateManagerEntry()
	{
	}
	
	public HtmlTemplateManagerEntry(String aTemplateName)
	{
		alias= aTemplateName;
	}

	public HtmlTemplateManagerEntry(String aTemplateName, String aResourcePath, Template aTemplate)
	{
		alias= aTemplateName;
		resourcePath= aResourcePath;
		template= aTemplate;
	}

	public String getResourcePath()
	{
		return resourcePath;
	}

	public String getTemplateName()
	{
		return alias;
	}

	public void setResourcePath(String unString)
	{
		resourcePath= unString;
	}

	public void setTemplateName(String unString)
	{
		alias= unString;
	}

	public Template getTemplate()
	{
		return template;
	}

	public synchronized void setTemplate(Template unTemplate)
	{
		template= unTemplate;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof HtmlTemplateManagerEntry)
			return getTemplateName().equals(((HtmlTemplateManagerEntry) obj).getTemplateName());
		else
			return getTemplateName().equals(obj.toString());
	}

	public int hashCode()
	{
		return getTemplateName().hashCode();
	}

	public int compareTo(Object o)
	{
		if (o instanceof HtmlTemplateManagerEntry)
			return getTemplateName().compareTo(((HtmlTemplateManagerEntry) o).getTemplateName());
		else
			return getTemplateName().compareTo(o.toString());
	}

}
