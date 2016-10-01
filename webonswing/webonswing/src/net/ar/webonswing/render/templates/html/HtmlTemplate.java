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

package net.ar.webonswing.render.templates.html;

import java.util.*;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.visitor.*;

import org.apache.commons.logging.*;
import org.apache.regexp.*;

/**
 * Plantilla de html que parsea un archivo creando recursivamente otros
 * HtmlTemplate cuando encuentra algun tag con el atributo "name". Quedando
 * entonces el tag encontrado como "theFoundTag" en la plantilla y el cuerpo se
 * separa en los fragmentos de texto que correspondan.
 * 
 * @author Fernando Damian Petrola
 */
public class HtmlTemplate extends DefaultTemplate
{
	protected StringBuffer theHtmlData;
	protected Tag theFoundTag;
	protected int theNumberOfBlocks;

	public HtmlTemplate()
	{
		super(null);
	}

	public HtmlTemplate(StringBuffer aBuffer, Tag aFoundTag)
	{
		super(new HtmlTemplateBody());
		theFoundTag= aFoundTag;
		theNumberOfBlocks= 0;
		theHtmlData= new StringBuffer(aBuffer.toString());
		generateSubTemplates();
		getHtmlTemplateBody().setTemplate(this);
	}

	public void generateSubTemplates()
	{
		while (theHtmlData.length() > 0)
			getNextHtmlTemplateElement(theHtmlData);
	}

	public HtmlTemplate(StringBuffer aBuffer)
	{
		this(aBuffer, null);
	}

	public HtmlTemplate(String aBuffer)
	{
		this(new StringBuffer(aBuffer));
	}

	public HtmlTemplateBody getHtmlTemplateBody()
	{
		return (HtmlTemplateBody) theBody;
	}

	public void addElement(TemplateElement anElement)
	{
		if (anElement != null)
		{
			TemplateElement theElement= getElementForId(anElement.getId());

			if (theElement != null)
			{
				theElement.setContent(anElement.getContent());

				if (theElement instanceof IdTagTemplateElement && anElement instanceof IdTagTemplateElement)
				{
					IdTagTemplateElement theIdTagElement= (IdTagTemplateElement) theElement;

					Tag theTag= ((IdTagTemplateElement) anElement).getTheIdTag();
					theIdTagElement.getTheIdTag().removeAttribute("name");

					if (theTag != null)
						theTag.addAllTagAttributes(theIdTagElement.getTheIdTag());
				}
			}
		}
	}

	public Tag getFoundTag()
	{
		return theFoundTag;
	}

	public void setFoundTag(Tag aTag)
	{
		theFoundTag= aTag;
	}

	public void mergeFoundTag(Tag aTag)
	{
		if (theFoundTag != null)
		{
			theFoundTag.removeAttribute("name");
			theFoundTag.addNewTagAttributesAndName(aTag);
			theFoundTag.copyNewTagContainer(aTag);
			theFoundTag.setNeedsClosure(aTag.isNeedsClosure());
		}
	}

	public void mergeFoundTagKeepingName(Tag aTag)
	{
		if (theFoundTag != null)
		{
			theFoundTag.removeAttribute("name");
			theFoundTag.addNewTagAttributes(aTag);
			theFoundTag.copyNewTagContainer(aTag);
			theFoundTag.setNeedsClosure(aTag.isNeedsClosure());
		}
	}

	public void deleteFoundTag()
	{
		theFoundTag= null;
	}

	public Object clone() throws CloneNotSupportedException
	{
		HtmlTemplate theHtmlTemplate= (HtmlTemplate) super.clone();

		if (theFoundTag != null)
			theHtmlTemplate.theFoundTag= (Tag) theFoundTag.clone();

		theHtmlTemplate.theNumberOfBlocks= theNumberOfBlocks;
		theHtmlTemplate.getHtmlTemplateBody().setTemplate(theHtmlTemplate);

		return theHtmlTemplate;
	}

	public void removeElement(String anElementId)
	{
		addElement(new IdTagTemplateElement(anElementId, new TextContent("")));
	}

	public String renderToText()
	{
		return new TextTemplateRenderer(this).getResult().toString();
	}

	public HtmlTemplate addIdTagTemplateElement(Object anId, Visitable aContent, Tag aTag)
	{
		addElement(new IdTagTemplateElement(anId, aContent, aTag));
		return this;
	}

