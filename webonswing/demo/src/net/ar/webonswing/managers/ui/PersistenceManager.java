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

package net.ar.webonswing.managers.ui;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import net.ar.webonswing.*;
import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.swing.layouts.*;

public class PersistenceManager extends JFrame
{
	JPanel theStatePanel;
	JTextField theContribClass;
	PersistenceStateManager theWindowPersistenceManager= new PersistenceStateManager();
	ContributorManager theContribManager= new ContributorManager();

	public PersistenceManager()
	{
		PersistenceManagerComponent thePersistenceComponent= new PersistenceManagerComponent();

		theStatePanel= new JPanel(new TemplateFlowLayout());

		ButtonGroup theButtonGroup= new ButtonGroup();
		JRadioButton theRadio1= new JRadioButton("persist in page");
		JRadioButton theRadio2= new JRadioButton("persist in session");
		JRadioButton theRadio3= new JRadioButton("persist in db");
		theRadio1.setLayout(WosFramework.getTemplateLayoutForName("PersistenceManager.theTable.theComponentState.theRadio"));
		theRadio2.setLayout(WosFramework.getTemplateLayoutForName("PersistenceManager.theTable.theComponentState.theRadio"));
		theRadio3.setLayout(WosFramework.getTemplateLayoutForName("PersistenceManager.theTable.theComponentState.theRadio"));
		theButtonGroup.add(theRadio1);
		theButtonGroup.add(theRadio2);
		theButtonGroup.add(theRadio3);

		theStatePanel.add(theRadio1);
		theStatePanel.add(theRadio2);
		theStatePanel.add(theRadio3);

		theContribClass= new JTextField();
		theContribClass.setLayout(WosFramework.getTemplateLayoutForName("PersistenceManager.theTable.theContribClass"));

		JPanel theMainStatePanel= new JPanel(WosFramework.getTemplateLayoutForName("PersistenceManager.theTable.theComponentState"));
		theMainStatePanel.add(theStatePanel, "theRadio");
		thePersistenceComponent.add(theContribClass, "theContribClass");

		thePersistenceComponent.add(theMainStatePanel, "theComponentState");
		setContentPane(new ManagementComponent(thePersistenceComponent, "Persistence/Contributor Manager"));
	}

	private void saveComponentState(TreePath theTreePath)
	{
		String theOldWindowName= theTreePath.getPathComponent(0).toString();
		String theOldComponentName= theTreePath.getLastPathComponent().toString();

		theContribManager.setContributorClassNameToTreeState(theOldWindowName, theOldComponentName, theContribClass.getText());

		for (int i= 0; i < 3; i++)
		{
			JRadioButton theButton= (JRadioButton) theStatePanel.getComponent(i);

			if (theButton.isSelected())
				theWindowPersistenceManager.setComponentPersistenceMethod(theOldWindowName, theOldComponentName, i);
		}
	}

	protected class MyTreeListener implements TreeSelectionListener, RemoteListener
	{
		public void valueChanged(TreeSelectionEvent e)
		{
			if (e.getOldLeadSelectionPath() != null)
				saveComponentState(e.getOldLeadSelectionPath());

			String theNewWindowName= e.getNewLeadSelectionPath().getPathComponent(0).toString();
			String theNewComponentName= e.getNewLeadSelectionPath().getLastPathComponent().toString();

			String theComponentContributorClassName= theContribManager.getContributorClassNameFromTreeState(theNewWindowName, theNewComponentName);
			
			if (theComponentContributorClassName != null)
				theContribClass.setText(theComponentContributorClassName);
			else
				theContribClass.setText("");

			JRadioButton theNewSelection= (JRadioButton) theStatePanel.getComponent(theWindowPersistenceManager.getPersistenceMethod(theNewWindowName, theNewComponentName));
			theNewSelection.setSelected(true);
		}

		public String getRemoteName()
		{
			return "PersistenceTreeSelectionListener";
		}

		public Object[] getRemoteParameters()
		{
			return new Object[0];
		}
	}

	protected class PersistenceManagerComponent extends TreeStateManagerPanel
	{
		public PersistenceManagerComponent()
		{
			super(new PersistenceTreeCreator(), WosFramework.getTemplateLayoutForName("PersistenceManager.theTable"), new MyTreeListener());
		}

		protected void updateNodeState(CheckNode theNode)
		{
			if (theWindow != null)
				new PersistenceStateManager().setComponentPersistenceState(theWindow.getClass().getName(), theNode.getUserObject().toString(), theNode.isSelected());

		}
		protected void updateTreeState()
		{
			if (theTree.getSelectionCount() > 0)
				saveComponentState(theTree.getSelectionPath());

			super.updateTreeState();
		}

	}

	public static void main(String s[])
	{
		JFrame frame= new PersistenceManager();
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		frame.pack();
		frame.setVisible(true);
	}
}