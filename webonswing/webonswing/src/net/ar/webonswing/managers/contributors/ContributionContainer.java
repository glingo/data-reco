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

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.managers.styles.*;
import net.ar.webonswing.ui.*;
import net.ar.webonswing.visitor.*;

/**
 * Sirve para visitar contribuciones y llamar la operacion especifica de cada tipo de contribuidor,
 * pasandole a cada uno el administrador correspondiente.
 * 
 * @author Fernando Damian Petrola
 */
public class ContributionContainer implements ContributorVisitor
{
	StyleContributionContainer theStylesContainer;
	ScriptContributionContainer theScriptsContainer;
	PersistenceContributionContainer thePersistenceContributionsContainer;

	public ContributionContainer(ScriptContributionContainer anScriptsContainer, StyleContributionContainer anStylesContainer, PersistenceContributionContainer aPersistenceDataContainer)
	{
		theScriptsContainer= anScriptsContainer;
		theStylesContainer= anStylesContainer;
		thePersistenceContributionsContainer= aPersistenceDataContainer;
	}

	public void visitScriptContributor(ScriptContributor aContributor)
	{
		aContributor.doScriptContribution(theScriptsContainer);
	}

	public void visitStyleContributor(StyleContributor aContributor)
	{
		aContributor.doStyleContribution(theStylesContainer);
	}

	public void visitPersistenceContributor(PersistenceContributor aContributor)
	{
		aContributor.doPersistenceContribution(thePersistenceContributionsContainer);
	}

	public void visitRenderingContributor(ComponentUIContributor aContributor)
	{
	}

	public void visit(Visitable aVisitable)
	{
	}

	public ScriptContributionContainer getScriptManager()
	{
		return theScriptsContainer;
	}

	public StyleContributionContainer getStyleManager()
	{
		return theStylesContainer;
	}

	public void setScriptManager(ScriptContributionContainer aManager)
	{
		theScriptsContainer= aManager;
	}

	public void setStyleManager(StyleContributionContainer aManager)
	{
		theStylesContainer= aManager;
	}

	public PersistenceContributionContainer getPersistenceContainer()
	{
		return thePersistenceContributionsContainer;
	}

	public void setPersistenceContainer(PersistenceContributionContainer aManager)
	{
		thePersistenceContributionsContainer= aManager;
	}
}
