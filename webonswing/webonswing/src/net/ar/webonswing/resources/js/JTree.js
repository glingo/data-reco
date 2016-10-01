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

var checkboxPressed= null;
var treeNodePressed= null;

function JCheckNode(aName)
{
	this.inheritFrom= JComponent;
	this.inheritFrom(aName);
	this.id= "JCheckNode";
	this.theName= aName;
	this.theRowNumber= parseInt(aName.substr(aName.lastIndexOf(".") + 1));
	this.theTreeName= aName.substr(0, aName.indexOf(".", aName.indexOf("_") + 1));
	this.theCheckValue= document.getElementsByName(this.theTreeName + ".checkValue")[0];
	this.theLastRowClicked= document.getElementsByName(this.theTreeName + ".lastRowClicked")[0];

	this.setSelected= function(isChecked)
	{
		this.theCheckValue.value= this.theCheckValue.value.substr(0, this.theRowNumber) + (isChecked ? "1" : "0") + this.theCheckValue.value.substr(this.theRowNumber + 1);
	}

}
function JTreeLeaf(aName)
{
	this.inheritFrom= JCheckNode;
	this.inheritFrom(aName);
	this.id= "JTreeLeaf";

	this.theCellCheck= this.theElement.getElementsByTagName("INPUT")[0];
	this.theRowElement= document.getElementById(this.theName);

	if (this.theCheckValue != null)
	{
		this.theCellCheck.checked= this.theCheckValue.value.charAt(this.theRowNumber) == "1" ? true : false;
		var theTreeNode= this;

		this.theRowElement.onclick= function()
		{
			theTreeNode.theLastRowClicked.value= theTreeNode.theRowNumber;
			theTreeNode.setSelected(theTreeNode.theCellCheck.checked);
			ed.dispatch(new TreeSelectionEvent(theTreeNode.theTreeName, theTreeNode));
		};
	}
}

function JTree(aName)
{
	this.inheritFrom= JCheckNode;
	this.inheritFrom(aName);
	this.id= "JTree";
	this.theCollapsedCell= document.getElementById(aName + ".collapsed");
	this.theExpandedCell= document.getElementById(aName + ".expanded");
	this.theExpandablePart= document.getElementById(aName + ".expandable");
	this.theExpandValue= document.getElementsByName(aName.substr(0, aName.indexOf(".", aName.indexOf("_") + 1)) + ".expandValue")[0];

	this.setExpanded= function(expand)
	{
		if (expand)
		{
			if (checkboxPressed == null)
				this.expand();
			else
			{
				this.setSelected(this.theCollapsedCellCheck.checked);
				this.theExpandedCellCheck.checked= this.theCollapsedCellCheck.checked;
			}
		}
		else
		{
			if (checkboxPressed == null)
				this.collapse();
			else
			{
				this.setSelected(this.theExpandedCellCheck.checked);
				this.theCollapsedCellCheck.checked= this.theExpandedCellCheck.checked;
			}
		}

		if (checkboxPressed == null)
			this.theExpandValue.value= this.theExpandValue.value.substr(0, this.theRowNumber) + (expand ? "1" : "0") + this.theExpandValue.value.substr(this.theRowNumber + 1);

		checkboxPressed= null;
	}

	this.expand= function()
	{
		this.theCollapsedCell.style.display= "none";
		this.theExpandedCell.style.display= "block";
		this.theExpandablePart.style.display= "block";
	}

	this.collapse= function()
	{
		this.theCollapsedCell.style.display= "block";
		this.theExpandedCell.style.display= "none";
		this.theExpandablePart.style.display= "none";
	}

	this.isCollapsed= function()
	{
		return this.theCollapsedCell.style.display == "block";
	}

	this.add= function(aComponent)
	{
		this.getComponents().add(aComponent);
		var theTreeNode= this;
		if (this.theCollapsedCell != null)
			this.theCollapsedCell.onclick= this.theExpandedCell.onclick= function()
		{
			theTreeNode.theLastRowClicked.value= theTreeNode.theRowNumber;
			ed.dispatch(new TreeSelectionEvent(theTreeNode.theTreeName, theTreeNode));
		};

		return this;
	};

	if (this.theCollapsedCell != null)
	{
		this.theCollapsedCellCheck= this.theCollapsedCell.getElementsByTagName("INPUT")[0];
		this.theExpandedCellCheck= this.theExpandedCell.getElementsByTagName("INPUT")[0];
		this.theExpandedCellCheck.checked= this.theCollapsedCellCheck.checked= this.theCheckValue.value.charAt(this.theRowNumber) == "1" ? true : false;
		this.setExpanded(this.theExpandValue.value.charAt(this.theRowNumber) == "1");
	}
}

function CheckTreeSelectionListener()
{
	this.inheritFrom= TreeSelectionListener;
	this.inheritFrom();

	this.valueChanged= function(aTreeSelectionEvent)
	{
		if (aTreeSelectionEvent.theTreeNode.id == "JTree")
			aTreeSelectionEvent.theTreeNode.setExpanded(aTreeSelectionEvent.theTreeNode.isCollapsed());
	};
}
