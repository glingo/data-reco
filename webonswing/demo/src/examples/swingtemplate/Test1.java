package examples.swingtemplate;
import javax.swing.*;

import net.ar.webonswing.render.templates.swing.*;
import net.ar.webonswing.swing.layouts.*;

public class Test1 extends JFrame
{
	public Test1()
	{
		JPanel contentPane= (JPanel) getContentPane();
		contentPane.setLayout(new TemplateLayout(new KeyPositionSwingTemplate(new TestTemplateLayout1().getContentPane())));
		
		contentPane.add(new JButton("hola"), "ph1");
		contentPane.add(new JCheckBox("check1"), "ph2");
		contentPane.add(new JRadioButton("radio1"), "ph3");
	}

	public static void main(String[] args)
	{
		Test1 test1= new Test1();
		test1.setVisible(true);
	}
}
