package net.ar.webonswing.tutorial;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.ar.webonswing.WosFramework;
import net.ar.webonswing.remote.RemoteListener;
import net.ar.webonswing.swing.layouts.TemplateLayout;

public class RemoteCalculator extends JDialog implements ActionListener, RemoteListener
{
	protected JLabel resultLabel;
	protected JTextField firstNumerField;
	protected JTextField secondNumerField;
	protected JComboBox operationCombo;

	public RemoteCalculator()
	{
		Container contentPane= getContentPane();

		resultLabel= new JLabel("result");
		firstNumerField= new JTextField("");
		secondNumerField= new JTextField("");
		operationCombo= new JComboBox(new String[] { "+", "-", "*", "/"});
		JButton processButton= new JButton("=");

		processButton.addActionListener(this);

		contentPane.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("CalculatorTemplate")));
		contentPane.add(firstNumerField, "firstNumber");
		contentPane.add(operationCombo, "operation");
		contentPane.add(secondNumerField, "secondNumber");
		contentPane.add(resultLabel, "result");
		contentPane.add(processButton, "button");
		
		WosFramework.assignContributor(this, CalculatorContributor.class);
	}

	public void actionPerformed(ActionEvent e)
	{
		float firstNumber= Float.parseFloat(firstNumerField.getText());
		float secondNumber= Float.parseFloat(secondNumerField.getText());
		float result= 0;

		switch (operationCombo.getSelectedItem().toString().charAt(0))
		{
			case '+':
				result= firstNumber + secondNumber;
				break;
			case '-':
				result= firstNumber - secondNumber;
				break;
			case '*':
				result= firstNumber * secondNumber;
				break;
			case '/':
				result= firstNumber / secondNumber;
				break;
		}

		resultLabel.setText(Float.toString(result));
	}

	public String getRemoteName()
	{
		return "CalculatorActionListener";
	}

	public Object[] getRemoteParameters()
	{
		return new Object[]{resultLabel, firstNumerField, secondNumerField, operationCombo};
	}
}