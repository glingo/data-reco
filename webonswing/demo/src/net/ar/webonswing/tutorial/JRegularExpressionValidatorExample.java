package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.components.validators.*;

public class JRegularExpressionValidatorExample extends JDialog
{
	public JRegularExpressionValidatorExample()
	{
		final JLabel label= new JLabel();

		JTextField component1= new JTextField();
		JTextField component2= new JTextField();
		JTextField component3= new JTextField();

		JValidator requiredFieldValidator1= new JRequiredFieldValidator(component1);
		JValidator requiredFieldValidator2= new JRequiredFieldValidator(component2);
		JValidator requiredFieldValidator3= new JRequiredFieldValidator(component3);
		
		JValidator regularExpressionValidator1= new JRegularExpressionValidator(component1, "", "Please enter a valid e-mail address", false, "^[\\w-]+@[\\w-]+\\.(com|net|org|edu|mil)$");
		JValidator regularExpressionValidator2= new JRegularExpressionValidator(component2, "", "Phone number must be in form: (XXX) XXX-XXXX", false, "(^x\\s*[0-9]{5}$)|(^(\\([1-9][0-9]{2}\\)\\s)?[1-9][0-9]{2}-[0-9]{4}(\\sx\\s*[0-9]{5})?$)");
		JValidator regularExpressionValidator3= new JRegularExpressionValidator(component3, "", "Zip code must be 5 numeric digits", false, "^\\d{5}$");
		
		final JGroupValidator groupValidator= new JGroupValidator(new JValidator[]{requiredFieldValidator1, requiredFieldValidator2, requiredFieldValidator3, regularExpressionValidator1, regularExpressionValidator2, regularExpressionValidator3}, false);
		
		
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

		getContentPane().add(component1).setName("component1");
		getContentPane().add(component2).setName("component2");
		getContentPane().add(component3).setName("component3");
		getContentPane().add(requiredFieldValidator1).setName("requiredFieldValidator1");
		getContentPane().add(requiredFieldValidator2).setName("requiredFieldValidator2");
		getContentPane().add(requiredFieldValidator3).setName("requiredFieldValidator3");
		getContentPane().add(regularExpressionValidator1).setName("regularExpressionValidator1");
		getContentPane().add(regularExpressionValidator2).setName("regularExpressionValidator2");
		getContentPane().add(regularExpressionValidator3).setName("regularExpressionValidator3");
		getContentPane().add(button).setName("button");
		getContentPane().add(groupValidator).setName("groupValidator");
		getContentPane().add(label).setName("label");

		getContentPane().setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("JRegularExpressionValidatorExample.main"));
	}
}