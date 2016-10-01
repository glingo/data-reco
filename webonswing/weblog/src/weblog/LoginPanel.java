//WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
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

package weblog;

import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.components.*;

import org.wafer.weblog.om.*;
import org.wafer.weblog.servlet.*;

public class LoginPanel extends MultipleFieldsPanel
{
	JTextField usernameField;
	JPasswordField passwordField;
	JLabel errorMessage;

	public LoginPanel()
	{
		super(new JLink("Enter your username and password to log in. New users must register first.", RegisterView.class));
	}

	protected void initialize()
	{
		super.initialize();
		
		submitButton.setText("Login");
		submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					if (!WebLogSystem.getUserStore().verify(usernameField.getText()))
						errorMessage.setText("password or username incorrect");
					else
					{
						User currentUser= WebLogSystem.getUserStore().getUser(usernameField.getText());

						if (!currentUser.getPassword().equals(new String(passwordField.getPassword())))
							errorMessage.setText("password or username incorrect");
						else
						{
							WosFramework.getSessionContext().put("activeUser", currentUser);
							WosFramework.showChildWindow(getTopLevelAncestor(), new Home());
							WosFramework.hide(getTopLevelAncestor());
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		addFieldPanel("Username", usernameField= new JTextField());
		addFieldPanel("Password", passwordField= new JPasswordField());
		addFieldPanel("", errorMessage= new JLabel());
	}
}
