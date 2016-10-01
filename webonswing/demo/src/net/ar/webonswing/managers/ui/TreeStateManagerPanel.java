//WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package net.ar.webonswing.managers.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.tree.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.pages.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.swing.layouts.*;
import net.ar.webonswing.wrapping.*;

import org.apache.commons.logging.*;

public abstract class TreeStateManagerPanel extends JPanel
{
	protected JTree theTree;
	protected JList thePagesList;
	protected JFrame theWindow;
	protected CheckNodeComponentStructureGenerator theTreeGenerator;
	protected TemplateLayout theLayout;
	protected TreeSelectionListener theTreeListener;

	public TreeStateManagerPanel(CheckNodeComponentStructureGenerator aTreeGenerator, TemplateLayout aLayout, TreeSelectionListener aTreeListener)
	{
		theTreeGenerator= aTreeGenerator;
		theLayout= aLayout;
		theTreeListener= aTreeListener;

		initComponents();
		
		
		String theFirstElement= (String) ((DefaultListModel) thePagesList.getModel()).getElementAt(1);
		theTree= getComponentsTree(theFirstElement);
	}

	private void initComponents()
	{
		thePagesList= new JList();
		updatePageList();

		thePagesList.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (e.getValueIsAdjusting() == false)
				{
					updateTreeState();

					int theIndex= thePagesList.getSelectedIndex();

					if (theIndex > 0)
						theTree= getComponentsTree((String) ((DefaultListModel) thePagesList.getModel()).getElementAt(theIndex));

					updatePageList();
				}
			}
		});
	}

	private void updatePageList()
	{
		DefaultListModel theListModel= new DefaultListModel();
		Vector thePathList= new Vector();

		for (Iterator i= WosFramework.getInstance().getPageManager().getIterator(); i.hasNext();)
			thePathList.add(((HtmlPageManagerEntry) i.next()).getPath());
		
		Collections.sort(thePathList);
		
		for (int i= thePathList.size()-1; i >=0 ; i--)
			theListModel.addElement(thePathList.get(i));

		if (theListModel.getSize() != thePagesList.getModel().getSize())
			thePagesList.setModel(theListModel);
	}

	protected void updateTreeState()
	{
		DefaultMutableTreeNode theRoot= (DefaultMutableTreeNode) theTree.getModel().getRoot();
		Enumeration theNodes= theRoot.depthFirstEnumeration();

		while (theNodes.hasMoreElements())
		{
			TreeNode theNode= (TreeNode) theNodes.nextElement();
			if (theNode instanceof CheckNode)
				updateNodeState((CheckNode) theNode);
		}

		WosFramework.getInstance().persistWindowTreeStateManager();
	}

	protected abstract void updateNodeState(CheckNode theNode);

	protected JTree getComponentsTree(String aWindowPath)
	{
		JTree tree= new JTree();

		try
		{
			WindowWrapper window= (WindowWrapper) WosFramework.getInstance().getPageManager().getWindowForPath(aWindowPath);
			VisualComponent theRootComponent= new ComponentStandardizer().standardizeWindowHierarchy(window).getRootComponent();

			tree= new JTree(theTreeGenerator.create(theRootComponent));

			tree.setCellRenderer(new ComponentStructureCellRenderer(tree));
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeSelectionListener(theTreeListener);

			JPanel theBodyPanel= this;

			theBodyPanel.setLayout(theLayout);
			if (theBodyPanel.getComponentCount() > 2)
			{
				theBodyPanel.remove(0);
				theBodyPanel.remove(0);
				theBodyPanel.remove(0);
			}

			theBodyPanel.add(thePagesList, "theCombo", 0);
			theBodyPanel.add(tree, "theTree", 0);

			JButton theSaveButton= new JButton("save");
			theSaveButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					updateTreeState();
				}
			});

			theBodyPanel.add(theSaveButton, "theSaveButton", 0);
		}
		catch (Exception e)
		{
			LogFactory.getLog(TreeStateManagerPanel.class).error("", e);
		}

		return tree;
	}

	public class ComponentStructureCellRenderer implements TreeCellRenderer
	{
		MyCell theLeafCell;
		MyCell theExpandedCell;
		MyCell theCollapsedCell;
		MyCell theRootCell;
		Template theTemplate;

		public ComponentStructureCellRenderer(JTree aTree)
		{
			theTemplate= WosSwingHelper.getTemplateForComponent("JTree2", aTree);

			theLeafCell= createCell("theLeafNode", new ImageIcon("resources/images/j.close.gif"));
			theExpandedCell= createCell("theExpandedNode", new ImageIcon("resources/images/j.open.gif"));
			theCollapsedCell= createCell("theCollapsedNode", new ImageIcon("resources/images/j.close.gif"));
			theRootCell= createCell("theRootNode", new ImageIcon("resources/images/j.close.gif"));
		}

		private MyCell createCell(String theNodeType, ImageIcon aIcon)
		{
			JCheckBox theCheck= new JCheckBox();
			JLabel theLabel= new JLabel();

			theCheck.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForSubTemplate(theTemplate, theNodeType + ".theTreeCell.theContent.theCheck")));
			theLabel.setLayout(new TemplateLayout(WosFramework.getKeyPositionTemplateForSubTemplate(theTemplate, theNodeType + ".theTreeCell.theContent.theLabel")));

			MyCell theCell= new MyCell(new TemplateLayout(WosFramework.getKeyPositionTemplateForSubTemplate(theTemplate, theNodeType + ".theTreeCell.theContent")), theCheck, "theCheck", theLabel, "theLabel");
			theCell.theLabel.setIcon(aIcon);

			return theCell;
		}

		public Component getTreeCellRendererComponent(JTree aTree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus)
		{
			MyCell theCell= null;

			if (value instanceof CheckNode)
			{
				TreeNode theRootNode= (TreeNode) aTree.getModel().getRoot();
				CheckNode theCheckNode= (CheckNode) value;
				theCell= theRootNode == theCheckNode ? theRootCell : leaf ? theLeafCell : expanded ? theExpandedCell : theCollapsedCell;

				theCell.setEnabled(aTree.isEnabled());
				theCell.theCheck.setSelected(theCheckNode.isSelected());
				theCell.theLabel.setText(aTree.convertValueToText(value, isSelected, expanded, leaf, row, hasFocus));

				Color theBgColor= isSelected ? new ColorUIResource(255, 255, 200) : UIManager.getColor("Label.background");

				theCell.theLabel.setBackground(theBgColor);

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
		}
	}

	public static class CheckTreeSelectionListener implements TreeSelectionListener, RemoteListener
	{
		public void valueChanged(TreeSelectionEvent e)
		{
			TreePath path= e.getNewLeadSelectionPath();

			if (e.getNewLeadSelectionPath().getLastPathComponent() instanceof CheckNode)
			{
				CheckNode node= (CheckNode) e.getNewLeadSelectionPath().getLastPathComponent();
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

}
