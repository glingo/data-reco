
package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;

public class LoginExample extends JDialog
{
	protected JLabel label= new JLabel("");

	public LoginExample()
	{
		JButton topSecret= new JButton("Login here!");
		topSecret.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent aE)
			{
				if (WosFramework.getSessionContext().get("user") == null)
				{
					Login loginWindow= new Login();
					loginWindow.setModal(true);
					WosFramework.showAndExecute(LoginExample.this, loginWindow, "processLogin");
				}
				else
					label.setText("Welcome again!!!");
			}
		});

		getContentPane().setLayout(new GridLayout(0, 1));
		getContentPane().add(label);
		getContentPane().add(topSecret);
	}

	public void processLogin(Login aLoginWindow)
	{
		label.setText("Welcome " + aLoginWindow.userField.getText() + "!!!");
	}
}