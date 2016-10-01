
package examples;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.ui.*;

public class LabelPersistenceContributor extends LabelUIContributor
{
	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
		JLabel label= (JLabel) getJComponent();
		aPersistenceManager.persistValue(theComponent, new Object[]{label.getText()});
	}
	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer aPersistenceManager)
	{
		return false;
	}
}