
package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;

public class Login extends JDialog
{
	public JTextField userField= new JTextField ();

	public Login()
	{
		final JPasswordField passwordField= new JPasswordField();
		JButton button= new JButton("Login");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent aE)
			{
				if (userField.getText().equals("maradona") && passwordField.getText().equals("pelusa"))
					{
						WosFramework.getSessionContext().put("user", "diego");
						WosFramework.hide(Login.this);
					}
			}
		});

		getContentPane().setLayout(new GridLayout(0, 1));
		getContentPane().add(userField);
		getContentPane().add(passwordField);
		getContentPane().add(button);
	}
}