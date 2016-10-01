package net.ar.webonswing.petstore;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.contributors.*;
import net.ar.webonswing.petstore.model.*;
import net.ar.webonswing.swing.components.validators.*;

public class AccountView extends ApplicationFrame implements ActionListener
{
	protected Customer accountOwner= new Customer();
	private Collection validators;
	protected JGroupValidator groupValidator;
	protected CustomerView customerView;
	private boolean editingCustomer= false;

	public AccountView()
	{
		WosFramework.assignContributor(this, new HibernateObjectPersistenceContributor("accountOwner"));
	}

	public AccountView(Customer aCustomer)
	{
		this(aCustomer, true);
	}

	public AccountView(Customer aCustomer, boolean isEditingCustomer)
	{
		this();
		setAccountOwner(aCustomer);
		editingCustomer= isEditingCustomer;
		init();
	}

	protected JComponent getBody()
	{
		customerView= new CustomerView(accountOwner, editingCustomer);
		customerView.setLayout(getCurrentSkin().getCurrentPropagateTemplateFor("CustomerView"));
		groupValidator= new JGroupValidator();
		JButton saveButton= new JButton("save");
		saveButton.addActionListener(this);

		groupValidator.addValidators(customerView.getValidators());
		groupValidator.addShooter(saveButton);

		JValidator registrationValidator= new JValidator()
		{
			public boolean isRemoteValidation()
			{
				return false;
			}

			protected boolean performValidation()
			{
				customerView.getBinder().viewToModel();

				if (!editingCustomer)
				{
					List customers= getDao().getCustomers(accountOwner.getUsername());
					if (!customers.isEmpty())
						return false;
				}

				getDao().saveOrUpdate(accountOwner);

				return true;
			}
		};

		groupValidator.addValidator(registrationValidator);

		JPanel panel= new JPanel();
		panel.add(customerView).setName("customerView");
		panel.add(groupValidator).setName("groupValidator");
		panel.add(saveButton).setName("button");

		panel.setLayout(getCurrentSkin().getCurrentPropagateTemplateFor("Register.main"));

		return panel;
	}

	public Collection getValidators()
	{
		return validators;
	}

	public void actionPerformed(ActionEvent e)
	{
		if (groupValidator.doValidation())
		{
			Window nextWindow= null;

			if (editingCustomer)
				nextWindow= new Home();
			else
				nextWindow= new LoginPage();

			WosFramework.showChildWindow(this, nextWindow);
		}
	}

	public boolean isEditingCustomer()
	{
		return editingCustomer;
	}

	public void setEditingCustomer(boolean aEditingCustomer)
	{
		editingCustomer= aEditingCustomer;
	}

	public Customer getAccountOwner()
	{
		return accountOwner;
	}

	public void setAccountOwner(Customer aAccountOwner)
	{
		accountOwner= aAccountOwner;
		editingCustomer= true;
		setCurrentCustomer(aAccountOwner);
	}
}

