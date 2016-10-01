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

import org.wafer.weblog.om.*;
import org.wafer.weblog.servlet.*;

public class RegisterPanel extends MultipleFieldsPanel
{
	JTextField usernameField;
	JTextField emailField;
	JTextField firstNameField;
	JTextField lastNameField;
	JTextField passwordField;

	public RegisterPanel()
	{
		super(new JLabel("Please fill in the following information to create a new account. All fields are required."));
	}

	protected void initialize()
	{
		super.initialize();
		
		submitButton.setText("Create User");
		submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					User newUser = WebLogSystem.getUserStore().create(usernameField.getText(), passwordField.getText());
					
					newUser.setFirstName(firstNameField.getText());
					newUser.setLastName(lastNameField.getText());
					newUser.setEmail(emailField.getText());
					newUser.save();

					WosFramework.getSessionContext().put("activeUser", newUser);
					WosFramework.showChildWindow(getTopLevelAncestor(), new Home());
					WosFramework.hide(getTopLevelAncestor());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		addFieldPanel("Username", usernameField= new JTextField());
		addFieldPanel("Email", emailField= new JTextField());
		addFieldPanel("First Name", firstNameField= new JTextField());
		addFieldPanel("Last Name", lastNameField= new JTextField());
		addFieldPanel("Password", passwordField= new JPasswordField());
	}
}
