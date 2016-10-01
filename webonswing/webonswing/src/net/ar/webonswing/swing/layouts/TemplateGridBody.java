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

package net.ar.webonswing.swing.layouts;

import java.util.*;

import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.visitor.*;

import org.apache.commons.logging.*;

public class TemplateGridBody implements TemplateBody
{
	public TemplateGridBody(int aRows, int aCols, Template aFirstTemplate)
	{
		theFirstTemplate= aFirstTemplate;
		theSecondTemplate= aFirstTemplate;
		theRows= aRows;
		theCols= aCols;
	}

	protected StringBuffer theReplacedBodyString= new StringBuffer();
	protected Template theFirstTemplate;
	protected Template theSecondTemplate;
	protected int theRows;
	protected int theCols;

	public TemplateGridBody(int aRows, int aCols, Template aFirstTemplate, Template aSecondTemplate)
	{
		theFirstTemplate= aFirstTemplate;
		theSecondTemplate= aSecondTemplate;
		theRows= aRows;
		theCols= aCols;
	}

	public void replace(List theElements)
	{
		try
		{
			int theRowCount= 0;
			int theColCount= 0;
			StringBuffer theRowContent= new StringBuffer();
			theReplacedBodyString= new StringBuffer();
			Template theTemplate= (Template) ((PublicCloneable) theFirstTemplate).clone();

			for (Iterator i= theElements.iterator(); i.hasNext();)
			{
				theRowContent.append(new ContentRenderer(((DefaultTemplateElement) i.next()).getContent()).getResult());

				if (theColCount == theCols - 1 || !i.hasNext())
				{
					theTemplate.addElement(new IdTagTemplateElement("theContent", new Tag ("span").addElementToContainer(new TagContent(theRowContent))));
					theReplacedBodyString.append(new TextTemplateRenderer(theTemplate).getResult());
					theRowContent= new StringBuffer();
					theColCount= 0;
					theRowCount++;

					if ((theRowCount % 2) == 1)
						theTemplate= (Template) ((PublicCloneable) theSecondTemplate).clone();
					else
						theTemplate= (Template) ((PublicCloneable) theFirstTemplate).clone();
				}
				else
					theColCount++;
			}
		}
		catch (CloneNotSupportedException e)
		{
			LogFactory.getLog(TemplateGridBody.class).error("", e);
		}
	}

	public Object getResult()
	{
		return theReplacedBodyString;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}