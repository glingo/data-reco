
package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.swing.components.validators.*;

public class ValidatorsExample extends JDialog
{
	public ValidatorsExample()
	{
		JComponent email= addNewComponent(new JTextField(), "email");
		JComponent password= addNewComponent(new JPasswordField(), "password");
		JComponent reenterPassword= addNewComponent(new JPasswordField(), "reenterPassword");
		JComponent zip= addNewComponent(new JTextField(), "zip");
		JComponent phone= addNewComponent(new JTextField(), "phone");
		JComponent cardNumber= addNewComponent(new JTextField(), "cardNumber");
		JComponent expirationCombo= addNewComponent(new JComboBox(new String[]{"", "06/04", "07/04", "08/04", "09/04", "10/04", "11/04", "12/04", "01/05", "02/05", "03/05", "04/05", "05/05", "06/05", "07/05", "08/05", "09/05", "10/05", "11/05", "12/05"}), "expirationCombo");

		addNewComponent(new JTextField(), "firstName");
		addNewComponent(new JTextField(), "lastName");
		addNewComponent(new JTextField(), "address");
		addNewComponent(new JTextField(), "state");

		JRadioButton cardType1= (JRadioButton) addNewComponent(new JRadioButton("Mastercard"), "cardType1");
		JRadioButton cardType2= (JRadioButton) addNewComponent(new JRadioButton("Visa"), "cardType2");
		ButtonGroup cardsGroup= new ButtonGroup();
		cardsGroup.add(cardType1);
		cardsGroup.add(cardType2);

		JValidator emailRequiredValidator= new JRequiredFieldValidator(email);
		JValidator passwordRequiredValidator= new JRequiredFieldValidator(password);
		JValidator reenterPasswordRequiredValidator= new JRequiredFieldValidator(reenterPassword);
		JValidator zipRequiredValidator= new JRequiredFieldValidator(zip);
		JValidator phoneRequiredValidator= new JRequiredFieldValidator(phone);
		JValidator cardsRequiredFieldValidator= new JRequiredFieldValidator(cardType1);
		JValidator expirationRequiredFieldValidator= new JRequiredFieldValidator(expirationCombo);
		JValidator numberRequiredFieldValidator= new JRequiredFieldValidator(cardNumber);

		JValidator emailValidator= new JRegularExpressionValidator(email, "", "Not a valid e-mail address.  Must follow email@host.domain.", false, "^[\\w-]+@[\\w-]+\\.(com|net|org|edu|mil)$");
		JValidator passwordValidator= new JRegularExpressionValidator(password, "", "Password must include one of these (!@#$%^&amp;*+;:)", false, ".*[!@#$%^&*+;:].*");
		JValidator reenterPasswordValidator= new JCompareValidator(reenterPassword, "", "Password fields dont match", false, password);
		JValidator zipValidator= new JRegularExpressionValidator(zip, "", "Zip code must be 5 numeric digits", false, "^\\d{5}$");
		JValidator phoneValidator= new JRegularExpressionValidator(phone, "", "Phone number must be in form: (XXX) XXX-XXXX", false, "(^x\\s*[0-9]{5}$)|(^(\\([1-9][0-9]{2}\\)\\s)?[1-9][0-9]{2}-[0-9]{4}(\\sx\\s*[0-9]{5})?$)");

		final JGroupValidator groupValidator= new JGroupValidator(new JValidator[]{emailRequiredValidator, passwordRequiredValidator, reenterPasswordRequiredValidator, zipRequiredValidator, phoneRequiredValidator, cardsRequiredFieldValidator, expirationRequiredFieldValidator, numberRequiredFieldValidator, emailValidator, passwordValidator, reenterPasswordValidator, zipValidator, phoneValidator}, true);
		addAllValidators(groupValidator);

		final JLabel label= new JLabel();

		JButton button= new JButton("Validate");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (groupValidator.doValidation())
					label.setText("Page is Valid!");
				else
					label.setText("Not valid!");
			}
		});

		getContentPane().setLayout(new GridLayout(0, 1));

		getContentPane().add(groupValidator).setName("groupValidator");
		getContentPane().add(button).setName("button");
		getContentPane().add(label).setName("outputLabel");

		getContentPane().setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("ValidatorsExample.main"));
	}

	protected JComponent addNewComponent(JComponent aComponent, String aName)
	{
		getContentPane().add(aComponent).setName(aName);
		return aComponent;
	}

	protected void addAllValidators(JGroupValidator aGroupValidator)
	{
		for (Iterator i= aGroupValidator.getValidators().iterator(); i.hasNext();)
		{
			JValidator validator= (JValidator) i.next();
			validator.setRemoteValidation(true);
			getContentPane().add(validator).setName(validator.getComponentToValidate().getName() + WosHelper.getNoPackageClassName(validator));
		}
	}
}