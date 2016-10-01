
package examples;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.ui.*;

public class TextAreaPersistenceContributor extends TextAreaUIContributor
{
	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
		JTextArea textArea= (JTextArea) getJComponent();
		aPersistenceManager.persistValue(theComponent, textArea.getText());
	}
	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer aPersistenceManager)
	{
		JTextArea textArea= (JTextArea) getJComponent();
		return false;//textArea.getText().equals(aPersistenceManager.restoreValue(theComponent));
	}
	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
		JTextArea textArea= (JTextArea) getJComponent();
		textArea.setText((String) aPersistenceManager.restoreValue(theComponent));
	}
}