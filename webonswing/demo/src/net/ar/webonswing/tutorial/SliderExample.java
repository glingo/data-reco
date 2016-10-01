package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SliderExample extends JDialog
{
	public SliderExample()
	{
		final JSlider slider= new JSlider(SwingConstants.HORIZONTAL, 1, 100, 20);
		final JButton button= new JButton("submit");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				button.setText(slider.getValue() + "");
			}
		});

		getContentPane().setLayout(new GridLayout(2, 1));
		getContentPane().add(slider);
		getContentPane().add(button);
		Dimension dimension= new Dimension(200, 200);
		getContentPane().setSize(dimension);
		getRootPane().setSize(dimension);
	}
}