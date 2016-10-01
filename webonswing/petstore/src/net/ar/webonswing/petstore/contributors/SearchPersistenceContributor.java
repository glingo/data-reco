package net.ar.webonswing.petstore.contributors;

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.petstore.*;
import net.ar.webonswing.ui.*;

public class SearchPersistenceContributor extends RootPaneUIContributor
{
	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
		Search searchWindow= (Search)((JRootPane)getJComponent()).getTopLevelAncestor();
		aPersistenceManager.persistValue(theComponent, new Object[] { searchWindow.getProducts(), searchWindow.getKeyword() });
	}

	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
		Search searchWindow= (Search)((JRootPane)getJComponent()).getTopLevelAncestor();

		Object[] values= (Object[])aPersistenceManager.restoreValue(theComponent);

		searchWindow.setProducts((List)values[0]);
		searchWindow.setKeyword((String)values[1]);
		searchWindow.init();
	}
}