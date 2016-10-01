
package net.ar.webonswing.tutorial;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.ui.*;

public class LabelPersistenceContributor extends LabelUIContributor
{
	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
		JLabel label= (JLabel) getJComponent();
		aPersistenceManager.persistValue(theComponent, label.getText());
	}

	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
		JLabel label= (JLabel) getJComponent();
		label.setText((String) aPersistenceManager.restoreValue(theComponent));
	}
}