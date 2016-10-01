package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.components.validators.*;

public class UserDetails extends JDialog
{
	protected User user;

	public UserDetails()
	{
		this(new User());
	}

	public UserDetails(User aUser)
	{
		user= aUser;

		final JTextField firstNameField= new JTextField(user.getFirstName());
		final JTextField lastNameField= new JTextField(user.getLastName());
		final JTextField emailField= new JTextField(user.getEmail());

		final JGroupValidator groupValidator= new JGroupValidator("Please fix the following errors:", true);
		JValidator validator1= groupValidator.addValidator(new JRequiredFieldValidator(firstNameField, "*", "Enter the first name", true));
		JValidator validator2= groupValidator.addValidator(new JRangeValidator(lastNameField, "*", "First and last name are equal!", true, "245", "247", JCompareValidator.Type.STRING));
		JValidator validator3= groupValidator.addValidator(new JRegularExpressionValidator(emailField, "*", "Enter a valid email address", true, "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"));

		JButton saveButton= new JButton("save");
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (groupValidator.doValidation())
				{
					user.setFirstName(firstNameField.getText());
					user.setLastName(lastNameField.getText());
					user.setLastName(lastNameField.getText());
					WosFramework.hide(UserDetails.this);
				}
			}
		});

		JPanel contentPane= new JPanel(new GridLayout(4, 2));
		contentPane.add(firstNameField);
		contentPane.add(validator1);
		contentPane.add(lastNameField);
		contentPane.add(validator2);
		contentPane.add(emailField);
		contentPane.add(validator3);
		contentPane.add(saveButton);
		contentPane.add(groupValidator);

		/*
		 * for (int i= 0; i < 60; i++) { JButton button= new JButton(i + "");
		 * contentPane.add(button); }
		 */
		setContentPane(contentPane);

		firstNameField.setName("firstName");
		lastNameField.setName("lastName");
		emailField.setName("email");
		validator1.setName("validator1");
		validator2.setName("validator2");
		validator3.setName("validator3");
		saveButton.setName("saveButton");
		groupValidator.setName("groupValidator");
		contentPane.setName("contentPane");

		WosFramework.assignContributor(this, UserDetailsContributor.class);

		if (WosFramework.isActive())
			contentPane.setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("UserDetails"));

	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User aUser)
	{
		user= aUser;
	}
}