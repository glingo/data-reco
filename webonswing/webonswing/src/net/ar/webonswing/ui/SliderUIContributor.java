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

public class SliderUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer aRenderingContribManager)
	{
		JSlider slider= (JSlider)getJComponent();

		Tag script= new Tag("script");

		JsBuilder jsBuilder= new JsBuilder();
		jsBuilder.add(new JsInstance(getName(), new JsConstructorCall("Slider", new JsStatement[] { new ValuesFromGetters(slider, new String[] { "width", "height", "minimum", "maximum", "value", "orientation" }) }, false)));
		jsBuilder.add(new GenericJsStatement(getName() + ".updateListener=function () {document.getElementsByName('" + getName() + ".hidden')[0].value= " + getName() + ".value;};"));
		jsBuilder.addDynApiComponentInit(getName());

		script.addTextToContainer(jsBuilder.renderJs());

		Tag hidden= new Tag("input");
		hidden.addAttribute("type", "hidden");
		hidden.addAttribute("name", getName() + ".hidden");
		hidden.addAttribute("value", slider.getValue());

		Tag content= new Tag("span");
		content.addElementToContainer(script);
		content.addElementToContainer(hidden);

		aRenderingContribManager.doContribution(theComponent, content, content, RemoteHelper.getListenersAdds(theComponent));
	}
	private String getName()
	{
		JSlider slider= (JSlider)getJComponent();
		return WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(slider).getName();
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent event= (RemoteEvent)i.next();

			if (event.getName().endsWith("hidden"))
			{
				JSlider slider= (JSlider)((ComponentWrapper)event.getSource()).getWrappedComponent();
				slider.setValue(Integer.parseInt((String)event.getParameters()[0]));
			}
		}
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		RemoteHelper.addDynApiInitCode(aContributionManager);

		aContributionManager.addCodeOnce("dynapi.library.add('dynapi.widgets.Slider','slider.js','DynLayer');");
		aContributionManager.addCodeOnce("dynapi.library.include('Button');");
		aContributionManager.addCodeOnce("dynapi.library.include('Slider');");
	}
}