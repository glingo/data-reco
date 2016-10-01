package net.ar.webonswing.tutorial;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.ui.*;

public class UpdateButtonUIContributor extends ButtonUIContributor
{
	public void doPersistenceContribution(PersistenceContributionContainer arg0)
	{
		JButton button= (JButton)getJComponent();
		arg0.persistValue(theComponent, button.getText());
	}

	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer aPersistenceManager)
	{
		JButton button= (JButton)getJComponent();
		return button.getText().equals(aPersistenceManager.restoreValue(theComponent).toString());
	}
}