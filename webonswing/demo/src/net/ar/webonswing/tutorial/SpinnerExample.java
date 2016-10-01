package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SpinnerExample extends JDialog
{
	public SpinnerExample()
	{
		final JSpinner spinner= new JSpinner();
		spinner.setValue(new Integer(10));
		
		final JButton button= new JButton("submit");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				button.setText(spinner.getValue() + "");
			}
		});

		getContentPane().setLayout(new GridLayout(2, 1));
		getContentPane().add(spinner);
		getContentPane().add(button);
		Dimension dimension= new Dimension(100, 80);
		getContentPane().setSize(dimension);
		getRootPane().setSize(dimension);
	}
}