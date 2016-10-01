package net.ar.webonswing.tutorial.tree;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.tree.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.swing.layouts.*;

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