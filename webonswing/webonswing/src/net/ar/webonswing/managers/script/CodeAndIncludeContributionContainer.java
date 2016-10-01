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

package net.ar.webonswing.managers.script;

import java.util.*;

/**
 * Maneja la inclusiones y codigo suelto de scritps y estilos, permitiendo variar en la subclases el formato
 * de las operaciones de inclusion. 
 * 
 * @author Fernando Damian Petrola
 */
public abstract class CodeAndIncludeContributionContainer
{
	protected Set theIncludes= new HashSet();
	protected StringBuffer theCode= new StringBuffer();
	protected Set uniqueCode= new HashSet();

	public void addInclude(String anIncludePath)
	{
		theIncludes.add(anIncludePath);
	}

	public void addCode(String aCode)
	{
		theCode.append(aCode);
	}

	public void addCodeOnce(String aCode)
	{
		if (!uniqueCode.contains(aCode))
		{
			theCode.append(aCode);
			uniqueCode.add(aCode);
		}
	}

	public String getIncludesCode()
	{
		StringBuffer theResult= new StringBuffer();

		for (Iterator i= theIncludes.iterator(); i.hasNext();)
			theResult.append(getIncludeOperation((String)i.next()));

		return theResult.toString();
	}

	protected abstract String getIncludeOperation(String anIncludedPath);

	public StringBuffer getCode()
	{
		return theCode;
	}

	public void setCode(StringBuffer aBuffer)
	{
		theCode= aBuffer;
	}
}