package net.ar.webonswing.petstore;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.model.*;
import net.ar.webonswing.swing.components.validators.*;
import net.ar.webonswing.swing.layouts.*;

public class LoginPage extends ApplicationFrame implements ActionListener
{
	protected LoginComponent login;

	public LoginPage()
	{
		init();
	}

	protected JComponent getBody()
	{
		login= new LoginComponent(this, new JValidator()
		{
			public boolean isRemoteValidation()
			{
				return false;
			}

			protected boolean performValidation()
			{
				groupMessage= "Invalid user name or password";

				List customers= getDao().getCustomers(login.getUsername(), login.getPassword());
				if (!customers.isEmpty())
				{
					setCurrentCustomer((Customer)customers.get(0));
					return true;
				}
				return false;
			}
		});

		login.setLayout(new PropagateTemplateLayoutByName(ApplicationFrame.getCurrentSkin().getCurrentTemplateFor("Login.main"), false));

		return login;
	}

	public void actionPerformed(ActionEvent event)
	{
		if (login.getGroupValidator().doValidation())
		{
			WosFramework.showChildWindow(LoginPage.this, new Home());
		}
	}
}