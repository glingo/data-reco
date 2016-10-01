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

package examples;

import javax.swing.*;
import javax.swing.event.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.ui.*;

public class WebOnSwingDemo2Contributor extends ListUIContributor 
{
	public void doPersistenceContribution(PersistenceContributionContainer aContributionManager)
	{
		aContributionManager.persistValue(theComponent, String.valueOf(((JList) getJComponent()).getSelectedIndex()));
	}

	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
		JList theList= (JList) getJComponent();

		ListSelectionListener[] theListeners= (ListSelectionListener[]) theList.getListeners(ListSelectionListener.class);

		if (theListeners.length > 0)
		{
			theList.removeListSelectionListener(theListeners[0]);
			String aPersistedValue= (String) aPersistenceManager.restoreValue(theComponent);
			theList.setSelectedIndex(Integer.parseInt(aPersistedValue));
			theList.addListSelectionListener(theListeners[0]);
		}
	}

	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer unPersistenceManager)
	{
		return true;
	}
}