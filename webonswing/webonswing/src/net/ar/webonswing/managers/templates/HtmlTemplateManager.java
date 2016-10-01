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
import java.net.*;
import java.util.*;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;

public class HtmlTemplateManager implements TemplateManager, Serializable
{
	protected boolean templateCacheActive= true;
	protected Hashtable templateAliases;
	protected Vector resourcesPaths;

	public HtmlTemplateManager()
	{
		templateAliases= new Hashtable();
		resourcesPaths= new Vector();
	}

	public HtmlTemplateManager(Hashtable aTemplateTable)
	{
		templateAliases= aTemplateTable;
		resourcesPaths= new Vector();
	}

	public HtmlTemplateManager(Vector aVector)
	{
		for (Iterator i= aVector.iterator(); i.hasNext();)
		{
			HtmlTemplateManagerEntry theEntry= (HtmlTemplateManagerEntry) i.next();
			templateAliases.put(theEntry, theEntry);
		}
	}

	/**
	 * Obtiene una plantilla a partir de un nombre que puede llegar a ser una ruta completa para alcanzar una subplantilla
	 * ej:
	 * 		"WinMenu.JMenuBar.MenuItem"
	 * 	
	 */
	public Template getTemplateForName(String aTemplateName)
	{
		try
		{
			HtmlTemplate theTemplate;
			String theTemplatePath;
			theTemplatePath= "";
			int theTokenEndPosition= aTemplateName.indexOf('.');

			if (theTokenEndPosition != -1)
			{
				theTemplatePath= aTemplateName.substring(theTokenEndPosition + 1);
				aTemplateName= aTemplateName.substring(0, theTokenEndPosition);
			}

			theTemplate= getTemplate(aTemplateName);

			if (theTemplate == null)
				throw new TemplateNotFoundException("Cannot find the template '" + aTemplateName + "'");

			HtmlTemplate theSubTemplate= getSubTemplate((HtmlTemplate) theTemplate.clone(), theTemplatePath);

			if (theSubTemplate == null)
				throw new TemplateNotFoundException("Cannot find the subtemplate '" + theTemplatePath + "' in the template: \n" + theTemplate + "\n\n");

			return theSubTemplate;
		}
		catch (CloneNotSupportedException e)
		{
			throw new WebOnSwingException("Cannot clone template '" + aTemplateName + "'", e);
		}
	}

	private HtmlTemplate getTemplate(String aTemplateName)
	{
		HtmlTemplateManagerEntry theEntry= (HtmlTemplateManagerEntry) templateAliases.get(aTemplateName);

		if (theEntry != null)
		{
			HtmlTemplate theTemplate= (HtmlTemplate) theEntry.getTemplate();

			if (theTemplate == null || !isTemplateCacheActive())
				theEntry.setTemplate(theTemplate= HtmlTemplateFactory.createTemplateFromResource(theEntry.getResourcePath()));

			return theTemplate;
		}
		else
		{
			for (Iterator i= resourcesPaths.iterator(); i.hasNext();)
			{
				String thePath= (String) i.next();

				String theResourceName= thePath + "/" + aTemplateName + ".html";
				URL theResourceURL= getClass().getResource(theResourceName);

				if (theResourceURL != null)
				{
					HtmlTemplate theTemplate= HtmlTemplateFactory.createTemplateFromResource(theResourceName);
					HtmlTemplateManagerEntry theNewEntry= new HtmlTemplateManagerEntry(aTemplateName, theResourceName, theTemplate);
					templateAliases.put(theNewEntry, theNewEntry);
					return theTemplate;
				}
			}
		}

		return null;
	}

	/**
	 * Va recorriendo los elementos de la plantilla dada recursivamente hasta llegar al fin de la ruta especificada 
	 * 
	 * @param aTemplate
	 * @param aPath
	 * @return
	 */
	public static HtmlTemplate getSubTemplate(Template aTemplate, String aPath)
	{
		return (HtmlTemplate) new TemplateFinder(aTemplate).getTemplate(aPath);
	}

	/**
	 * Idem anterior pero devuelve un clone del encontrado.
	 * 
	 * @param aTemplate
	 * @param aPath
	 * @return
	 */
	public static HtmlTemplate getClonedSubTemplate(Template aTemplate, String aPath)
	{
		try
		{
			DefaultTemplate theFoundTemplate= getSubTemplate(aTemplate, aPath);

			if (theFoundTemplate == null)
				throw new TemplateNotFoundException("Cannot find sub template for the path '" + aPath + "' in the template: \n" + aTemplate + "\n\n");

			return (HtmlTemplate) theFoundTemplate.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new WebOnSwingException("Cannot clone template '" + aTemplate + "'", e);
		}
	}

	/**
	 * Acelerador para generar KeyPositionTemplates en una ventana de Swing
	 * 
	 */
	public KeyPositionTemplate getKeyPositionTemplateForName(String aTemplateName)
	{
		return new KeyPositionHtmlTemplate((HtmlTemplate) getTemplateForName(aTemplateName));
	}

	/**
	 * Idem anterior pero encontrando previamente una subplantilla de otra 
	 */
	public KeyPositionTemplate getKeyPositionTemplateForSubTemplate(Template aTemplate, String aPath)
	{
		return new KeyPositionHtmlTemplate(getClonedSubTemplate(aTemplate, aPath));
	}

	public Hashtable getTemplateTable()
	{
		return templateAliases;
	}

	public void setTemplateTable(Hashtable aTable)
	{
		templateAliases= aTable;
	}

	public Vector getTemplateSpecification()
	{
		Vector theResult= new Vector();

		for (Iterator i= templateAliases.entrySet().iterator(); i.hasNext();)
		{
			Map.Entry theMapEntry= (Map.Entry) i.next();
			theResult.add(new HtmlTemplateManagerEntry(theMapEntry.getKey().toString(), theMapEntry.getValue().toString(), null));
		}

		return theResult;
	}
	public Vector getResources_path()
	{
		return resourcesPaths;
	}
	public void setResources_path(Vector resources_path)
	{
		this.resourcesPaths= resources_path;
	}
	public Hashtable getTemplates()
	{
		return templateAliases;
	}
	public void setTemplates(Hashtable templates)
	{
		this.templateAliases= templates;
	}
	
	public boolean isTemplateCacheActive()
	{
		return templateCacheActive;
	}

	public void setTemplateCacheActive(boolean aB)
	{
		templateCacheActive= aB;
	}
}
