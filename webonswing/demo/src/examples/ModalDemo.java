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

import net.ar.webonswing.*;

public class ModalDemo extends JDialog implements ActionListener
{
	protected JTextField theTextField;

	public ModalDemo()
	{
		init("0");
	}

	public ModalDemo(String aCounter)
	{
		init(aCounter);
	}

	private void init(String aCounter)
	{
		JPanel theContentPane= (JPanel) getContentPane();

		JButton theOpenButton= new JButton("open new modal");
		theOpenButton.addActionListener(this);

		JButton theCloseButton= new JButton("close");
		theCloseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WosFramework.hide(ModalDemo.this);
			}
		});

		theTextField= new JTextField(String.valueOf(aCounter));
		theTextField.putClientProperty("theContributor", ModalDemoTextFieldContributor.class.getName());

		theContentPane.setLayout(new GridLayout(3, 1));
		theContentPane.add(theOpenButton);
		theContentPane.add(theCloseButton);
		theContentPane.add(theTextField);
	}

	public void processResults(ModalDemo aWindow)
	{
		theTextField.setText(aWindow.getTextField().getText() + Integer.toHexString((Integer.parseInt(aWindow.getTextField().getText().substring(aWindow.getTextField().getText().length() - 1), 16) - 1)));
	}

	public void actionPerformed(ActionEvent e)
	{
		ModalDemo theNewWindow= new ModalDemo(getTextField().getText() + Integer.toHexString((Integer.parseInt(getTextField().getText().substring(getTextField().getText().length() - 1), 16) + 1)));
		theNewWindow.setModal(true);
		WosFramework.showAndExecute(ModalDemo.this, theNewWindow, "processResults");
	}

	public JTextField getTextField()
	{
		return theTextField;
	}

	public void setTextField(JTextField aField)
	{
		theTextField= aField;
	}

}
