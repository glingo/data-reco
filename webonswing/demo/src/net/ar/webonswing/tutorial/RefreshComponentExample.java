
package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;

public class RefreshComponentExample extends JDialog
{
	public RefreshComponentExample()
	{
		JProgressBar progressBar= new JProgressBar(1, 100);
		progressBar.setValue(updateValue().intValue());
		
		JButton button= new JButton("Reset");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent aE)
			{
				WosFramework.getSessionContext().put("value", new Integer(1));
			}
		});

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(progressBar, BorderLayout.NORTH);
		getContentPane().add(button, BorderLayout.SOUTH);
		Dimension dimension= new Dimension(200, 100);
		getContentPane().setSize(dimension);
		getRootPane().setSize(dimension);
	}

	protected Integer updateValue()
	{
		Integer value= (Integer) WosFramework.getSessionContext().get("value");
		if (value == null)
			value= new Integer(1);
		value= new Integer(value.intValue() + 1);
		WosFramework.getSessionContext().put("value", value);
		
		return value;
	}
}