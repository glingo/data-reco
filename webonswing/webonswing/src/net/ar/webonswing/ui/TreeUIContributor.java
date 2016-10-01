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

package net.ar.webonswing.ui;

import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.swing.layouts.*;
import net.ar.webonswing.walkers.*;
import net.ar.webonswing.wrapping.*;
import net.ar.webonswing.wrapping.swing.*;

import org.apache.commons.lang.*;

public class TreeUIContributor extends AbstractSwingComponentUIContributor
{
	protected String theTreeExpandValue;
	protected StringBuffer theTreeCheckValue;
	protected HtmlTemplate theTemplate;
	protected TreeCellRenderer theTreeCellRenderer;
	protected JTree theTree= null;
	protected int theNodeCount;
	protected RenderingContributionContainer theContribManager= null;
	protected String theTreeName;
	protected Map theCellCache;

	private StringBuffer theScript;

	public void doRenderingContribution(RenderingContributionContainer aContribManager)
	{
		init();

		theContribManager= aContribManager;

		JTree aTree= (JTree) getJComponent();

		theTreeName= theComponent.getName();

		theTemplate= (HtmlTemplate) WosSwingHelper.getTemplateForComponent("JTree", aTree);

		TreeNode theRootNode= (TreeNode) theTree.getModel().getRoot();
		theTreeCellRenderer= theTree.getCellRenderer();

		String theResult= renderTreeNode(theRootNode, theScript);
		String theFinalScript= theScript.toString() + ";" + RemoteHelper.getListenersAdds(theComponent);
		theContribManager.doContribution(theComponent, new TextContent(theResult), null, theFinalScript);
	}

	protected void init()
	{
		theScript= new StringBuffer();
		theNodeCount= 0;
		theTreeCheckValue= new StringBuffer();
		theTreeExpandValue= "";
		theCellCache= Collections.synchronizedMap(new HashMap());
	}

	public void setComponent(VisualComponent aComponent)
	{
		super.setComponent(aComponent);
		theTree= (JTree) getJComponent();

		if (!(theTree.getLayout() instanceof TemplateLayout))
		{
			theTree.setLayout(WosFramework.getTemplateLayoutForName("TreeDemo.JTree"));
			//theTree.setCellRenderer(new TreeDemoWindow.CheckRenderer());
		}
	}

	private String renderTreeNode(TreeNode aTreeNode, StringBuffer theNodeScript)
	{
		if (aTreeNode instanceof CheckNode)
			theTreeCheckValue.append(((CheckNode) aTreeNode).isSelected() ? "1" : "0");

		String theNodeNumber= new Integer(theNodeCount++).toString();
		String theRowName= theTreeName + ".row." + theNodeNumber;
		boolean isRootNode= theTree.getModel().getRoot() == aTreeNode;
		String theNodeName= isRootNode ? theTreeName : theRowName;

		theNodeScript.append("new JTree" + (aTreeNode.isLeaf() ? "Leaf" : "") + "('" + theNodeName + "')");

		String theNodeType= isRootNode ? "theRootNode" : aTreeNode.isLeaf() ? "theLeafNode" : "theCollapsedNode";
		HtmlTemplate theNodeTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, theNodeType);
		HtmlTemplate theNextItemTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theRootNode.theNextItem");

		StringBuffer theResult= new StringBuffer();
		for (int i= 0; i < aTreeNode.getChildCount(); i++)
		{
			TreeNode theChild= aTreeNode.getChildAt(i);
			StringBuffer theChildScript= new StringBuffer();

			theResult.append(renderTreeNode(theChild, theChildScript));
			theNodeScript.append(".add (" + theChildScript.toString() + ")");
		}

		String theCellResult= getCells(aTreeNode, theRowName, isRootNode);

		theNodeTemplate.addIdTagTemplateElement("theTreeCell", new TextContent(theCellResult));

		if (!aTreeNode.isLeaf())
		{
			HtmlTemplate theExpandablePartTemplate= HtmlTemplateManager.getSubTemplate(theNodeTemplate, "theNextItem.theExpandablePart");
			theExpandablePartTemplate.addIdTagTemplateElement("theContent", new TextContent(theResult));
			theExpandablePartTemplate.mergeFoundTagKeepingName(new Tag("span").addAttribute("id", theRowName + ".expandable"));
		}
		if (isRootNode)
		{
			theNodeTemplate.getSubTemplate("theLastRowClicked").mergeFoundTag(new Tag("input").addAttribute("name", theTreeName + ".lastRowClicked").addAttribute("value", theTree.getSelectionCount() > 0 ? theTree.getSelectionRows()[0] : 0));
			theNodeTemplate.getSubTemplate("theTreeState").mergeFoundTag(new Tag("input").addAttribute("name", theTreeName + ".checkValue").addAttribute("value", theTreeCheckValue.toString()));
			theTreeExpandValue= (String) theTree.getClientProperty("theTreeExpandValue");
			if (theTreeExpandValue == null)
				theTreeExpandValue= StringUtils.repeat("0", theNodeCount);
			theNodeTemplate.getSubTemplate("theTreeExpandState").mergeFoundTag(new Tag("input").addAttribute("name", theTreeName + ".expandValue").addAttribute("value", theTreeExpandValue));
		}

		theNodeTemplate.mergeFoundTagKeepingName(new Tag("span").addAttribute("id", theNodeName));
		theNextItemTemplate.addIdTagTemplateElement("theExpandablePart", theNodeTemplate);

