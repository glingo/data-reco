//WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package net.ar.webonswing.managers.ui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.swing.layouts.*;

public class TemplateManagerWindow extends JFrame
{
	private JPanel theListPanel;

	public TemplateManagerWindow()
	{
		initComponents();
	}

	private void initComponents()
	{
		WosFramework.getInstance().initTemplateManager();
		SortedMap theTemplateMap= new TreeMap(WosFramework.getInstance().getTemplateManager().getTemplateTable());

		theListPanel= new JPanel(new TemplateFlowLayout());

		for (Iterator i= theTemplateMap.entrySet().iterator(); i.hasNext();)
		{
			Map.Entry theEntry= (Map.Entry) i.next();

			JPanel theEntryPanel= new JPanel();
			theEntryPanel.setLayout(WosFramework.getTemplateLayoutForName("TemplateManager.theTable.theRow"));

			HtmlTemplateManagerEntry theKey= (HtmlTemplateManagerEntry) theEntry.getKey();

			JTextField theKeyTextField= new JTextField(theKey.getTemplateName());
			JTextField theValueTextField= new JTextField(theKey.getResourcePath());

			JLabel theDeleteButton= new JLabel(new ImageIcon("net/ar/webonswing/resources/images/bot_delete.gif"));

			if (theKey.equals("TemplateManager") || theKey.equals("JButton") || theKey.equals("JTextField") || theKey.equals("JLabel"))
			{
				theKeyTextField.setEnabled(false);
				theValueTextField.setEnabled(false);
			}
			else
			{
				theDeleteButton.addMouseListener(new DeleteAction(theKeyTextField));
				theDeleteButton.setToolTipText("Delete template");
			}

			theEntryPanel.add(theKeyTextField, "theKey");
			theEntryPanel.add(theValueTextField, "theValue");
			theEntryPanel.add(theDeleteButton, "theDeleteButton");

			theListPanel.add(theEntryPanel);
		}

		JButton theSaveButton= new JButton("save");
		theSaveButton.addActionListener(new SaveAction());

		JLabel theAddButton= new JLabel(new ImageIcon("net/ar/webonswing/resources/images/bot_insertar.gif"));
		theAddButton.setToolTipText("Add template");
		theAddButton.addMouseListener(new AddAction());

		JPanel theBodyPanel= new JPanel();
		theBodyPanel.setLayout(WosFramework.getTemplateLayoutForName("TemplateManager.theTable"));
		theBodyPanel.add(theListPanel, "theRow");
		theBodyPanel.add(theSaveButton, "theSaveButton");
		theBodyPanel.add(theAddButton, "theAddButton");

		setContentPane(new ManagementComponent(theBodyPanel, "Template Manager"));
	}

	public void updateTemplateManager(Map theMap)
	{
		WosFramework.getInstance().getTemplateManager().setTemplateTable(new Hashtable(theMap));
		WosFramework.getInstance().persistTemplateManager();

		initComponents();
	}

	private Hashtable getTemplateManagerMapFromTextFields()
	{
		Hashtable theNewTemplateMap= new Hashtable();

		for (int i= 0; i < theListPanel.getComponentCount(); i++)
		{
			JPanel theEntryPanel= (JPanel) theListPanel.getComponent(i);
			String theKey= ((JTextField) theEntryPanel.getComponent(0)).getText();
			String theValue= ((JTextField) theEntryPanel.getComponent(1)).getText();
			
			HtmlTemplateManagerEntry theEntry= new HtmlTemplateManagerEntry (theKey, theValue, null);
			theNewTemplateMap.put(theEntry, theEntry);
		}

		return theNewTemplateMap;
	}

	public class SaveAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			updateTemplateManager(getTemplateManagerMapFromTextFields());
		}
	}

	public class AddAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			Hashtable theMap= getTemplateManagerMapFromTextFields();

			String theTemplateName= "Z-Template" + theMap.size();

			while (theMap.containsKey(theTemplateName))
				theTemplateName= theTemplateName + "b";

			HtmlTemplateManagerEntry theEntry= new HtmlTemplateManagerEntry (theTemplateName, "a path", null);
			theMap.put(theEntry, theEntry);

			updateTemplateManager(theMap);
		}
	}

	public class DeleteAction extends MouseAdapter
	{
		private JTextField theKeyTextField;

		public DeleteAction(JTextField aKeyTextField)
		{
			theKeyTextField= aKeyTextField;
		}

		public void mouseClicked(MouseEvent e)
		{
			Map theMap= getTemplateManagerMapFromTextFields();
			theMap.remove(theKeyTextField.getText());
			updateTemplateManager(theMap);
		}
	}

	public static void main(String s[])
	{
		JFrame frame= new TemplateManagerWindow();
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
