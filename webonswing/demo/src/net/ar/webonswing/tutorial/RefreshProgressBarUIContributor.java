package net.ar.webonswing.tutorial;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.ui.*;

public class RefreshProgressBarUIContributor extends ProgressBarUIContributor
{
	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
		JProgressBar aProgressBar= (JProgressBar)getJComponent();
		aPersistenceManager.persistValue(theComponent, new Integer(aProgressBar.getValue()));
	}

	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer aPersistenceManager)
	{
		JProgressBar aProgressBar= (JProgressBar)getJComponent();
		Integer value= (Integer)aPersistenceManager.restoreValue(theComponent);

		return value.intValue() == aProgressBar.getValue();
	}

	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
		Integer value= (Integer)aPersistenceManager.restoreValue(theComponent);
		JProgressBar aProgressBar= (JProgressBar)getJComponent();
		aProgressBar.setValue(value.intValue());
	}
}