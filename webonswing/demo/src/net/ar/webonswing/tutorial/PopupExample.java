package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.remote.*;

public class PopupExample extends JDialog
{
	static int number;

	protected final class OpenDialogAction implements ActionListener, RemoteListener
	{
		public void actionPerformed(ActionEvent e)
		{
			PopupExample popupExample= new PopupExample();
			popupExample.setModal(true);
			WosFramework.showChildWindow(PopupExample.this, popupExample);
		}

		public String getRemoteName()
		{
			return "OpenDialogListener";
		}

		public Object[] getRemoteParameters()
		{
			return new Object[] { number + "" };
		}
	}

	public PopupExample()
	{
		JButton button= new JButton("open " + number);
		button.addActionListener(new OpenDialogAction());

		final JButton incrementButton= new JButton("increment " + number);
		incrementButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				number++;
				incrementButton.setText("increment " + number);
			}
		});

		final JButton closeButton= new JButton("close");
		closeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WosFramework.hide(PopupExample.this);
			}
		});
		
		getContentPane().setLayout(new GridLayout(3, 1));
		getContentPane().add(button);
		getContentPane().add(incrementButton);
		getContentPane().add(closeButton);
		
		Dimension dimension= new Dimension(100, 80);
		getContentPane().setSize(dimension);
		getRootPane().setSize(dimension);
		

		WosFramework.assignContributor(this, PopupExampleContributor.class);
	}
}