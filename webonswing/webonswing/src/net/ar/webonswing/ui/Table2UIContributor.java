//WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
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

import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.remote.js.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.walkers.*;
import net.ar.webonswing.wrapping.*;

public class Table2UIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer aRenderingContribManager)
	{
		JTable table= (JTable) getJComponent();
		String theComponentName= theComponent.getName();

		Tag script= new Tag("script");

		JsConstructorCall headerArray= new JsConstructorCall("Array", new JsStatement[]{}, true);
		for (int j= 0; j < table.getColumnCount(); j++)
			headerArray.add(new StringValue(table.getColumnName(j)));

		JsConstructorCall dataArray= new JsConstructorCall("Array", new JsStatement[]{}, true);
		for (int i= 0; i < table.getRowCount(); i++)
		{
			JsConstructorCall rowArray= new JsConstructorCall("Array", new JsStatement[]{}, true);

			for (int j= 0; j < table.getColumnCount(); j++)
				rowArray.add(new StringValue(table.getValueAt(i, j).toString()));

			dataArray.add(rowArray);
		}

		JsBuilder jsBuilder= new JsBuilder();

		jsBuilder.add(new GenericJsStatement("var mergeTag= document.getElementById('" + theComponentName + ".merge');"));
		jsBuilder.add(new JsInstance(theComponentName, new JsConstructorCall("Grid", new JsStatement[]{headerArray, new GenericJsStatement(20), dataArray, new GenericJsStatement(table.getSelectedRow())}, false)));
		//		jsBuilder.add(new JsMethodCall(theComponentName, "setWidth", new JsStatement[]{new GenericJsStatement(table.getWidth())}));
		//		jsBuilder.add(new JsMethodCall(theComponentName, "setHeight", new JsStatement[]{new GenericJsStatement(table.getHeight())}));
		jsBuilder.add(new JsMethodCall(theComponentName, "setWidth", new JsStatement[]{new GenericJsStatement("parseInt(mergeTag.style.width)")}));
		jsBuilder.add(new JsMethodCall(theComponentName, "setHeight", new JsStatement[]{new GenericJsStatement("parseInt(mergeTag.style.height)")}));
		//jsBuilder.add(new JsMethodCall(theComponentName, "setLocation", new JsStatement[]{new GenericJsStatement(table.getX()), new GenericJsStatement(table.getY())}));
		jsBuilder.add(new GenericJsStatement(theComponentName + ".updateListener=function () {document.getElementsByName('" + theComponentName + ".selectedRow')[0].value= " + theComponentName + ".selectedrow; ed.dispatch(new MouseClickedEvent('" + theComponentName + "'));};"));
		//jsBuilder.add(new JsMethodCall(theComponentName, "setSelected", new JsStatement[]{new StringValue(table.getSelectedRow()+"")}));

		jsBuilder.addDynApiComponentInit(theComponentName);

		script.addTextToContainer(jsBuilder.renderJs());

		Tag hidden= new Tag("input");
		hidden.addAttribute("type", "hidden");
		hidden.addAttribute("name", theComponentName + ".selectedRow");
		hidden.addAttribute("value", table.getSelectedRow());

		Tag content= new Tag("div");
		content.addAttribute("id", theComponentName + ".merge");
		content.addElementToContainer(script);
		content.addElementToContainer(hidden);

		aRenderingContribManager.doContribution(theComponent, content, content, RemoteHelper.getListenersAdds(theComponent));
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JTable theTable= (JTable) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (theEvent.getName().endsWith(".selectedRow"))
			{
				int index= Integer.parseInt(theEvent.getParameters()[0].toString());
				theTable.getSelectionModel().setSelectionInterval(index, index);
			}
		}
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		RemoteHelper.addDynApiInitCode(aContributionManager);

		aContributionManager.addCodeOnce("dynapi.library.add('dynapi.widgets.Grid','grid.js','DynLayer');");
		aContributionManager.addCodeOnce("dynapi.library.add('dynapi.widgets.EditLabel','editlabel.js','DynLayer');");

		aContributionManager.addCodeOnce("dynapi.library.include('DynKeyEvent');");

		aContributionManager.addCodeOnce("dynapi.library.include('ViewPane');");
		aContributionManager.addCodeOnce("dynapi.library.include('Label');");
		aContributionManager.addCodeOnce("dynapi.library.include('EditLabel');");
		aContributionManager.addCodeOnce("dynapi.library.include('Grid');");
	}

	public VisitableContainer getContainerVisitable()
	{
		return new NullContainerVisitable();
	}

}