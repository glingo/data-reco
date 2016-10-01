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
import net.ar.webonswing.remote.*;
import net.ar.webonswing.swing.layouts.*;

public class TemplateCheckBoxDemo extends JDialog
{
	public static void main(String[] args)
	{
		WosFramework.getInstance().init();
		new TemplateCheckBoxDemo().setVisible(true);
	}

	public TemplateCheckBoxDemo(Frame owner, boolean modal) 
	{
		super(owner, modal);
		init();
	}

	public TemplateCheckBoxDemo()
	{
		init();
	}

	private void init()
	{
		setBounds(0, 0, 400, 400);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		setContentPane(getCheckBoxDemoPabel());
	}


	public JPanel getCheckBoxDemoPabel()
	{
		return new CheckBoxDemoPanel();
	}

	public static class CheckBoxDemoPanel extends JPanel
	{
		JCheckBox chinButton;
		JCheckBox glassesButton;
		JCheckBox hairButton;
		JCheckBox teethButton;

		/*
		 * Four accessory choices provide for 16 different
		 * combinations. The image for each combination is
		 * contained in a separate image file whose name indicates
		 * the accessories. The filenames are "geek-XXXX.gif"
		 * where XXXX can be one of the following 16 choices.
		 * The "choices" StringBuffer contains the string that
		 * indicates the current selection and is used to generate
		 * the file name of the image to display.
		  
			 ----             // zero accessories
		  
			 c---             // one accessory
			 -g--
			 --h-
			 ---t
		  
			 cg--             // two accessories
			 c-h-
			 c--t
			 -gh-
			 -g-t
			 --ht
		  
			 -ght             // three accessories
			 c-ht
			 cg-t
			 cgh-
		  
			 cght             // all accessories
		 */

		StringBuffer choices;
		JLabel pictureLabel;

		public CheckBoxDemoPanel()
		{

			// Create the check boxes
			chinButton= new JCheckBox("Chin");
			chinButton.setMnemonic(KeyEvent.VK_C);
			chinButton.setSelected(true);
			chinButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("CheckBoxDemo.twoElements.checkBoxPanel.check1")));

			glassesButton= new JCheckBox("Glasses");
			glassesButton.setMnemonic(KeyEvent.VK_G);
			glassesButton.setSelected(true);
			glassesButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("CheckBoxDemo.twoElements.checkBoxPanel.check1")));

			hairButton= new JCheckBox("Hair");
			hairButton.setMnemonic(KeyEvent.VK_H);
			hairButton.setSelected(true);
			hairButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("CheckBoxDemo.twoElements.checkBoxPanel.check1")));

			teethButton= new JCheckBox("Teeth");
			teethButton.setMnemonic(KeyEvent.VK_T);
			teethButton.setSelected(true);
			teethButton.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("CheckBoxDemo.twoElements.checkBoxPanel.check1")));

			// Register a listener for the check boxes.
			CheckBoxListener myListener= new CheckBoxListener();
			chinButton.addItemListener(myListener);
			glassesButton.addItemListener(myListener);
			hairButton.addItemListener(myListener);
			teethButton.addItemListener(new CheckBoxListener());

			// Indicates what's on the geek.
			choices= new StringBuffer("cght");

			// Set up the picture label
			pictureLabel= new JLabel();
			pictureLabel.setIcon(new ImageIcon("resources/images/geek/geek-" + choices.toString() + ".gif"));
			pictureLabel.setToolTipText(choices.toString());
			pictureLabel.setText(choices.toString());
			pictureLabel.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("CheckBoxDemo.twoElements.thePicture")));

			// Put the check boxes in a column in a panel
			JPanel checkPanel= new JPanel();
			checkPanel.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("CheckBoxDemo.twoElements.checkBoxPanel")));
			checkPanel.add(chinButton, "check1");
			checkPanel.add(glassesButton, "check2");
			checkPanel.add(hairButton, "check3");
			checkPanel.add(teethButton, "check4");

			setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("CheckBoxDemo.twoElements")));

			add(checkPanel, "checkBoxPanel");
			add(pictureLabel, "thePicture");

			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		}

		/** Listens to the check boxes. */
		class CheckBoxListener implements ItemListener, RemoteListener
		{
			public void itemStateChanged(ItemEvent e)
			{
				int index= 0;
				char c= '-';
				Object source= e.getItemSelectable();

				if (source == chinButton)
				{
					index= 0;
					c= 'c';
				}
				else if (source == glassesButton)
				{
					index= 1;
					c= 'g';
				}
				else if (source == hairButton)
				{
					index= 2;
					c= 'h';
				}
				else if (source == teethButton)
				{
					index= 3;
					c= 't';
				}

				if (e.getStateChange() == ItemEvent.DESELECTED)
					c= '-';

				choices.setCharAt(index, c);
				pictureLabel.setIcon(new ImageIcon("resources/images/geek/geek-" + choices.toString() + ".gif"));
				pictureLabel.setToolTipText(choices.toString());
				pictureLabel.setText(choices.toString());
			}

			public String getRemoteName()
			{
				return "CheckBoxListener";
			}

			public Object[] getRemoteParameters()
			{
				return new Object[] { pictureLabel };
			}
		}
	}
}
