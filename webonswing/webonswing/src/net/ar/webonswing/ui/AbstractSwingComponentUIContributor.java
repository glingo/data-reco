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

package net.ar.webonswing.ui;

import javax.swing.*;

import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.managers.styles.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.walkers.*;
import net.ar.webonswing.wrapping.*;

public abstract class AbstractSwingComponentUIContributor extends AbstractComponentUIContributor implements ScriptContributor, StyleContributor, PersistenceContributor
{
	public void accept(Visitor aVisitor)
	{
		super.accept(aVisitor);
		((ContributorVisitor) aVisitor).visitScriptContributor(this);
		((ContributorVisitor) aVisitor).visitStyleContributor(this);
		((ContributorVisitor) aVisitor).visitPersistenceContributor(this);
	}

	public JComponent getJComponent()
	{
		return (JComponent) ((ComponentWrapper) theComponent).getWrappedComponent();
	}

	public VisitableContainer getContainerVisitable()
	{
		return new DefaultSwingVisitableContainer();
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
	}

	public void doStyleContribution(StyleContributionContainer aContributionManager)
	{
	}
	
	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
	}
	
	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer aPersistenceManager)
	{
		return false;
	}
	
	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
	}
}