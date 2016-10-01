package net.ar.webonswing.tutorial;

import javax.swing.*;

import net.ar.webonswing.*;

public class PropagateTemplatesExample extends JDialog
{
	public PropagateTemplatesExample()
	{
		JLabel label= new JLabel("label");
		JPanel panel1= new JPanel();
		JButton button= new JButton("button");
		JPanel panel2= new JPanel();
		JCheckBox checkButton= new JCheckBox("check button");

		label.setName("label");
		button.setName("button");
		checkButton.setName("checkButton");
		panel1.setName("panel1");
		panel2.setName("panel2");

		panel1.add(button);
		panel1.add(panel2);
		panel2.add(checkButton);
		getContentPane().add(label);
		getContentPane().add(panel1);
		getContentPane().setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("PropagateTemplatesExample2"));
	}
}