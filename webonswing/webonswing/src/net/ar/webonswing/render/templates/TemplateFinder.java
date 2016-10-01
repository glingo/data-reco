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

package net.ar.webonswing.render.templates;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.visitor.*;

/**
 *Permite buscar una subplantilla dentro de otra mediante una ruta hacia ella.
 * 
 * @author Fernando Damian Petrola
 */
public class TemplateFinder implements TemplateVisitor
{
	protected String theTemplatePath;
	protected Template theTemplate;
	protected Template theFoundTemplate;

	public TemplateFinder(Template aTemplate)
	{
		theTemplate= aTemplate;
		theFoundTemplate= aTemplate;
	}

	public void visitTemplate(Template aTemplate)
	{
		if (!theTemplatePath.equals(""))
		{
			String theTemplateName= theTemplatePath;
			int theTokenEndPosition= theTemplatePath.indexOf('.');

			if (theTokenEndPosition == -1)
				theTemplatePath= "";
			else
			{
				theTemplateName= theTemplatePath.substring(0, theTokenEndPosition);
				theTemplatePath= theTemplatePath.substring(theTokenEndPosition + 1);
			}

			TemplateElement theFoundElement= ((DefaultTemplate) aTemplate).getElementForId(theTemplateName);
			if (theFoundElement == null)
			{
				theFoundTemplate= null;
				//LogFactory.getLog(TemplateFinder.class).info("Cannot find a template for '" + theTemplateName + "' id");
				return;
			}

			Visitable theContent= theFoundElement.getContent();

			if (!(theContent instanceof Template))
				throw new WebOnSwingException("The template element with id=\"" + theTemplateName + "\" is not a template");

			theFoundTemplate= (Template) theContent;
			theFoundTemplate.accept(this);
		}
	}

	public void visit(Visitable aVisitable)
	{
	}

	public Template getTemplate(String aTemplatePath)
	{
		theTemplatePath= aTemplatePath;
		theTemplate.accept(this);
		return theFoundTemplate;
	}
}
