package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;

public class PartialUpdateTest1 extends JDialog
{
	private int counter= 0;

	public PartialUpdateTest1()
	{
		JButton button= new JButton("open dialog");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				PartialUpdateTest1 partialUpdateTest1= new PartialUpdateTest1();
				partialUpdateTest1.setModal(true);
				WosFramework.showChildWindow(PartialUpdateTest1.this, partialUpdateTest1);
			}
		});

		final JButton countButton= new JButton(getCounterText());
		countButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				counter++;
				countButton.setText(getCounterText());
			}
		});

		final JButton closeButton= new JButton("close");
		closeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WosFramework.hide(PartialUpdateTest1.this);
			}
		});
		
		getContentPane().add(button);
		getContentPane().add(countButton);
		getContentPane().add(closeButton);
		getContentPane().setLayout(new GridLayout(3, 1));
		Dimension dimension= new Dimension(100, 80);
		getContentPane().setSize(dimension);
		getRootPane().setSize(dimension);
	}

	private String getCounterText()
	{
		return "counter: " + counter;
	}
}