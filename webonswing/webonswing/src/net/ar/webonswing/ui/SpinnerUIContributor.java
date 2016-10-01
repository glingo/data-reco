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

import net.ar.webonswing.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.remote.js.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.wrapping.*;

public class SpinnerUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer aRenderingContribManager)
	{
		JSpinner spinner= (JSpinner)getJComponent();
 
		Tag script= new Tag("script");

		JsBuilder jsBuilder= new JsBuilder();
		jsBuilder.add(new GenericJsStatement("var mergeTag= document.getElementById('" + getName() + ".merge');"));
		jsBuilder.add(new JsInstance(getName(), new JsConstructorCall("Spinner", new JsStatement[] { new ValuesFromGetters(spinner, new String[] { "width", "height", "value" }), new ValuesFromGetters(spinner.getModel(), new String[] { "minimum", "maximum" }), new StringValue(getName()) }, false)));
		//jsBuilder.add(new JsInstance(getName(), new JsConstructorCall("Spinner", new JsStatement[] { new GenericJsStatement("mergeTag.offsetWidth"),  new GenericJsStatement("mergeTag.offsetHeight") , new ValuesFromGetters(spinner, new String[] { "value" }), new ValuesFromGetters(spinner.getModel(), new String[] { "minimum", "maximum" }), new StringValue(getName()) }, false)));
		jsBuilder.addDynApiComponentInit(getName());
		script.addTextToContainer(jsBuilder.renderJs());

		Tag content= new Tag("div");
		content.addAttribute("id", getName() + ".merge");
		content.addElementToContainer(script);

		StringBuffer initScripts= new StringBuffer(RemoteHelper.getListenersAdds(theComponent));

		aRenderingContribManager.doContribution(theComponent, content, content, initScripts.toString());
	}
	private String getName()
	{
		JSpinner slider= (JSpinner)getJComponent();
		return WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(slider).getName();
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent event= (RemoteEvent)i.next();

			if (event.getName().endsWith("input"))
			{
				JSpinner spinner= (JSpinner)((ComponentWrapper)event.getSource()).getWrappedComponent();
				spinner.setValue(new Integer((String)event.getParameters()[0]));
			}
		}
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		RemoteHelper.addDynApiInitCode(aContributionManager);

		aContributionManager.addCodeOnce("dynapi.library.include('HTMLTextBox');");
		aContributionManager.addCodeOnce("dynapi.library.include('Button');");
		aContributionManager.addCodeOnce("dynapi.library.include('ButtonFlatStyle');");
		aContributionManager.addCodeOnce("dynapi.library.include('ButtonImageStyle');");

		aContributionManager.addCodeOnce("dynapi.library.add('dynapi.widgets.Spinner','spinner.js','DynLayer');");
		aContributionManager.addCodeOnce("dynapi.library.include('Spinner');");
	}
}