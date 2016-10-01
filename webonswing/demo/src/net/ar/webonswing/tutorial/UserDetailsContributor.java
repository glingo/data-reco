package net.ar.webonswing.tutorial;

import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.ui.*;
import net.ar.webonswing.visitor.*;

public class UserDetailsContributor extends RootPaneUIContributor implements PersistenceContributor
{
	public void accept(Visitor aVisitor)
	{
		super.accept(aVisitor);

		((ContributorVisitor) aVisitor).visitPersistenceContributor(this);
	}

	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
		UserDetails theWindow= (UserDetails) getJComponent().getTopLevelAncestor();

		aPersistenceManager.persistValue(theComponent, new Integer(theWindow.getUser().getId()));
	}

	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
		UserDetails theWindow= (UserDetails) getJComponent().getTopLevelAncestor();

		Integer index= (Integer) aPersistenceManager.restoreValue(theComponent);
		theWindow.setUser(User.users[index.intValue()]);
	}

	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer aPersistenceManager)
	{
		return false;
	}
}
