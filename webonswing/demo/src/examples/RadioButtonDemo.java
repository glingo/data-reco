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

public class RadioButtonDemo extends JFrame
{
	public static void main(String s[])
	{
		JFrame frame= new RadioButtonDemo();
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

	public RadioButtonDemo()
	{
		setContentPane(new RadioButtonDemoPanel());
	}

	public static class RadioButtonDemoPanel extends JPanel
	{
		static String birdString= "Bird";
		static String catString= "Cat";
		static String dogString= "Dog";
		static String rabbitString= "Rabbit";
		static String pigString= "Pig";

		JLabel picture;

		public RadioButtonDemoPanel()
		{
			// Create the radio buttons.
			JRadioButton birdButton= new JRadioButton(birdString);
			birdButton.setMnemonic(KeyEvent.VK_B);
			birdButton.setActionCommand(birdString);
			birdButton.setSelected(true);

			JRadioButton catButton= new JRadioButton(catString);
			catButton.setMnemonic(KeyEvent.VK_C);
			catButton.setActionCommand(catString);

			JRadioButton dogButton= new JRadioButton(dogString);
			dogButton.setMnemonic(KeyEvent.VK_D);
			dogButton.setActionCommand(dogString);

			JRadioButton rabbitButton= new JRadioButton(rabbitString);
			rabbitButton.setMnemonic(KeyEvent.VK_R);
			rabbitButton.setActionCommand(rabbitString);

			JRadioButton pigButton= new JRadioButton(pigString);
			pigButton.setMnemonic(KeyEvent.VK_P);
			pigButton.setActionCommand(pigString);

			// Group the radio buttons.
			ButtonGroup group= new ButtonGroup();
			group.add(birdButton);
			group.add(catButton);
			group.add(dogButton);
			group.add(rabbitButton);
			group.add(pigButton);

			// Register a listener for the radio buttons.
			RadioListener myListener= new RadioListener();
			birdButton.addActionListener(myListener);
			catButton.addActionListener(myListener);
			dogButton.addActionListener(myListener);
			rabbitButton.addActionListener(myListener);
			pigButton.addActionListener(myListener);

			// Set up the picture label
			picture= new JLabel(new ImageIcon("resources/images/" + birdString + ".gif"));

			// The preferred size is hard-coded to be the width of the
			// widest image and the height of the tallest image.
			// A real program would compute this.
			picture.setPreferredSize(new Dimension(177, 122));

			// Put the radio buttons in a column in a panel
			JPanel radioPanel= new JPanel();
			radioPanel.setLayout(new GridLayout(0, 1));
			radioPanel.add(birdButton);
			radioPanel.add(catButton);
			radioPanel.add(dogButton);
			radioPanel.add(rabbitButton);
			radioPanel.add(pigButton);

			setLayout(new BorderLayout());
			add(radioPanel, BorderLayout.WEST);
			add(picture, BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		}

		/** Listens to the radio buttons. */
		class RadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				picture.setIcon(new ImageIcon("resources/images/" + e.getActionCommand() + ".gif"));
			}
		}

	}
}