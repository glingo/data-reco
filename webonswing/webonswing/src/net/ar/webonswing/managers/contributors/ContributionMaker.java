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

package net.ar.webonswing.managers.contributors;

import net.ar.webonswing.wrapping.*;

/**
 * Visita cada componente de una ventana, busca por cada uno el contribuidor que le corresponda y le pide 
 * que acepte la visita de un administrador de contribuciones para que realice las mismas.
 *  
 * @author Fernando Damian Petrola
 */
public class ContributionMaker extends ComponentVisitorAdapter
{
	ContributionContainer theContributionContainer;

	public ContributionMaker(ContributionContainer aContributionContainer)
	{
		theContributionContainer= aContributionContainer;
	}

	public void getContributionsFrom(VisualComponent aComponent)
	{
		aComponent.accept(this);
	}

	public void visitComponentBegin(VisualComponent aComponent)
	{
		ComponentContributor theContributor= aComponent.getContributor();
		theContributor.setComponent(aComponent);
		theContributor.accept(theContributionContainer);
	}
}
