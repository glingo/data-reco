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

import javax.swing.*;

public class MultiListener extends JFrame
{
	public MultiListener()
	{
		setContentPane(new MultiListenerPanel());
	}

	public static void main(String[] args)
	{
		MultiListener theMultiListener= new MultiListener();

		theMultiListener.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		theMultiListener.pack();
		theMultiListener.setVisible(true);
	}

	public static class MultiListenerPanel extends JPanel implements ActionListener
	{
		JTextArea topTextArea;
		JTextArea bottomTextArea;
		JButton button1, button2;
		final static String newline= "\n";

		public MultiListenerPanel()
		{
			init();
		}

		public void init()
		{
			JLabel l= null;

			GridBagLayout gridbag= new GridBagLayout();
			GridBagConstraints c= new GridBagConstraints();
			JPanel contentPane= this;
			contentPane.setLayout(gridbag);
			contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, Color.black), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

			c.fill= GridBagConstraints.BOTH;
			c.gridwidth= GridBagConstraints.REMAINDER;
			l= new JLabel("What MultiListener hears:");
			gridbag.setConstraints(l, c);
			contentPane.add(l);

			c.weighty= 1.0;
			topTextArea= new JTextArea();
			topTextArea.setEditable(false);
			JScrollPane topScrollPane= new JScrollPane(topTextArea);

			Dimension preferredSize= new Dimension(200, 75);
			topScrollPane.setPreferredSize(preferredSize);
			gridbag.setConstraints(topScrollPane, c);
			contentPane.add(topScrollPane);

			c.weightx= 0.0;
			c.weighty= 0.0;
			l= new JLabel("What Eavesdropper hears:");
			gridbag.setConstraints(l, c);
			contentPane.add(l);

			c.weighty= 1.0;
			bottomTextArea= new JTextArea();
			bottomTextArea.setEditable(false);
			JScrollPane bottomScrollPane= new JScrollPane(bottomTextArea);
			bottomScrollPane.setPreferredSize(preferredSize);
			gridbag.setConstraints(bottomScrollPane, c);
			contentPane.add(bottomScrollPane);

			c.weightx= 1.0;
			c.weighty= 0.0;
			c.gridwidth= 1;
			c.insets= new Insets(10, 10, 0, 10);
			button1= new JButton("Blah blah blah");
			gridbag.setConstraints(button1, c);
			contentPane.add(button1);

			c.gridwidth= GridBagConstraints.REMAINDER;
			button2= new JButton("You dont say!");
			gridbag.setConstraints(button2, c);
			contentPane.add(button2);

			button1.addActionListener(this);
			button2.addActionListener(this);

			button2.addActionListener(new Eavesdropper(bottomTextArea));
		}

		public void actionPerformed(ActionEvent e)
		{
			topTextArea.append(e.getActionCommand() + newline);
		}

		class Eavesdropper implements ActionListener
		{
			JTextArea myTextArea;
			public Eavesdropper(JTextArea ta)
			{
				myTextArea= ta;
			}

			public void actionPerformed(ActionEvent e)
			{
				myTextArea.append(e.getActionCommand() + MultiListenerPanel.newline);
			}
		}
	}
}