// WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package examples.withtemplate;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.tree.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.swing.layouts.*;

public class TreeDemoWindow extends JFrame
{
	public TreeDemoWindow()
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
		tree.setCellRenderer(new CheckRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.putClientProperty("JTree.lineStyle", "Angled");

		tree.addTreeSelectionListener(new CheckTreeSelectionListener(tree));

		//tree.addMouseListener(new NodeSelectionListener(tree));
		tree.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("TreeDemo.JTree")));

		JPanel thePanel= (JPanel) getContentPane();
		thePanel.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForName("TreeDemo.theDemo")));
		getContentPane().add(tree, "theTree");

		DefaultListModel theListModel= new DefaultListModel();
		theListModel.addElement("list element 1");
		theListModel.addElement("list element 2");
		theListModel.addElement("list element 3");
		theListModel.addElement("list element 4");

		JList theList= new JList(theListModel);
		//theList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		theList.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (!e.getValueIsAdjusting())
					System.out.println(((JList) e.getSource()).getSelectedValue());
			}
		});

		getContentPane().add(theList, "theTextField");

		getContentPane().add(new TemplateRadioButtonDemo.RadioButtonDemoPanel(), "theRadioDemo");
	}

	public static class CheckTreeSelectionListener implements TreeSelectionListener, RemoteListener
	{
		private final JTree tree;

		public CheckTreeSelectionListener(JTree aTree)
		{
			super();
			this.tree= aTree;
		}
		public void valueChanged(TreeSelectionEvent e)
		{
			TreePath path= e.getNewLeadSelectionPath();

			if (e.getNewLeadSelectionPath().getLastPathComponent() instanceof CheckNode)
			{
				CheckNode node= (CheckNode) e.getNewLeadSelectionPath().getLastPathComponent();
				boolean isSelected= !(node.isSelected());

				node.setSelected(isSelected);

				if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION)
				{
					if (isSelected)
						tree.expandPath(path);
					else
						tree.collapsePath(path);
				}
				((DefaultTreeModel) tree.getModel()).nodeChanged(node);
			}
		}
		public String getRemoteName()
		{
			return "CheckTreeSelectionListener";
		}
		public Object[] getRemoteParameters()
		{
			return new Object[0];
		}
	}

	static public class CheckRenderer implements TreeCellRenderer
	{
		public Component getTreeCellRendererComponent(JTree theTree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus)
		{
			MyCell theCell= null;

			if (value instanceof CheckNode)
			{
				TreeNode theRootNode= (TreeNode) theTree.getModel().getRoot();
				CheckNode theCheckNode= (CheckNode) value;

				Template theTemplate= WosSwingHelper.getTemplateForComponent("JTree", theTree);

				JCheckBox theCheck= new JCheckBox();
				JLabel theLabel= new JLabel();

				String theNodeType= theRootNode == theCheckNode ? "theRootNode" : leaf ? "theLeafNode" : expanded ? "theExpandedNode" : "theCollapsedNode";

				theCheck.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForSubTemplate(theTemplate, theNodeType + ".theTreeCell.theContent.theCheck")));
				theLabel.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForSubTemplate(theTemplate, theNodeType + ".theTreeCell.theContent.theLabel")));

				theCell= new MyCell(new TemplateLayout(WosFramework.getKeyPositionTemplateForSubTemplate(theTemplate, theNodeType + ".theTreeCell.theContent")), theCheck, "theCheck", theLabel, "theLabel");

				String stringValue= theTree.convertValueToText(value, isSelected, expanded, leaf, row, hasFocus);

				theCell.setEnabled(theTree.isEnabled());
				theCell.theCheck.setSelected(theCheckNode.isSelected());
				theCell.theLabel.setText(stringValue);

				if (leaf)
				{
					theCell.theLabel.setIcon(new ImageIcon("resources/images/tree/page.gif"));
				}
				else if (expanded)
				{
					theCell.theLabel.setIcon(new ImageIcon("resources/images/tree/folderopen.gif"));
				}
				else
				{
					theCell.theLabel.setIcon(new ImageIcon("resources/images/tree/folder.gif"));
				}
			}

			return theCell;
		}

		class MyCell extends JPanel
		{
			JCheckBox theCheck;
			JLabel theLabel;

			public MyCell(LayoutManager aLayout, JCheckBox aCheck, String aCheckPosition, JLabel aLabel, String aLabelPosition)
			{
				super(aLayout);

				add(theCheck= aCheck, aCheckPosition);
				add(theLabel= aLabel, aLabelPosition);
			}
			public Dimension getPreferredSize()
			{
				Dimension d_check= theCheck.getPreferredSize();
				Dimension d_label= theLabel.getPreferredSize();
				return new Dimension(d_check.width + d_label.width, (d_check.height < d_label.height ? d_label.height : d_check.height));
			}

			public void setBackground(Color color)
			{
				if (color instanceof ColorUIResource)
					color= null;

				super.setBackground(color);
			}
			
			public JCheckBox getCheck()
			{
				return theCheck;
			}
			public void setCheck(JCheckBox aCheck)
			{
				theCheck= aCheck;
			}
			public JLabel getLabel()
			{
				return theLabel;
			}
			public void setLabel(JLabel aLabel)
			{
				theLabel= aLabel;
			}
		}
	}

	public static void main(String args[])
	{
		TreeDemoWindow frame= new TreeDemoWindow();
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
}