	public HtmlTemplate addIdTagTemplateElement(Object anId, Visitable aContent)
	{
		addElement(new IdTagTemplateElement(anId, aContent));
		return this;
	}

	public HtmlTemplate addIdTagTemplateElement(Object anId, String aContent)
	{
		addElement(new IdTagTemplateElement(anId, new TextContent(aContent)));
		return this;
	}

	public HtmlTemplate getSubTemplate(String aPath)
	{
		return HtmlTemplateManager.getSubTemplate(this, aPath);
	}

	public HtmlTemplate getClonedSubTemplate(String aPath)
	{
		return HtmlTemplateManager.getClonedSubTemplate(this, aPath);
	}

	public HtmlTemplate getClonedSubTemplateAt(int i)
	{
		try
		{
			TemplateElement theElement= getElementAt(i);

			return (HtmlTemplate) ((HtmlTemplate) theElement.getContent()).clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new WebOnSwingException(e);
		}
	}

	public TemplateElement getElementAt(int i)
	{
		return super.getElementAt(i * 2 + 1);
	}

	public int getElementCount()
	{
		return super.getElementCount() / 2;
	}

	public IdTagTemplateElement getNextHtmlTemplateElement(StringBuffer aBuffer)
	{
		try
		{
			Object[] theTagSpecification= getNextTagSpecification(aBuffer);

			if (theTagSpecification != null)
			{
				Tag foundTag= (Tag) theTagSpecification[0];
				String aKey= foundTag.getAttributeValueForKey("name");
				int theStart= ((Integer) theTagSpecification[1]).intValue();
				int theEnd= ((Integer) theTagSpecification[2]).intValue();

				if (!foundTag.isNeedsClosure())
					return createHtmlTemplateElement(aBuffer, aKey, foundTag, theStart, theEnd, theEnd, theEnd, theEnd);

				int theNextOpen= 0;
				RE reFindNextOpen= new RE("(<)");
				if (reFindNextOpen.match(aBuffer.toString(), theEnd))
					theNextOpen= reFindNextOpen.getParenStart(1);

				String theTagName= foundTag.getTheName();

				RE reFindOpenTag= new RE("(<" + theTagName + "[^<]*>)", RE.MATCH_CASEINDEPENDENT);
				RE reFindCloseTag= new RE("(</" + theTagName + "[^<]*>)", RE.MATCH_CASEINDEPENDENT);

				int theOpenTags= 1;
				boolean finalCloseFound= false;
				int thePosition= theEnd;

				while (thePosition <= aBuffer.length() && !finalCloseFound)
				{
					boolean openFound= reFindOpenTag.match(aBuffer.toString(), thePosition);
					while (openFound && aBuffer.charAt(reFindOpenTag.getParenEnd(1) - 2) == '/')
						openFound= reFindOpenTag.match(aBuffer.toString(), reFindOpenTag.getParenEnd(1));

					boolean closeFound= finalCloseFound= reFindCloseTag.match(aBuffer.toString(), thePosition);
					if (closeFound)
					{
						thePosition= reFindCloseTag.getParenEnd(1);
						theOpenTags--;
						if (openFound)
							if (reFindOpenTag.getParenStart(1) < reFindCloseTag.getParenStart(1))
							{
								thePosition= reFindOpenTag.getParenEnd(1);
								theOpenTags += 2;
							}

						finalCloseFound= theOpenTags == 0;
						if (finalCloseFound)
							return createHtmlTemplateElement(aBuffer, aKey, foundTag, theStart, theEnd, theNextOpen, reFindCloseTag.getParenStart(1), reFindCloseTag.getParenEnd(1));
					}
					else
					{
						throw new WebOnSwingException("Cannot find the close tag for block= \"" + aKey + "\" (" + theStart + ", " + theEnd + ")");
					}
				}
			}

			if (theTagSpecification == null)
			{
				super.addElement(new DefaultTemplateElement("block" + theNumberOfBlocks++, new TextContent(new StringBuffer(aBuffer.toString()))));
				aBuffer.setLength(0);
			}
		}
		catch (RESyntaxException e)
		{
			LogFactory.getLog(HtmlTemplateBody.class).error("", e);
		}

		return null;
	}

