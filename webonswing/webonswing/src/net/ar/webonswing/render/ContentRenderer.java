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

package net.ar.webonswing.render;

import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.visitor.*;

public class ContentRenderer implements TemplateVisitor, MarkupVisitor, TextContentVisitor
{
	StringBuffer theResult= new StringBuffer();
	TextMarkupRenderer theMarkupRenderer= new TextMarkupRenderer(theResult, false);
	TextTemplateRenderer theTemplateRenderer= new TextTemplateRenderer(theResult);

	public ContentRenderer (Visitable aVisitable)
	{
		render(aVisitable);
	}

	public void visitTemplate(Template aTemplate)
	{
		theTemplateRenderer.visitTemplate(aTemplate);
	}

	public void visitTextContent(TextContent aTextContent)
	{
		theResult.append(aTextContent.getContent());
	}

	public void visit(Visitable aVisitable)
	{
		aVisitable.accept(this);
	}

	public StringBuffer render(Visitable aVisitable)
	{
		visit(aVisitable);
		return theResult;
	}

	public String getResult()
	{
		return theResult.toString();
	}

	public void setResult(StringBuffer aBuffer)
	{
		theResult= aBuffer;
	}
	
	public void visitAttribute(TagAttribute anAttribute)
	{
		theMarkupRenderer.visitAttribute(anAttribute);
	}

	public void visitMarkupContainerBegin(MarkupContainer anAttribute)
	{
		theMarkupRenderer.visitMarkupContainerBegin(anAttribute);
	}

	public void visitMarkupContainerEnd(MarkupContainer anAttribute)
	{
		theMarkupRenderer.visitMarkupContainerEnd(anAttribute);
	}

	public void visitTagBegin(Tag aTag)
	{
		theMarkupRenderer.visitTagBegin(aTag);
	}

	public void visitTagContent(TagContent aContent)
	{
		theMarkupRenderer.visitTagContent(aContent);
	}

	public void visitTagEnd(Tag aTag)
	{
		theMarkupRenderer.visitTagEnd(aTag);
	}

	public void tagVisit(Tag aTag)
	{
		theMarkupRenderer.tagVisit(aTag);
	}

	public void visitAllTagAttributesEnd(Tag tag)
	{
		theMarkupRenderer.visitAllTagAttributesEnd(tag);
	}
}