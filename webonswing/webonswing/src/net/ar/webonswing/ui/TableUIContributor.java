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
import javax.swing.table.*;

import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.walkers.*;
import net.ar.webonswing.wrapping.*;
import net.ar.webonswing.wrapping.swing.*;

import org.apache.commons.lang.*;

public class TableUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JTable theTable = (JTable) getJComponent();

		RemoteHelper.removeToolTipListeners(theTable);

		StringBuffer theResult = new StringBuffer();
		StringBuffer theResultScript = new StringBuffer();

		theResult.append(renderHeader(theTable));
		theResult.append(renderRows(theContribManager, theTable, theComponent.getName(), theResultScript));

		Tag theTableTag = new Tag("table").addAttribute("border", "1").addAttribute("id", theComponent.getName());
		theTableTag.addTextToContainer(theResult);

		Tag span = new Tag("span");
		String selectedRowName = theComponent.getName() + ".selectedRow";
		span.addElementToContainer(new Tag("input").addAttribute("type", "hidden").addAttribute("id", selectedRowName).addAttribute("name", selectedRowName));
		span.addElementToContainer(theTableTag);

		String theMainScript = RemoteHelper.getListenersAdds(theComponent) + theResultScript.toString();
		theContribManager.doContribution(theComponent, span, theTableTag, theMainScript);
	}

	private StringBuffer renderRows(RenderingContributionContainer theContribManager, JTable theTable, String theComponentName, StringBuffer theResultScript)
	{
		StringBuffer theResult = new StringBuffer();

		for (int i = 0; i < theTable.getRowCount(); i++)
		{
			Tag theRow = new Tag("tr").addAttribute("align", "right");
			theRow.addAttribute("onclick", "document.getElementById('" + theComponentName + ".selectedRow').value=" + i + ";ed.dispatch(new MouseClickedEvent('" + theComponentName + "'));");

			for (int j = 0; j < theTable.getColumnCount(); j++)
			{
				Tag theColumn = new Tag("td");

				JComponent theCellRenderer, theEditorRenderer;

				theCellRenderer = (JComponent) theTable.prepareRenderer(theTable.getCellRenderer(i, j), i, j);
				theEditorRenderer = (JComponent) theTable.getCellEditor(i, j).getTableCellEditorComponent(theTable, theTable.getValueAt(i, j), true, i, j);

				VisualComponent theCellRendererComponent = new ComponentStandardizer().standardizeChildsComponentHierarchy(new SwingComponentWrapper(theCellRenderer));
				theCellRendererComponent.setName(theComponentName + ".CellRenderer." + i + "." + j);

				VisualComponent theEditorRendererComponent = new ComponentStandardizer().standardizeChildsComponentHierarchy(new SwingComponentWrapper(theEditorRenderer));
				theEditorRendererComponent.setName(theComponentName + ".EditorRenderer." + i + "." + j);

				VisualComponent renderer= "edit".equals(theTable.getClientProperty("mode")) ? theEditorRendererComponent :  theCellRendererComponent; 

				Visitable theCellResult = theContribManager.getContainerRenderer().render(renderer);
				theColumn.addTextToContainer(new ContentRenderer(theCellResult).getResult());

				String theComponentScript = theContribManager.getComponentInitScripts(renderer)[0];
				if (theComponentScript.length() > 0)
					theResultScript.append(theComponentScript + ";");

				theRow.addElementToContainer(theColumn);
			}

			theResult.append(new ContentRenderer(theRow).getResult());
		}

		return theResult;
	}

	private StringBuffer renderHeader(JTable theTable)
	{
		StringBuffer theResult = new StringBuffer();

		Tag theRow = new Tag("tr").addAttribute("align", "right");
		for (int j = 0; j < theTable.getColumnCount(); j++)
		{
			Tag theColumn = new Tag("td");
			theColumn.addTextToContainer(theTable.getColumnName(j));
			theRow.addElementToContainer(theColumn);
		}
		theResult.append(new ContentRenderer(theRow).getResult());

		return theResult;
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i = anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent = (RemoteEvent) i.next();
			JTable theTable = (JTable) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (theEvent.getName().endsWith(".selectedRow"))
			{
				if (theEvent.getParameters()[0].toString().trim().length() > 0)
				{
					int index = Integer.parseInt(theEvent.getParameters()[0].toString());
					theTable.getSelectionModel().setSelectionInterval(index, index);
				}
			}
			else if (!theEvent.getName().endsWith("mouseClicked"))
			{
				TableModel theModel = theTable.getModel();

				String[] theElements = StringUtils.split(theEvent.getName(), ".");
				int theRow = Integer.parseInt(theElements[2]);
				int theCol = Integer.parseInt(theElements[3]);

				Object theValue = theEvent.getParameters()[0];

				if (theTable.getColumnClass(theCol).equals(Boolean.class))
					theValue = theValue.equals("on") ? Boolean.TRUE : Boolean.FALSE;
				else if (theTable.getColumnClass(theCol).equals(Integer.class))
					theValue = getIntegerFromString((String) theValue);

				theModel.setValueAt(theValue, theRow, theCol);
			}
		}
	}

	private Integer getIntegerFromString(String theValue)
	{
		try
		{
			return Integer.decode(theValue.toString());
		}
		catch (NumberFormatException e)
		{
			return new Integer(0);
		}
	}

	public VisitableContainer getContainerVisitable()
	{
		return new NullContainerVisitable();
	}
}