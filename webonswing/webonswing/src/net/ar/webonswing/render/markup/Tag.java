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

import java.util.*;

import net.ar.webonswing.visitor.*;

import org.apache.commons.logging.*;

public class Tag extends MarkupElement
{
	String theName;
	Vector theAttributes;
	MarkupContainer theMarkupContainer;
	boolean needsClosure;

	public Tag(String aName)
	{
		theName= aName;
		theAttributes= new Vector();
		theMarkupContainer= new MarkupContainer();
		needsClosure= true;
	}

	public Tag addAttribute(TagAttribute aAttribute)
	{
		theAttributes.add(aAttribute);
		return this;
	}

	public Tag addAttribute(String aKey, String aValue)
	{
		addAttribute(new TagAttribute(aKey, aValue));
		return this;
	}

	public Tag addAttribute(String aKey, int aValue)
	{
		addAttribute(new TagAttribute(aKey, aValue));
		return this;
	}

	public void removeAttribute(String anAttributeName)
	{
		theAttributes.remove(new TagAttribute(anAttributeName, ""));
	}

	public Tag addElementToContainer(MarkupElement anElement)
	{
		getTheMarkupContainer().addElement(anElement);
		return this;
	}

	public Tag addTextToContainer(String aText)
	{
		getTheMarkupContainer().addElement(new TagContent(aText));
		return this;
	}

	public Tag addTextToContainer(StringBuffer aText)
	{
		addTextToContainer(aText.toString());
		return this;
	}

	public boolean isNeedsClosure()
	{
		return needsClosure;
	}

	public Tag setNeedsClosure(boolean isNeedsClosure)
	{
		this.needsClosure= isNeedsClosure;
		return this;
	}

	public List getTheAttributes()
	{
		return theAttributes;
	}

	public String getAttributeValueForKey(String anAttributeKey)
	{
		for (Iterator i= theAttributes.iterator(); i.hasNext();)
		{
			TagAttribute theAttribute= (TagAttribute) i.next();
			if (theAttribute.getKey().equals(anAttributeKey))
				return theAttribute.getValue();
		}

		return null;
	}

	public String getTheName()
	{
		return theName;
	}

	public void setTheAttributes(Vector anAttributes)
	{
		this.theAttributes= anAttributes;
	}

	public void setTheName(String aName)
	{
		this.theName= aName;
	}

	public MarkupContainer getTheMarkupContainer()
	{
		return theMarkupContainer;
	}

	public void setTheMarkupContainer(MarkupContainer aMarkupContainer)
	{
		this.theMarkupContainer= aMarkupContainer;
	}

	public Tag addNewTagAttributes(Tag aTag)
	{
		for (Iterator i= aTag.getTheAttributes().iterator(); i.hasNext();)
		{
			TagAttribute theAttribute= (TagAttribute) i.next();

			if (!getTheAttributes().contains(theAttribute))
				addAttribute(theAttribute);
		}

		return this;
	}

	public Tag copyNewTagContainer(Tag aTag)
	{
		try
		{
			setTheMarkupContainer((MarkupContainer) aTag.getTheMarkupContainer().clone());
		}
		catch (CloneNotSupportedException e)
		{
			LogFactory.getLog(Tag.class).error("", e);
		}

		return this;
	}

	public Tag addNewTagAttributesAndName(Tag aTag)
	{
		addNewTagAttributes(aTag);
		theName= aTag.getTheName();
		return this;
	}

	public Tag addAllTagAttributes(Tag aTag)
	{
		for (Iterator i= aTag.getTheAttributes().iterator(); i.hasNext();)
		{
			TagAttribute theAttribute= (TagAttribute) i.next();

			if (getTheAttributes().contains(theAttribute))
				getTheAttributes().remove(theAttribute);

			addAttribute(theAttribute);
		}

		return this;
	}

	public Tag addAllTagAttributesAndName(Tag aTag)
	{
		addAllTagAttributes(aTag);
		theName= aTag.getTheName();
		return this;
	}

	public void accept(Visitor aMarkupVisitor)
	{
		MarkupVisitor theMarkupVisitor= (MarkupVisitor) aMarkupVisitor;

		theMarkupVisitor.visitTagBegin(this);

		for (Iterator i= getTheAttributes().iterator(); i.hasNext();)
			 ((TagAttribute) i.next()).accept(aMarkupVisitor);

		theMarkupVisitor.visitAllTagAttributesEnd(this);
		getTheMarkupContainer().accept(aMarkupVisitor);
		theMarkupVisitor.visitTagEnd(this);
	}

	public Object clone() throws CloneNotSupportedException
	{
		Tag theTag= (Tag) super.clone();

		theTag.theAttributes= new Vector();
		for (Iterator i= theAttributes.iterator(); i.hasNext();)
			theTag.theAttributes.add(i.next());

		theTag.theMarkupContainer= (MarkupContainer) theMarkupContainer.clone();
		theTag.theName= theName;
		theTag.needsClosure= needsClosure;

		return theTag;
	}

}
