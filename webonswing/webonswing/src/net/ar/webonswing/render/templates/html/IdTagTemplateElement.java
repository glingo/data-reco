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

package net.ar.webonswing.render.templates.html;

import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.visitor.*;

/**
 * Este tipo de elemento de plantilla permite asociarle a un contenido un tag que contenga 
 * ciertos atributos.Este tag va a ser mezclado con el tag que se encuentre en la plantilla, en 
 * el "hueco" correspondiente donde se pego el componente. 
 * 
 * @author Fernando Damian Petrola
 */
public class IdTagTemplateElement extends DefaultTemplateElement 
{
	Tag theIdTag;

	public IdTagTemplateElement(Object aId, Visitable aContent, Tag anIdTag)
	{
		super(aId, aContent);
		theIdTag= anIdTag;
	}

	public IdTagTemplateElement(Object aId, Tag anIdTag)
	{
		this(aId, anIdTag, anIdTag);
	}


	public IdTagTemplateElement(Object aId, Visitable aContent)
	{
		this(aId, aContent, null);
	}

	public Tag getTheIdTag()
	{
		return theIdTag;
	}

	public void setTheIdTag(Tag tag)
	{
		theIdTag= tag;
	}
	public Object clone() throws CloneNotSupportedException
	{
		IdTagTemplateElement theNewTemplateElement= (IdTagTemplateElement) super.clone();
		theNewTemplateElement.theIdTag= (Tag) theIdTag.clone();
		
		return theNewTemplateElement;
	}

}