		return theNextItemTemplate.renderToText();
	}

	private String getCells(TreeNode aTreeNode, String theRowName, boolean isRootNode)
	{
		boolean isSelected= theTree.getSelectionPath() != null && theTree.getSelectionPath().getLastPathComponent() == aTreeNode;
		String theCellText= aTreeNode.toString();
		String theHashValue= new Boolean(isRootNode).toString() + new Boolean(isSelected).toString() + new Boolean(aTreeNode.isLeaf()).toString();

		String theCachedCell= (String) theCellCache.get(theHashValue);
		if (theCachedCell == null)
		{
			((MutableTreeNode) aTreeNode).setUserObject("theCellText");
			theCellCache.put(theHashValue, theCachedCell= createCells(aTreeNode, "theRowName", isRootNode));
			((MutableTreeNode) aTreeNode).setUserObject(theCellText);
		}

		theCachedCell= StringUtils.replace(theCachedCell, "theCellText", theCellText);
		theCachedCell= StringUtils.replace(theCachedCell, "theRowName", theRowName);

		return theCachedCell.toString();
	}

	private String createCells(TreeNode aTreeNode, String theRowName, boolean isRootNode)
	{
		boolean isSelected= theTree.getSelectionPath() != null && theTree.getSelectionPath().getLastPathComponent() == aTreeNode;

		StringBuffer theCellResult= new StringBuffer();

		VisualComponent theTreeRendererComponent= new ComponentStandardizer().standardizeChildsComponentHierarchy(new SwingComponentWrapper((JComponent) theTreeCellRenderer.getTreeCellRendererComponent(theTree, aTreeNode, isSelected, false, aTreeNode.isLeaf(), 1, false)));

		HtmlTemplate theCollapsedCellTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theCollapsedNode.theTreeCell");
		theCollapsedCellTemplate.addIdTagTemplateElement("theContent", theContribManager.getContainerRenderer().render(theTreeRendererComponent));
		theCollapsedCellTemplate.mergeFoundTagKeepingName(new Tag("span").addAttribute("id", theRowName + (aTreeNode.isLeaf() ? "" : ".collapsed")));
		theCellResult.append(theCollapsedCellTemplate.renderToText());

		if (!aTreeNode.isLeaf() && !isRootNode)
		{
			VisualComponent theTreeRendererComponentExpanded= new ComponentStandardizer().standardizeChildsComponentHierarchy(new SwingComponentWrapper((JComponent) theTreeCellRenderer.getTreeCellRendererComponent(theTree, aTreeNode, isSelected, true, aTreeNode.isLeaf(), 1, false)));

			HtmlTemplate theExpandedCellTemplate= HtmlTemplateManager.getClonedSubTemplate(theTemplate, "theExpandedNode.theTreeCell");
			theExpandedCellTemplate.addIdTagTemplateElement("theContent", theContribManager.getContainerRenderer().render(theTreeRendererComponentExpanded));
			theExpandedCellTemplate.mergeFoundTagKeepingName(new Tag("span").addAttribute("id", theRowName + ".expanded"));
			theCellResult.append(theExpandedCellTemplate.renderToText());
		}

		return theCellResult.toString();
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext(); )
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JTree theSourceTree= (JTree) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (theEvent.getName().endsWith("lastRowClicked"))
			{
				expandAll(theSourceTree, true);
				theSourceTree.setSelectionRow(new Integer((String) theEvent.getParameters()[0]).intValue());
			}
			else if (theEvent.getName().endsWith("checkValue"))
			{
				theTreeCheckValue= new StringBuffer((String) theEvent.getParameters()[0]);
				theNodeCount= 0;

				setCheckState((TreeNode) theSourceTree.getModel().getRoot());
			}
			else if (theEvent.getName().endsWith("expandValue"))
			{
				theTreeExpandValue= (String) theEvent.getParameters()[0];
				theSourceTree.putClientProperty("theTreeExpandValue", theTreeExpandValue);
			}
		}
	}

	public void expandAll(JTree aTree, boolean expand)
	{
		TreeNode root= (TreeNode) aTree.getModel().getRoot();
		expandAll(aTree, new TreePath(root), expand);
	}
	private void expandAll(JTree aTree, TreePath parent, boolean expand)
	{
		TreeNode node= (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0)
		{
			for (Enumeration e= node.children(); e.hasMoreElements(); )
			{
				TreeNode n= (TreeNode) e.nextElement();
				TreePath path= parent.pathByAddingChild(n);
				expandAll(aTree, path, expand);
			}
		}

		if (expand)
			aTree.expandPath(parent);
		else
			aTree.collapsePath(parent);
	}

	public void setCheckState(TreeNode aTreeNode)
	{
		if (theNodeCount < theTreeCheckValue.length())
		{
			CheckNode theTreeNode= (CheckNode) aTreeNode;
			theTreeNode.setSelected(theTreeCheckValue.charAt(theNodeCount) == '1');
			theNodeCount++;

			for (int i= 0; i < aTreeNode.getChildCount(); i++)
			{
				TreeNode theChild= aTreeNode.getChildAt(i);
				setCheckState(theChild);
			}
		}
	}

	public VisitableContainer getContainerVisitable()
	{
		return new NullContainerVisitable();
	}
	
	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/JTree.js");
	}
}