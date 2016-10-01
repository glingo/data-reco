
package examples;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.ui.*;

public class RadioButtonPersistenceContributor extends RadioButtonUIContributor
{
	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
		JRadioButton radioButton= (JRadioButton) getJComponent();
		aPersistenceManager.persistValue(theComponent, new Boolean(radioButton.isSelected()));
	}
	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer aPersistenceManager)
	{
		JRadioButton radioButton= (JRadioButton) getJComponent();
		return radioButton.isSelected() == ((Boolean)aPersistenceManager.restoreValue(theComponent)).booleanValue();
	}
	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
		JRadioButton radioButton= (JRadioButton) getJComponent();
		radioButton.setSelected(((Boolean) aPersistenceManager.restoreValue(theComponent)).booleanValue());
	}
}