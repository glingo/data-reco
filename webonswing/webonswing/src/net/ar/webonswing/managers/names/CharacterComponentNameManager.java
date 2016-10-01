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

package net.ar.webonswing.managers.names;

import java.text.*;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.wrapping.*;

import org.apache.commons.lang.*;

/**
 * 
 * Nombra los componentes de la siguiente forma: si el numero de rama es menor
 * o igual a 52 entonces usa una letra para identificarla, y si es mayor usar
 * un numero con el siguiente formato "XXX". Y luego le concatena un "_"
 * seguido del nombre del componente con el numero de rama.
 * 
 * ej: aabac_067eb.JButton02
 * 
 * @author Fernando Damian Petrola
 */
public class CharacterComponentNameManager extends ComponentVisitorAdapter implements ComponentNameManager
{
	public static final String UNIQUE_NAME_PROPERTY= "theUniqueName";
	public static final String USER_UNIQUE_NAME_PROPERTY= "theUserUniqueName";

	int theCurrentChildNumber;
	protected static final char SEPARATOR= '_';
	protected DecimalFormat digitsFormat= new DecimalFormat("#000");

	public VisualComponent findComponentWithNameIn(String aComponentName, VisualComponent aRootComponent)
	{
		try
		{
			VisualComponent theContainer= null;

			theContainer= aRootComponent;
			String[] theParts= StringUtils.split(aComponentName, SEPARATOR + "");

			if (theParts.length < 2)
				return null;

			boolean isValidName= true;

			for (int i= 1; i < theParts[0].length() && isValidName; i++)
			{
				int theComponentNumber;
				char theChar= theParts[0].charAt(i);

				if (Character.isDigit(theChar))
				{
					theComponentNumber= digitsFormat.parse(theParts[0].substring(i, i + 4)).intValue();
					i += 3;
				}
				else
					theComponentNumber= CharacterConverter.charToPosition[theChar];

				isValidName= theComponentNumber < theContainer.getChildCount();
				if (isValidName)
					theContainer= theContainer.getChildAt(theComponentNumber);
			}

			return theContainer;
		}
		catch (ParseException e)
		{
			throw new WebOnSwingException(e);
		}
	}

	public void assignNamesFrom(VisualComponent aComponent)
	{
		theCurrentChildNumber= 0;
		aComponent.accept(this);
	}

	public void visitComponentBegin(VisualComponent aComponent)
	{
		String theHead= pathToString(aComponent.getParent() != null && !(aComponent.getParent() instanceof VisualWindow) ? aComponent.getParent().getName().substring(0, aComponent.getParent().getName().indexOf(SEPARATOR)) : "");

		if (aComponent.getName() == null || aComponent.getName().length() == 0)
			aComponent.setName(theHead + SEPARATOR + getTail(aComponent));

		theCurrentChildNumber= 0;
	}

	public void visitComponentEnd(VisualComponent aComponent)
	{
		try
		{
			int separatorIndex= aComponent.getName().indexOf(SEPARATOR);
			char lastChar= aComponent.getName().charAt(separatorIndex - 1);
			
			if (Character.isDigit(lastChar))
				theCurrentChildNumber= digitsFormat.parse(aComponent.getName().substring(separatorIndex - 3, separatorIndex)).intValue() + 1;
			else
				theCurrentChildNumber= CharacterConverter.charToPosition[lastChar] + 1;
		}
		catch (ParseException e)
		{
			throw new WebOnSwingException(e);
		}
	}

	public String getTail(VisualComponent aComponent)
	{
		String theTail= (String) aComponent.getClientProperty(USER_UNIQUE_NAME_PROPERTY);

		if (theTail == null)
			theTail= aComponent.getTypeId().substring(aComponent.getTypeId().lastIndexOf('.') + 1) + theCurrentChildNumber;

		return theTail;
	}

	protected String pathToString(String aParentPath)
	{
		String theResult= "";

		if (theCurrentChildNumber < CharacterConverter.positionToChar.length)
			theResult= aParentPath + CharacterConverter.positionToChar[theCurrentChildNumber];
		else
			theResult= aParentPath + digitsFormat.format(theCurrentChildNumber);

		return theResult;
	}

	public static class CharacterConverter
	{
		static public char[] positionToChar= { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		static public int[] charToPosition= new CharacterConverter().getIntMapping();

		public int[] getIntMapping()
		{
			int[] theIntMap= new int[300];

			for (int i= 0; i < positionToChar.length; i++)
				theIntMap[positionToChar[i]]= i;

			return theIntMap;
		}
	}
}