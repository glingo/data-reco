package net.ar.webonswing.tutorial;

import javax.swing.*;

import examples.*;

public class InternalFrameTest extends JDialog
{
	public InternalFrameTest()
	{
		JDesktopPane desktopPane= new JDesktopPane();

		JInternalFrame internalFrame= new JInternalFrame("title1");
		JInternalFrame internalFrame2= new JInternalFrame("title2");

		internalFrame.setContentPane(new ListDemo.ListDemoPanel());
		internalFrame2.setContentPane(new ContainerEventDemo.ContainerEventDemoPanel());

		internalFrame.setBounds(10, 10, 200, 200);
		internalFrame2.setBounds(40, 70, 200, 200);

		desktopPane.add(internalFrame, 1);
		desktopPane.add(internalFrame2, 2);

		getContentPane().add(desktopPane);
	}
}