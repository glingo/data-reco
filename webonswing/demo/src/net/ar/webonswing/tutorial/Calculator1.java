
package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Calculator1 extends JDialog implements ActionListener
{
	protected JLabel resultLabel;
	protected JTextField firstNumerField;
	protected JTextField secondNumerField;
	protected JComboBox operationCombo;

	public Calculator1()
	{
		Container contentPane= getContentPane();

		resultLabel= new JLabel("result");
		firstNumerField= new JTextField("");
		secondNumerField= new JTextField("");
		operationCombo= new JComboBox(new String[]{"+", "-", "*", "/"});
		JButton processButton= new JButton("=");

		processButton.addActionListener(this);

		contentPane.setLayout(new GridLayout(2, 3));
		contentPane.add(firstNumerField);
		contentPane.add(operationCombo);
		contentPane.add(secondNumerField);
		contentPane.add(resultLabel);
		contentPane.add(processButton);

		pack();
	}

	public void actionPerformed(ActionEvent e)
	{
		float firstNumber= Float.parseFloat(firstNumerField.getText());
		float secondNumber= Float.parseFloat(secondNumerField.getText());
		float result= 0;

		switch (operationCombo.getSelectedItem().toString().charAt(0))
		{
			case '+' :
				result= firstNumber + secondNumber;
				break;
			case '-' :
				result= firstNumber - secondNumber;
				break;
			case '*' :
				result= firstNumber * secondNumber;
				break;
			case '/' :
				result= firstNumber / secondNumber;
				break;
		}

		resultLabel.setText(Float.toString(result));
	}
}