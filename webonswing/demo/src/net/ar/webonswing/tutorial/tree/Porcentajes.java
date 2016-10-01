package net.ar.webonswing.tutorial.tree;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import net.ar.webonswing.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.swing.components.*;

public class Porcentajes extends JFrame
{
	protected JTextField textField;

	public Porcentajes()
	{
		super("CheckNode TreeExample");
		String[] strs= {"swing", "platf", "basic", "metal", "JTree"};

		CheckNode[] nodes= new CheckNode[strs.length];
		for (int i= 0; i < strs.length; i++)
			nodes[i]= new CheckNode(strs[i]);

		nodes[0].add(nodes[1]);
		nodes[1].add(nodes[2]);
		nodes[1].add(new CheckNode("leaf1"));
		nodes[1].add(new CheckNode("leaf2"));
		nodes[1].add(new CheckNode("leaf3"));
		nodes[1].add(nodes[3]);
		nodes[2].add(new CheckNode("leaf4"));
		nodes[2].add(new CheckNode("leaf5"));
		nodes[3].add(new CheckNode("leaf6"));
		nodes[0].add(nodes[4]);
		nodes[0].add(new CheckNode("leaf7"));
		nodes[0].add(new CheckNode("leaf8"));
		nodes[3].setSelected(true);

		final JTree tree= new JTree(nodes[0]);
		tree.setCellRenderer(new ComponentStructureCellRenderer(tree));
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.putClientProperty("JTree.lineStyle", "Angled");

		tree.addTreeSelectionListener(new CheckTreeSelectionListener());

		tree.setLayout(WosFramework.getTemplateLayoutForName("TreeDemo.JTree"));

		textField= new JTextField();
		JButton button= new JButton("Grabar");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent aE)
			{
				CheckNode node= (CheckNode) tree.getLastSelectedPathComponent();
				node.setUserObject(textField.getText());
			}
		});
		
		JPanel thePanel= (JPanel) getContentPane();
		thePanel.setLayout(WosFramework.getTemplateLayoutForName("TreeDemo.theDemo"));
		thePanel.add(tree, "theTree");
		thePanel.add(textField, "theTextField");
		thePanel.add(button, "theButton");
	}

	public class CheckTreeSelectionListener implements TreeSelectionListener, RemoteListener
	{
		public void valueChanged(TreeSelectionEvent e)
		{
			String theNewWindowName= e.getNewLeadSelectionPath().getLastPathComponent().toString();
			textField.setText(theNewWindowName);
		}
		public String getRemoteName()
		{
			return "PorcentajesTreeSelectionListener";
		}
		public Object[] getRemoteParameters()
		{
			return new Object[0];
		}
	}
}