
package net.ar.webonswing.petstore;

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.petstore.helpers.*;
import net.ar.webonswing.petstore.model.*;
import net.ar.webonswing.swing.components.validators.*;

public class CustomerView extends JPanel
{
	protected Customer customer;

	protected JTextField firstName=new JTextField();
	protected JTextField lastName= new JTextField();
	protected JTextField email= new JTextField();
	protected JTextField telephone= new JTextField();
	protected JComponent username=new JTextField();
	protected JPasswordField password= new JPasswordField();
	protected JPasswordField repeatPassword= new JPasswordField();

	protected AddressView address= new AddressView();
	protected CreditCardView creditCard= new CreditCardView();

	protected Binder binder= new Binder();

	protected Collection validators;

	public CustomerView()
	{
		init();
	}

	public CustomerView(Customer aCustomer, boolean isEditing)
	{
		if (isEditing)
			username.setEnabled(false);

		init();
		setCustomer(aCustomer);
	}

	protected void init()
	{
		binder.add(firstName, "firstname");
		binder.add(lastName, "lastname");
		binder.add(email, "email");
		binder.add(telephone, "telephone");
		binder.add(creditCard, "creditCard", "creditCard");
		binder.add(address, "address", "address");
		binder.add(username, "username");
		binder.add(password, "password");

		binder.addViewsToContainer(this);
		binder.modelToView();

		JRegularExpressionValidator emailValidator= new JRegularExpressionValidator(email, "*", "Email is incorrect", true, "^[\\w-]+@[\\w-]+\\.(com|net|org|edu|mil)$");
		JRegularExpressionValidator telephoneValidator= new JRegularExpressionValidator(telephone, "*", "Phone number must be in form: (XXX) XXX-XXXX", true, "(^x\\s*[0-9]{5}$)|(^(\\([1-9][0-9]{2}\\)\\s)?[1-9][0-9]{2}-[0-9]{4}(\\sx\\s*[0-9]{5})?$)");
		JValidator usernameValidator= new JRequiredFieldValidator(username);
		JValidator passwordValidator= new JRegularExpressionValidator(password, "*", "Password must include one of these (!@#$%^&*+;:)", true, ".*[!@#$%^&*+;:].*");
		JValidator repeatPasswordValidator= new JCompareValidator(repeatPassword, "*", "Password fields dont match", true, password);

		add(emailValidator).setName("emailValidator");
		add(telephoneValidator).setName("telephoneValidator");
		add(usernameValidator).setName("usernameValidator");
		add(passwordValidator).setName("passwordValidator");
		add(repeatPasswordValidator).setName("repeatPasswordValidator");
		add(repeatPassword).setName("repeatPassword");

		validators= new Vector(Arrays.asList(new JValidator[]{emailValidator, telephoneValidator, usernameValidator, passwordValidator, repeatPasswordValidator}));
		validators.addAll(creditCard.getValidators());
	}
	public Customer getCustomer()
	{
		binder.viewToModel();
		return customer;
	}

	public void setCustomer(Customer aCustomer)
	{
		this.customer= aCustomer;
		binder.setModel(aCustomer);
		binder.modelToView();
	}

	public Collection getValidators()
	{
		return validators;
	}

	public Binder getBinder()
	{
		return binder;
	}
}