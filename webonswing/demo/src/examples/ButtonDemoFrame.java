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

public class ButtonDemoFrame extends JFrame
{

	public ButtonDemoFrame()
	{
		super("ButtonDemo");

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		getContentPane().add(new ButtonDemo());
	}
	public static class ButtonDemo extends JPanel implements ActionListener
	{
		protected static JButton b1, b2, b3;
		{
			ImageIcon leftButtonIcon= new ImageIcon("images/right.gif");
			ImageIcon middleButtonIcon= new ImageIcon("images/middle.gif");
			ImageIcon rightButtonIcon= new ImageIcon("images/left.gif");

			b1= new JButton("Disable middle button", leftButtonIcon);
			b1.setVerticalTextPosition(SwingConstants.CENTER);
			b1.setHorizontalTextPosition(SwingConstants.LEFT);
			b1.setMnemonic(KeyEvent.VK_D);
			b1.setActionCommand("disable");

			b2= new JButton("Middle button", middleButtonIcon);
			b2.setVerticalTextPosition(SwingConstants.BOTTOM);
			b2.setHorizontalTextPosition(SwingConstants.CENTER);
			b2.setMnemonic(KeyEvent.VK_M);

			b3= new JButton("Enable middle button", rightButtonIcon);
			//Use the default text position of CENTER, RIGHT.
			b3.setMnemonic(KeyEvent.VK_E);
			b3.setActionCommand("enable");
			b3.setEnabled(false);

			add(b1);
			add(b2);
			add(b3);
		}

		public ButtonDemo()
		{
			b1.addActionListener(this);
			b3.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("disable"))
			{
				b2.setEnabled(false);
				b1.setEnabled(false);
				b3.setEnabled(true);
			}
			else
			{
				b2.setEnabled(true);
				b1.setEnabled(true);
				b3.setEnabled(false);
			}
		}
	}
}
