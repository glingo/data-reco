
package net.ar.webonswing.petstore;

import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.helpers.*;
import net.ar.webonswing.petstore.model.*;
import net.ar.webonswing.swing.components.validators.*;

public class LoginComponent extends JPanel
{
	protected String username;
	protected String password;

	protected JTextField usernameField= new JTextField();
	protected JPasswordField passwordField= new JPasswordField();

	protected Binder binder= new Binder();
	protected ActionListener loginListener;
	private JValidator loginValidator;
	protected JGroupValidator groupValidator;

	public LoginComponent(ActionListener aLoginListener, JValidator aValidator)
	{
		loginListener= aLoginListener;
		loginValidator= aValidator;
		loginValidator.setRemoteValidation(false);

		init();
	}

	protected void init()
	{
		binder.setModel(this);
		binder.add(usernameField, "username");
		binder.add(passwordField, "password");

		binder.modelToView();

		JValidator usernameValidator= new JRequiredFieldValidator(usernameField);
		JValidator passwordValidator= new JRegularExpressionValidator(passwordField, "", "Password must include one of these (!@#$%^&*+;:)", true, ".*[!@#$%^&*+;:].*");
		groupValidator= new JGroupValidator(new JValidator[]{usernameValidator, passwordValidator, loginValidator}, true);
		JButton loginButton= new JButton("Login");
		loginButton.addActionListener(loginListener);

		JButton registerButton= new JButton("Register");
		registerButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WosFramework.showChildWindow(LoginComponent.this.getTopLevelAncestor(), new AccountView(new Customer(), false));
			}
		});

		groupValidator.addShooter(loginButton);

		binder.addViewsToContainer(this);
		add(loginButton).setName("login");
		add(registerButton).setName("registerButton");
		add(usernameValidator).setName("usernameValidator");
		add(passwordValidator).setName("passwordValidator");
		add(groupValidator).setName("groupValidator");
		add(loginValidator).setName("loginValidator");
	}

	public String getPassword()
	{
		binder.viewToModel();
		return password;
	}

	public void setPassword(String aPassword)
	{
		this.password= aPassword;
	}

	public String getUsername()
	{
		binder.viewToModel();
		return username;
	}

	public void setUsername(String anUsername)
	{
		this.username= anUsername;
	}
	public JGroupValidator getGroupValidator()
	{
		return groupValidator;
	}
	public void setGroupValidator(JGroupValidator aGroupValidator)
	{
		groupValidator= aGroupValidator;
	}
}