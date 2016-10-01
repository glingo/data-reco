
package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.components.validators.*;

public class JGroupValidatorExample extends JDialog
{
	public JGroupValidatorExample()
	{
		final JLabel label= new JLabel();

		JRadioButton card1= new JRadioButton("Mastercard");
		JRadioButton card2= new JRadioButton("Visa");
		ButtonGroup cardsGroup= new ButtonGroup();
		cardsGroup.add(card1);
		cardsGroup.add(card2);

		JTextField cardNumberField= new JTextField();

		JComboBox expirationCombo= new JComboBox(new String[]{"", "06/04", "07/04", "08/04", "09/04", "10/04", "11/04", "12/04", "01/05", "02/05", "03/05", "04/05", "05/05", "06/05", "07/05", "08/05", "09/05", "10/05", "11/05", "12/05"});

		final JGroupValidator groupValidator= new JGroupValidator();

		final JValidator cardsRequiredFieldValidator= new JRequiredFieldValidator(card1, "*", "Card Type.", true);
		final JValidator expirationRequiredFieldValidator= new JRequiredFieldValidator(expirationCombo, "*", "Card Number. ", true);
		final JValidator numberRequiredFieldValidator= new JRequiredFieldValidator(cardNumberField, "*", "Expiration Date. ", true);

		groupValidator.addValidator(cardsRequiredFieldValidator);
		groupValidator.addValidator(expirationRequiredFieldValidator);
		groupValidator.addValidator(numberRequiredFieldValidator);

		JButton button= new JButton("Validate");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (groupValidator.doValidation())
					label.setText("Page is Valid!");
				else
					label.setText("Some of the required fields are empty");
			}
		});

		getContentPane().setLayout(new GridLayout(0, 1));

		getContentPane().add(label).setName("label1");
		getContentPane().add(card1).setName("card1");
		getContentPane().add(card2).setName("card2");
		getContentPane().add(cardsRequiredFieldValidator).setName("cardsValidator");
		getContentPane().add(cardNumberField).setName("number");
		getContentPane().add(numberRequiredFieldValidator).setName("numberValidator");
		getContentPane().add(expirationCombo).setName("expirationCombo");
		getContentPane().add(expirationRequiredFieldValidator).setName("expirationValidator");
		getContentPane().add(button).setName("button");
		getContentPane().add(groupValidator).setName("groupValidator");

		getContentPane().setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("JGroupValidatorExample.main"));
	}
}