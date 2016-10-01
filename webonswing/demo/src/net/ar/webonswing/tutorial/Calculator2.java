package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.layouts.*;

public class Calculator2 extends JDialog implements ActionListener
{
  protected JLabel resultLabel;
  protected JTextField firstNumerField;
  protected JTextField secondNumerField;
  protected JComboBox operationCombo;
  
  public Calculator2()
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
}       
