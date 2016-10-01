package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.components.validators.*;

public class JRangeValidatorExample extends JDialog
{
	public JRangeValidatorExample()
	{
		final JLabel label= new JLabel();

		JTextField component1= new JTextField();
		JTextField component2= new JTextField();
		JTextField component3= new JTextField();

		final JRangeValidator rangeValidator1= new JRangeValidator(component1, "Not a valid number!", "", false, "1", "10", JCompareValidator.Type.INTEGER);
		final JRangeValidator rangeValidator2= new JRangeValidator(component2, "Not a valid date!", "", false, "2000/01/01", "2001/01/01", JCompareValidator.Type.DATE("yyyy/MM/dd"));
		final JRangeValidator rangeValidator3= new JRangeValidator(component3, "Not a valid string!", "", false, "Aardvark", "Zebra", JCompareValidator.Type.STRING);

		JButton button= new JButton("Validate");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (rangeValidator1.doValidation() & rangeValidator2.doValidation() & rangeValidator3.doValidation())
					label.setText("Page is Valid!");
				else
					label.setText("Not valid!");
			}
		});

		
		getContentPane().setLayout(new GridLayout(0, 1));

		getContentPane().add(component1).setName("component1");
		getContentPane().add(component2).setName("component2");
		getContentPane().add(component3).setName("component3");
		getContentPane().add(rangeValidator1).setName("validator1");
		getContentPane().add(rangeValidator2).setName("validator2");
		getContentPane().add(rangeValidator3).setName("validator3");
		getContentPane().add(button).setName("button");
		getContentPane().add(label).setName("outputLabel");

		getContentPane().setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("JRangeValidatorExample.main"));
	}
}