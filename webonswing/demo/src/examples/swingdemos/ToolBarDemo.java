package examples.swingdemos;

import java.awt.*;

import javax.swing.*;

public class ToolBarDemo extends JFrame
{
	JToolBar jToolBar1= new JToolBar();
	JButton jButton1= new JButton();
	JRadioButton jRadioButton1= new JRadioButton();
	JLabel jLabel1= new JLabel();
	JTextArea jTextArea1= new JTextArea();
	JButton jButton2= new JButton();

	public ToolBarDemo()
	{
		jbInit();
	}
	private void jbInit()
	{
		this.getContentPane().setLayout(new GridLayout(0, 1));

		jButton1.setText("jButton1");
		jRadioButton1.setText("jRadioButton1");
		jLabel1.setText("jLabel1");
		jTextArea1.setText("jTextArea1");
		jButton2.setText("jButton2");

		jToolBar1.add(jButton1, null);
		jToolBar1.add(jButton2, null);
		jToolBar1.add(jTextArea1, null);
		jToolBar1.add(jRadioButton1, null);
		jToolBar1.add(jLabel1, null);

		this.getContentPane().add(jToolBar1);
	}

	public static void main(String[] args)
	{
		ToolBarDemo frame= new ToolBarDemo();
		frame.pack();
		frame.setVisible(true);
	}

}
