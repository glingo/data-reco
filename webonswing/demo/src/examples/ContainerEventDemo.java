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

package examples;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class ContainerEventDemo extends JFrame
{
	public static void main(String[] args)
	{
		JFrame theDemo= new ContainerEventDemo();

		theDemo.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		theDemo.pack();
		theDemo.setVisible(true);
	}

	public ContainerEventDemo ()
	{
		setContentPane(new ContainerEventDemoPanel());
	}

	public static class ContainerEventDemoPanel extends JPanel implements ContainerListener, ActionListener
	{
		JTextArea display= new JTextArea();
		JPanel buttonPanel= new JPanel();
		JButton addButton, removeButton, clearButton;
		Vector buttonList= new Vector(10, 10);
		static final String ADD= "add";
		static final String REMOVE= "remove";
		static final String CLEAR= "clear";
		static final String newline= "\n";


		public ContainerEventDemoPanel()
		{
			setBounds(0, 0, 300, 300);
			init();
		}

		public void init()
		{
			//Initialize an empty list of buttons.

			//Create all the components.
			addButton= new JButton("Add a button");
			addButton.setActionCommand(ADD);
			addButton.addActionListener(this);

			removeButton= new JButton("Remove a button");
			removeButton.setActionCommand(REMOVE);
			removeButton.addActionListener(this);

			buttonPanel.setPreferredSize(new Dimension(200, 75));
			buttonPanel.addContainerListener(this);

			display.setEditable(false);
			JScrollPane scrollPane= new JScrollPane(display);
			scrollPane.setPreferredSize(new Dimension(200, 75));
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

			clearButton= new JButton("Clear text area");
			clearButton.setActionCommand(CLEAR);
			clearButton.addActionListener(this);

			//Lay out the components.
			GridBagLayout gridbag= new GridBagLayout();
			GridBagConstraints c= new GridBagConstraints();
			JPanel contentPane= this;
			contentPane.setLayout(gridbag);
			c.fill= GridBagConstraints.BOTH; //Fill entire cell.

			c.weighty= 1.0; //Button area and message area have equal height.
			c.gridwidth= GridBagConstraints.REMAINDER; //end of row
			gridbag.setConstraints(scrollPane, c);
			contentPane.add(scrollPane);

			c.weighty= 0.0;
			gridbag.setConstraints(clearButton, c);
			contentPane.add(clearButton);

			c.weightx= 1.0; //Add/remove buttons have equal width.
			c.gridwidth= 1; //NOT end of row
			gridbag.setConstraints(addButton, c);
			contentPane.add(addButton);

			c.gridwidth= GridBagConstraints.REMAINDER; //end of row
			gridbag.setConstraints(removeButton, c);
			contentPane.add(removeButton);

			c.weighty= 1.0; //Button area and message area have equal height.
			gridbag.setConstraints(buttonPanel, c);
			contentPane.add(buttonPanel);
		}

		public void componentAdded(ContainerEvent e)
		{
			displayMessage(" added to ", e);
		}

		public void componentRemoved(ContainerEvent e)
		{
			displayMessage(" removed from ", e);
		}

		void displayMessage(String action, ContainerEvent e)
		{
			display.append(((JButton) e.getChild()).getText() + " was" + action + e.getContainer().getClass().getName() + newline);
		}

		/*
		 * This could have been implemented as two or three
		 * classes or objects, for clarity.
		 */
		public void actionPerformed(ActionEvent e)
		{
			String command= e.getActionCommand();

			if (command == ADD)
			{
				JButton newButton= new JButton("JButton #" + (buttonList.size() + 1));
				buttonList.addElement(newButton);
				buttonPanel.add(newButton);
				//buttonPanel.revalidate(); //Make the button show up.

			}
			else if (command == REMOVE)
			{
				int lastIndex= buttonList.size() - 1;
				try
				{
					JButton nixedButton= (JButton) buttonList.elementAt(lastIndex);
					buttonPanel.remove(nixedButton);
					buttonList.removeElementAt(lastIndex);
					/*
						buttonPanel.revalidate(); //Make the button disappear.
						buttonPanel.repaint(); //Make the button disappear.
					*/
				}
				catch (ArrayIndexOutOfBoundsException exc)
				{
				}
			}
			else if (command == CLEAR)
			{
				display.setText("");
			}
		}
	}
}