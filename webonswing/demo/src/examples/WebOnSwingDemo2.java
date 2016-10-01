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

import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.swing.layouts.*;
import examples.withtemplate.*;

public class WebOnSwingDemo2 extends JFrame
{
	JPanel theMainPanel= null;
	static int i= 0;

	public static void main(String[] args)
	{
		new WebOnSwingDemo2().setVisible(true);
	}

	public WebOnSwingDemo2()
	{
		addComponents();
	}

	public void addComponents()
	{
		final JLabel theLabel1= new JLabel("label 1");
		theLabel1.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("Label1")));
		theLabel1.setIcon(new ImageIcon("resources/images/Rabbit.gif"));

		final JLabel theLabel2= new JLabel("label inside");
		final JButton theCountButton= new JButton("Count Button");

		ActionListener action= new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				theCountButton.setText("button value= " + ++i);
				theLabel2.setText("label value= " + i);
				TemplateCheckBoxDemo theCheckBoxDemo= new TemplateCheckBoxDemo(WebOnSwingDemo2.this, true);
				theCheckBoxDemo.setModal(true);

				// WindowManager.showWindow(WebOnSwingDemo2.this, new WebPage("http://localhost:8080/WosExamples/WebOnSwingDemo.page"));
				//theCheckBoxDemo.show();
				WosFramework.showChildWindow(WebOnSwingDemo2.this, theCheckBoxDemo);
			}
		};

		ActionListener action2= new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				theLabel2.setText("clicks= " + i++);
			}
		};

		JButton theButton= new JButton("Goto TemplateCheckBoxDemo");
		theButton.addActionListener(action);

		theCountButton.addActionListener(action2);

		JPanel theBodyPanel= new JPanel(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("WebOnSwingDemo2Body")));

		theBodyPanel.add(theButton, "theCheckDemo");
		theBodyPanel.add(theCountButton, "theCountButton");
		theBodyPanel.add(theLabel2, "theLabel2");

		theBodyPanel.add(theLabel1, "theBucket2");

		JLink unLink= new JLink("Goto TemplateRadioButtonDemo", TemplateRadioButtonDemo.class);

		class LinkMouseMotion implements MouseMotionListener, RemoteListener
		{
			public void mouseDragged(MouseEvent e)
			{
			}
			public void mouseMoved(MouseEvent e)
			{
			}

			public String getRemoteName()
			{
				return "ChangeLinkColor";
			}
			public Object[] getRemoteParameters()
			{
				return new Object[0];
			}
		}
		unLink.addMouseMotionListener(new LinkMouseMotion());

		theBodyPanel.add(unLink, "theLink1");

		DefaultComboBoxModel theModel= new DefaultComboBoxModel();
		theModel.addElement("first");
		theModel.addElement("second");
		theModel.addElement("third");

		DefaultComboBoxModel theModel2= new DefaultComboBoxModel();
		theModel2.addElement("element1");
		theModel2.addElement("element2");
		theModel2.addElement("element3");

		DefaultListModel theListModel= new DefaultListModel();
		theListModel.addElement("list element 1");
		theListModel.addElement("list element 2");
		theListModel.addElement("list element 3");
		theListModel.addElement("list element 4");

		JList theList= new JList(theListModel);
		theList.putClientProperty("theContributor", WebOnSwingDemo2Contributor.class.getName());

		JPanel theTable= new JPanel(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("TableTest")));
		JPanel theGrid= new JPanel(new TemplateGridLayout(3, 3, WosFramework.getKeyPositionTemplateForName("RowTest"), WosFramework.getKeyPositionTemplateForName("RowTest2")));
		theGrid.add(new JButton("button 1"));
		theGrid.add(new JComboBox(theModel2));
		theGrid.add(new JCheckBox("check 1"));
		theGrid.add(theList);
		theGrid.add(new JButton("button 2"));
		theGrid.add(new JCheckBox("a check 1"));
		theTable.add(theGrid, "theContent");
		theBodyPanel.add(theTable, "theElement1");

		theBodyPanel.add(new TemplateRadioButtonDemo.RadioButtonDemoPanel(), "theElement3");
		theBodyPanel.add(new JComboBox(theModel), "theComboBox");
		theBodyPanel.add(new TemplateCheckBoxDemo.CheckBoxDemoPanel(), "otroPanel");
		theBodyPanel.add(new JWosReloader(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("JWosReloader"))), "theElement2");

		theMainPanel= new JPanel(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("Label1")));

		theMainPanel.add(new JLabel("WebOnSwing Demo"), "theLabel");
		theMainPanel.add(theBodyPanel, "theImage");

		setContentPane(theMainPanel);
		
		/*
		show();
		pack();
		*/
	}
}
