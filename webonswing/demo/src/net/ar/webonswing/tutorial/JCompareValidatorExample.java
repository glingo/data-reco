package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.components.validators.*;

public class JCompareValidatorExample extends JDialog
{
	public JCompareValidatorExample()
	{
		final JLabel label= new JLabel();

		JTextField component1= new JTextField();
		JTextField component2= new JTextField();

		final JCompareValidator compareValidator= new JCompareValidator(component1, "", "", false, component2, JCompareValidator.Operation.equal, JCompareValidator.Type.STRING);

		final JList operationList= new JList(new Object[] { JCompareValidator.Operation.equal, JCompareValidator.Operation.notEqual, JCompareValidator.Operation.greaterThan, JCompareValidator.Operation.greaterThanEqual, JCompareValidator.Operation.lessThan, JCompareValidator.Operation.lessThanEqual});

		JButton button= new JButton("Validate");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				compareValidator.setOperation((JCompareValidator.Operation)operationList.getSelectedValue());

				if (compareValidator.doValidation())
					label.setText("Page is Valid!");
				else
					label.setText("Not valid!");
			}
		});

		getContentPane().setLayout(new GridLayout(0, 1));

		getContentPane().add(label).setName("label1");
		getContentPane().add(component1).setName("component1");
		getContentPane().add(component2).setName("component2");
		getContentPane().add(operationList).setName("operationList");
		getContentPane().add(compareValidator).setName("compareValidator");
		getContentPane().add(button).setName("button");

		getContentPane().setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("JCompareValidatorExample.main"));
	}
}