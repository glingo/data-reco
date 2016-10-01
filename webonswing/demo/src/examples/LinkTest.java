package examples;
import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.swing.components.*;

public class LinkTest extends JFrame
{
	public LinkTest()
	{
		getContentPane().setLayout(new BorderLayout());
		
		JLink link= new JLink("aaaa", "agfdag");
		getContentPane().add(link, BorderLayout.NORTH);
		getContentPane().add(new JButton("button"), BorderLayout.SOUTH);
		
		setSize(600, 400);
	}
	
	public static void main(String[] args)
	{
		LinkTest l= new LinkTest();
		l.setVisible(true);
	}
}
