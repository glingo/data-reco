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

package examples.withtemplate;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.layouts.*;

/*
 * This class adds event handling to MenuLookDemo.
 */
public class TemplateMenuDemo extends JFrame implements ActionListener, ItemListener
{
	JMenuBar menuBar;
	JTextArea output;
	JScrollPane scrollPane;
	String newline= "\n";
	String currentMenu= "MenuArgentino";

	class RadioListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			currentMenu= e.getActionCommand();
			menuBar.setLayout(new PropagateMenuTemplateLayout(WosFramework.getKeyPositionTemplateForName(e.getActionCommand() + ".JMenuBar")));
		}
	}

	private JRadioButton getRadioFor(String aText, ButtonGroup aButtonGroup)
	{
		JRadioButton theRadio= new JRadioButton(aText);
		theRadio.setLayout(WosFramework.getTemplateLayoutForName("XpMenu.theDemo.theRadio"));
		theRadio.addActionListener(new RadioListener());
		aButtonGroup.add(theRadio);
		return theRadio;
	}

	private JPanel getComboPanel()
	{
		JPanel theStatePanel= new JPanel(new TemplateFlowLayout());

		ButtonGroup theButtonGroup= new ButtonGroup();

		JRadioButton theRadio1= getRadioFor("MenuArgentino", theButtonGroup);
		theRadio1.setSelected(true);
		theStatePanel.add(theRadio1);

		theStatePanel.add(getRadioFor("EcompanyMenu", theButtonGroup));
		theStatePanel.add(getRadioFor("XpMenu", theButtonGroup));
		theStatePanel.add(getRadioFor("WinMenu", theButtonGroup));

		return theStatePanel;
	}

	public TemplateMenuDemo()
	{
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		menuBar= new JMenuBar();
		menuBar.setLayout(new PropagateMenuTemplateLayout(WosFramework.getKeyPositionTemplateForName("MenuArgentino.JMenuBar")));

		Container contentPane= getContentPane();
		contentPane.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("XpMenu.theDemo")));
		//contentPane.add(menuBar);
		contentPane.add(menuBar, "theMenuBar");
		contentPane.add(getComboPanel(), "theRadio");

		//Build the first menu.
		menu= new JMenu("A Menu");
		menu.setBounds(0, 19, 800, 1000);
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem= new JMenuItem("A text-only menu item", KeyEvent.VK_T);
		menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(this);
		menuItem.setEnabled(false);
		menu.add(menuItem);

		menuItem= new JMenuItem("Both text and icon", new ImageIcon("resources/images/open.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		//a group of radio button menu items

		menu.addSeparator();
		ButtonGroup group= new ButtonGroup();
		rbMenuItem= new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(this);
		menu.add(rbMenuItem);
		rbMenuItem= new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(this);
		menu.add(rbMenuItem);

		//a group of check box menu items
		menu.addSeparator();

		cbMenuItem= new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		cbMenuItem.setSelected(true);
		cbMenuItem.addItemListener(this);
		menu.add(cbMenuItem);
		cbMenuItem= new JCheckBoxMenuItem("Another one");
		cbMenuItem.setSelected(true);
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		cbMenuItem.addItemListener(this);
		menu.add(cbMenuItem);

		//a submenu

		JSeparator aSeparator= new JPopupMenu.Separator();
		menu.add(aSeparator);

		submenu= new JMenu("A submenu");
		submenu.setBounds(200, 180, 200, 200);
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem= new JMenuItem("An item in the submenu", new ImageIcon("resources/images/save.gif"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		submenu.add(menuItem);

		menuItem= new JMenuItem("Another item");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		menu.add(submenu);

		//Build second menu in the menu bar.
		menu= new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menu.setBounds(0, 19, 200, 200);
		menuBar.add(menu);

		menuItem= new JMenuItem("1");
		menu.add(menuItem);
		menuItem= new JMenuItem("2");
		menu.add(menuItem);
		menuItem= new JMenuItem("3");
		menu.add(menuItem);

		menu= new JMenu("Another Menu 2");
		menu.setBounds(0, 19, 200, 200);
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menuBar.add(menu);

		menuItem= new JMenuItem("4");
		menu.add(menuItem);
		menuItem= new JMenuItem("5");
		menu.add(menuItem);
		menuItem= new JMenuItem("6");
		menu.add(menuItem);

		JMenu menu2= new JMenu("Another Menu 3");
		menu2.setBounds(0, 19, 200, 200);
		menu2.setMnemonic(KeyEvent.VK_N);
		menu2.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menuBar.add(menu2);

		menuItem= new JMenuItem("4");
		menu2.add(menuItem);
		menuItem= new JMenuItem("5");
		menu2.add(menuItem);
		menuItem= new JMenuItem("6");
		menu2.add(menuItem);

		menu= new JMenu("Another Menu 3");
		menu.setBounds(200, 50, 200, 200);
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menu2.add(menu);

		menuItem= new JMenuItem("4");
		menu.add(menuItem);
		menuItem= new JMenuItem("5");
		menu.add(menuItem);

		menu2= new JMenu("Another Menu 3");
		menu2.setBounds(200, 50, 100, 300);
		menu2.setMnemonic(KeyEvent.VK_N);
		menu2.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menu.add(menu2);

		menuItem= new JMenuItem("4");
		menu.add(menuItem);
		menuItem= new JMenuItem("5");
		menu.add(menuItem);
		menu= new JMenu("Another Menu 3");
		menu.setBounds(200, 10, 200, 200);
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menu2.add(menu);

		menuItem= new JMenuItem("4");
		menu.add(menuItem);
		menuItem= new JMenuItem("5");
		menu.add(menuItem);

	}

	public void actionPerformed(ActionEvent e)
	{
		JMenuItem source= (JMenuItem) (e.getSource());
		String s= "Action event detected." + newline + "    Event source: " + source.getText() + " (an instance of " + getClassName(source) + ")";
		output.append(s + newline);
	}

	public void itemStateChanged(ItemEvent e)
	{
		JMenuItem source= (JMenuItem) (e.getSource());
		String s= "Item event detected." + newline + "    Event source: " + source.getText() + " (an instance of " + getClassName(source) + ")" + newline + "    New state: " + ((e.getStateChange() == ItemEvent.SELECTED) ? "selected" : "unselected");
		output.append(s + newline);
	}

	// Returns just the class name -- no package info.
	protected String getClassName(Object o)
	{
		String classString= o.getClass().getName();
		int dotIndex= classString.lastIndexOf(".");
		return classString.substring(dotIndex + 1);
	}

	public static void main(String[] args)
	{
		TemplateMenuDemo window= new TemplateMenuDemo();

		window.setTitle("MenuDemo");
		window.setSize(450, 260);
		window.setVisible(true);
	}
	public String getCurrentMenu()
	{
		return currentMenu;
	}

	public void setCurrentMenu(String aString)
	{
		currentMenu= aString;
	}

}