	protected IdTagTemplateElement createHtmlTemplateElement(StringBuffer aBuffer, String aKey, Tag aFoundTag, int theOpenTagStart, int theOpenTagEnd, int theNextOpen, int theCloseTagStart, int theCloseTagEnd)
	{
		super.addElement(new DefaultTemplateElement("block" + theNumberOfBlocks++, new TextContent(new StringBuffer(aBuffer.substring(0, theOpenTagStart)))));

		IdTagTemplateElement theNewElement= null;

		if (!aKey.equals("delete-template"))
		{
			HtmlTemplate theNewHtmlTemplate= new HtmlTemplate(new StringBuffer(aBuffer.substring(theOpenTagEnd, theCloseTagStart)), aFoundTag);
			theNewElement= new IdTagTemplateElement(aKey, theNewHtmlTemplate, aFoundTag);
			super.addElement(theNewElement);
		}

		aBuffer.delete(0, theCloseTagEnd);

		return theNewElement;
	}

	public Object[] getNextTagSpecification(StringBuffer aBuffer) throws RESyntaxException
	{
		//TODO: optimizar con compiladas

		RE theIdTagRe= new RE("(<\\w*\\s*[^<]*\\s+name\\s*=\\s*\"[^\"]+\"[^<]*>)", RE.MATCH_CASEINDEPENDENT);
		RE theTagNameRe= new RE("<(\\w+)\\s*[^<]*>");
		RE theTagAttributeRe= new RE("\\s+(\\w+)\\s*=\\s*\"([^\"]*)\"\\s*");

		Tag theTag= null;
		int theStart= 0;
		int theEnd= 0;
		if (theIdTagRe.match(aBuffer.toString()))
		{
			theStart= theIdTagRe.getParenStart(1);
			theEnd= theIdTagRe.getParenEnd(1);
			String theTagString= aBuffer.substring(theStart, theEnd).toString();
			if (theTagNameRe.match(theTagString))
			{
				theTag= new Tag(theTagNameRe.getParen(1));
				int thePosition= 0;
				while (theTagAttributeRe.match(theTagString.toString(), thePosition))
				{
					theTag.addAttribute(new TagAttribute(theTagAttributeRe.getParen(1), theTagAttributeRe.getParen(2)));
					thePosition= theTagAttributeRe.getParenEnd(2);
				}
			}

			if (aBuffer.charAt(theEnd - 2) == '/')
				theTag.setNeedsClosure(false);
		}
		else
			return null;
		return new Object[] { theTag, new Integer(theStart), new Integer(theEnd)};
	}
}

class HtmlTemplateBody implements TemplateBody, Cloneable
{
	protected StringBuffer theReplacedBodyBuffer;
	protected HtmlTemplate theTemplate;

	public HtmlTemplateBody()
	{
		theReplacedBodyBuffer= new StringBuffer();
	}

	public void replace(List theElements)
	{
		try
		{
			theReplacedBodyBuffer= new StringBuffer();

			for (Iterator i= theElements.iterator(); i.hasNext();)
			{
				TemplateElement theElement= (TemplateElement) i.next();
				String theRendering= new ContentRenderer((theElement).getContent()).getResult();
				theReplacedBodyBuffer.append(theRendering);
			}

			if (theTemplate.getFoundTag() != null)
			{
				Tag theClonedTag= (Tag) theTemplate.getFoundTag().clone();

				theClonedTag.getTheMarkupContainer().addElement(new TagContent(theReplacedBodyBuffer));
				theReplacedBodyBuffer= new StringBuffer(new TextMarkupRenderer(theClonedTag).getResult().toString());
			}
		}
		catch (CloneNotSupportedException e)
		{
			throw new WebOnSwingException(e);
		}
	}

	public Object getResult()
	{
		return theReplacedBodyBuffer;
	}

	public Object clone() throws CloneNotSupportedException
	{
		HtmlTemplateBody theTemplateBody= (HtmlTemplateBody) super.clone();

		if (theReplacedBodyBuffer != null)
			theTemplateBody.theReplacedBodyBuffer= new StringBuffer(theReplacedBodyBuffer.toString());

		return theTemplateBody;
	}

	public HtmlTemplate getTemplate()
	{
		return theTemplate;
	}

	public void setTemplate(HtmlTemplate aTemplate)
	{
		theTemplate= aTemplate;
	}
}