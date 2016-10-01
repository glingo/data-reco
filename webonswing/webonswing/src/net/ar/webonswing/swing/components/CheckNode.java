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

package net.ar.webonswing.swing.components;

import java.util.*;

import javax.swing.tree.*;

public class CheckNode extends DefaultMutableTreeNode
{
	public final static int SINGLE_SELECTION= 0;
	public final static int DIG_IN_SELECTION= 4;
	protected int selectionMode;
	protected boolean isSelected;

	public CheckNode()
	{
		this(null);
	}

	public CheckNode(Object anUserObject)
	{
		this(anUserObject, true, false);
	}

	public CheckNode(Object anUserObject, boolean anAllowsChildren, boolean selected)
	{
		super(anUserObject, anAllowsChildren);
		this.isSelected= selected;
		setSelectionMode(SINGLE_SELECTION);
	}

	public void setSelectionMode(int mode)
	{
		selectionMode= mode;
	}

	public int getSelectionMode()
	{
		return selectionMode;
	}

	public void setSelected(boolean selected)
	{
		this.isSelected= selected;

		if ((selectionMode == DIG_IN_SELECTION) && (children != null))
		{
			Enumeration enum= children.elements();
			while (enum.hasMoreElements())
			{
				Object theNode= enum.nextElement();

				if (theNode instanceof CheckNode)
				{
					CheckNode theCheckNode= (CheckNode) theNode;
					theCheckNode.setSelected(selected);
				}
			}
		}
	}

	public boolean isSelected()
	{
		return isSelected;
	}
}