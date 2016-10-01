
package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;

public class StatePersistenceExample extends JDialog
{
	public StatePersistenceExample()
	{
		final JLabel label= new JLabel("Hello, World!");

		JButton changeButton= new JButton("Change Message");
		changeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent aE)
			{
				label.setText("Goodbye, Everyone!");
			}
		});

		JButton dummyButton= new JButton("Dummy Button");
		dummyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent aE)
			{
			}
		});
		
		getContentPane().setLayout(new GridLayout(0, 1));
		getContentPane().add(label);
		getContentPane().add(changeButton);
		getContentPane().add(dummyButton);

		WosFramework.assignContributor(label, LabelPersistenceContributor.class);
	}
}