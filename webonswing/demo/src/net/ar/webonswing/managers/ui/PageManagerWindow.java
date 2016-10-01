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
import net.ar.webonswing.managers.pages.*;
import net.ar.webonswing.swing.layouts.*;

public class PageManagerWindow extends JFrame
{
	private JPanel theListPanel;

	public PageManagerWindow()
	{
		initComponents();
	}

	private void initComponents()
	{
		WosFramework.getInstance().initPageManager();
		theListPanel= new JPanel(new TemplateFlowLayout());

		for (Iterator i= WosFramework.getInstance().getPageManager().getIterator(); i.hasNext();)
		{
			HtmlPageManagerEntry thePageSpec= (HtmlPageManagerEntry) i.next();

			JPanel theEntryPanel= new JPanel();
			theEntryPanel.setLayout(WosFramework.getTemplateLayoutForName("PageManager.theTable.theRow"));

			JTextField theUrlField= new JTextField(thePageSpec.getPath());
			JTextField thePageField= new JTextField(thePageSpec.getPageClassName());
			JTextField theWindowField= new JTextField(thePageSpec.getWindowClassName());
			JTextField thePlaceField= new JTextField(thePageSpec.getWindowPlace());
			JLabel theDeleteButton= new JLabel(new ImageIcon("resources/images/bot_delete.gif"));

			theDeleteButton.addMouseListener(new DeleteAction(theUrlField));
			theDeleteButton.setToolTipText("Delete template");

			theEntryPanel.add(theUrlField, "theUrl");
			theEntryPanel.add(thePageField, "thePage");
			theEntryPanel.add(theWindowField, "theWindow");
			theEntryPanel.add(thePlaceField, "thePlace");
			theEntryPanel.add(theDeleteButton, "theDeleteButton");

			theListPanel.add(theEntryPanel);
		}

		JButton theSaveButton= new JButton("save");
		theSaveButton.addActionListener(new SaveAction());

		JLabel theAddButton= new JLabel(new ImageIcon("resources/images/bot_insertar.gif"));
		theAddButton.setToolTipText("Add template");
		theAddButton.addMouseListener(new AddAction());

		JPanel theBodyPanel= new JPanel();
		theBodyPanel.setLayout(WosFramework.getTemplateLayoutForName("PageManager.theTable"));
		theBodyPanel.add(theListPanel, "theRow");
		theBodyPanel.add(theSaveButton, "theSaveButton");
		theBodyPanel.add(theAddButton, "theAddButton");

		setContentPane(new ManagementComponent(theBodyPanel, "Page Manager"));
	}

	private PageManager createPageManagerFromFields()
	{
		HtmlPageManager theNewPageManager= new HtmlPageManager();

		for (int i= 0; i < theListPanel.getComponentCount(); i++)
		{
			JPanel theEntryPanel= (JPanel) theListPanel.getComponent(i);
			String theUrl= ((JTextField) theEntryPanel.getComponent(0)).getText();
			String thePage= ((JTextField) theEntryPanel.getComponent(1)).getText();
			String theWindow= ((JTextField) theEntryPanel.getComponent(2)).getText();
			String thePlace= ((JTextField) theEntryPanel.getComponent(3)).getText();

			theNewPageManager.addPage(theUrl, thePage, theWindow, thePlace);
		}

		return theNewPageManager;
	}

	public class SaveAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			WosFramework.getInstance().setPageManager(createPageManagerFromFields());
			WosFramework.getInstance().persistPageManager();
			initComponents();
		}
	}

	public class AddAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			WosFramework.getInstance().setPageManager(createPageManagerFromFields());
			WosFramework.getInstance().getPageManager().addPage("", "", "", "");
			WosFramework.getInstance().persistPageManager();
			initComponents();
		}
	}

	public class DeleteAction extends MouseAdapter
	{
		private JTextField theUrlField;

		public DeleteAction(JTextField anUrlField)
		{
			theUrlField= anUrlField;
		}

		public void mouseClicked(MouseEvent e)
		{
			WosFramework.getInstance().setPageManager(createPageManagerFromFields());
			WosFramework.getInstance().getPageManager().removePageWithPath(theUrlField.getText());
			WosFramework.getInstance().persistPageManager();
			initComponents();
		}
	}

	public static void main(String s[])
	{
		JFrame frame= new PageManagerWindow();
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
