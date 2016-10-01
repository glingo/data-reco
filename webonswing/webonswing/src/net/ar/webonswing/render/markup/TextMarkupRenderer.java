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

package net.ar.webonswing.render.markup;

import net.ar.webonswing.visitor.*;

public class TextMarkupRenderer implements MarkupVisitor
{
	StringBuffer theResult;
	boolean concatNewLine;

	public TextMarkupRenderer(MarkupElement anElement)
	{
		theResult= new StringBuffer();
		visit(anElement);
		concatNewLine=false;
	}

	public TextMarkupRenderer(MarkupElement anElement, boolean doConcatNewLine)
	{
		theResult= new StringBuffer();
		concatNewLine= doConcatNewLine;
		visit(anElement);
	}

	public TextMarkupRenderer(StringBuffer anExternalResult, boolean doConcatNewLine)
	{
		theResult= anExternalResult;
		concatNewLine= doConcatNewLine;
	}

	public void tagVisit(Tag aTag)
	{
	}

	public void visitTagContent(TagContent aContent)
	{
		theResult.append(aContent.getTheContent().toString());
	}

	public String getResult()
	{
		return theResult.toString();
	}

	public void setResult(StringBuffer aResult)
	{
		this.theResult= aResult;
	}

	public void visit(Visitable aRendereable)
	{
		if (aRendereable != null)
			aRendereable.accept(this);
	}

	public void visitTagBegin(Tag aTag)
	{
		theResult.append("<" + aTag.getTheName());
	}

	public void visitAttribute(TagAttribute anAttribute)
	{
		theResult.append(" ");
		theResult.append(anAttribute.getKey());

		if (anAttribute.getValue().length() > 0)
			theResult.append("=\"" + anAttribute.getValue() + "\"");
	}

	public void visitAllTagAttributesEnd(Tag tag)
	{
		theResult.append(">" + (concatNewLine ? "\n" : ""));
	}

	public void visitMarkupContainerBegin(MarkupContainer anAttribute)
	{
	}

	public void visitMarkupContainerEnd(MarkupContainer anAttribute)
	{
	}

	public void visitTagEnd(Tag aTag)
	{
		if (aTag.isNeedsClosure())
			theResult.append("</" + aTag.getTheName() + ">" + (concatNewLine ? "\n" : ""));
	}
}
