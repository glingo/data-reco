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

public class TemplateRadioButtonDemo extends JFrame
{

	public static void main(String[] args)
	{
		new TemplateRadioButtonDemo().setVisible(true);
	}

	public TemplateRadioButtonDemo()
	{
		setContentPane(new RadioButtonDemoPanel());
	}

	public JPanel getRadioButtonPanel()
	{
		return new RadioButtonDemoPanel();
	}

	public static class RadioButtonDemoPanel extends JPanel
	{

		String birdString= "Bird";
		String catString= "Cat";
		String dogString= "Dog";
		String rabbitString= "Rabbit";
		String pigString= "Pig";

		JLabel picture;

		public RadioButtonDemoPanel()
		{
			// Create the radio buttons.
			final JRadioButton birdButton= new JRadioButton(birdString);
			birdButton.setMnemonic(KeyEvent.VK_B);
			birdButton.setActionCommand(birdString);
			birdButton.setSelected(true);
			birdButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("RadioButtonDemo.twoElements.radioButtonPanel.radio1")));

			final JRadioButton catButton= new JRadioButton(catString);
			catButton.setMnemonic(KeyEvent.VK_C);
			catButton.setActionCommand(catString);
			catButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("RadioButtonDemo.twoElements.radioButtonPanel.radio1")));

			final JRadioButton dogButton= new JRadioButton(dogString);
			dogButton.setMnemonic(KeyEvent.VK_D);
			dogButton.setActionCommand(dogString);
			dogButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("RadioButtonDemo.twoElements.radioButtonPanel.radio1")));
			dogButton.putClientProperty("theUserUniqueName", "DogButton");
			

			final JRadioButton rabbitButton= new JRadioButton(rabbitString);
			rabbitButton.setMnemonic(KeyEvent.VK_R);
			rabbitButton.setActionCommand(rabbitString);
			rabbitButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("RadioButtonDemo.twoElements.radioButtonPanel.radio1")));

			final JRadioButton pigButton= new JRadioButton(pigString);
			pigButton.setMnemonic(KeyEvent.VK_P);
			pigButton.setActionCommand(pigString);
			pigButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("RadioButtonDemo.twoElements.radioButtonPanel.radio1")));

			// Group the radio buttons.
			ButtonGroup group= new ButtonGroup();
			group.add(birdButton);
			group.add(catButton);
			group.add(dogButton);
			group.add(rabbitButton);
			group.add(pigButton);

			picture= new JLabel();
			picture.setIcon(new ImageIcon("resources/images/" + birdString + ".gif"));

			class RadioListener implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					picture.setIcon(new ImageIcon("resources/images/" + e.getActionCommand() + ".gif"));
				}

				public String getName()
				{
					return "ChangePicture";
				}

				public Object[] getParameters()
				{
					return new Object[] { picture };
				}
			}

			RadioListener myListener= new RadioListener();
			birdButton.addActionListener(myListener);
			catButton.addActionListener(myListener);
			dogButton.addActionListener(myListener);
			rabbitButton.addActionListener(myListener);
			pigButton.addActionListener(myListener);

			// Set up the picture label

			// The preferred size is hard-coded to be the width of the
			// widest image and the height of the tallest image.
			// A real program would compute this.
			picture.setPreferredSize(new Dimension(177, 122));
			picture.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("RadioButtonDemo.twoElements.thePicture")));

			// Put the radio buttons in a column in a panel
			JPanel radioPanel= new JPanel();
			radioPanel.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("RadioButtonDemo.twoElements.radioButtonPanel")));
			
			radioPanel.add(birdButton, "radio1");
			radioPanel.add(catButton, "radio2");
			radioPanel.add(dogButton, "radio3");
			radioPanel.add(rabbitButton, "radio4");
			radioPanel.add(pigButton, "radio5");

			setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("RadioButtonDemo.twoElements")));
			add(radioPanel, "radioButtonPanel");
			add(picture, "thePicture");

			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		}
	}

}
