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

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.remote.js.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public class InternalFrameUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer aRenderingContribManager)
	{
		JInternalFrame internalFrame= (JInternalFrame)getJComponent();

		VisualComponent contentPane= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(internalFrame.getContentPane());
		Visitable rendering= aRenderingContribManager.getContainerRenderer().render(contentPane);

		if (internalFrame.getContentPane().getWidth() == 0)
			internalFrame.getContentPane().setBounds(0, 0, 640, 480);

		JsConstructorCall contentConstruction= new JsConstructorCall("DynLayer", new JsStatement[] { new StringValue(new ContentRenderer(rendering).getResult()), new ValuesFromGetters(internalFrame.getContentPane(), new String[] { "x", "y", "width", "height" }), new StringValue("grey") }, true);

		JsBuilder jsWC= new JsBuilder();
		jsWC.add(new JsInstance(getName(), new JsConstructorCall("VisualWindow", new JsStatement[] { new ValuesFromGetters(internalFrame, new String[] { "x", "y", "width", "height", "title" }), contentConstruction }, false)));
		jsWC.addDynApiComponentInit(getName());

		Tag scriptTag= new Tag("script");
		scriptTag.addTextToContainer(jsWC.renderJs());

		Tag span= new Tag("span");
		span.addElementToContainer(scriptTag);

		StringBuffer initScript= new StringBuffer(RemoteHelper.getListenersAdds(theComponent));
		initScript.append(aRenderingContribManager.getComponentInitScripts(contentPane)[0]);

		aRenderingContribManager.doContribution(theComponent, span, span, initScript.toString());
	}
	private String getBoundsString(Component aComponent)
	{
		return aComponent.getX() + ", " + aComponent.getY() + ", " + aComponent.getWidth() + ", " + aComponent.getHeight();
	}

	private String getName()
	{
		JInternalFrame internalFrame= (JInternalFrame)getJComponent();
		return WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(internalFrame).getName();
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		RemoteHelper.addDynApiInitCode(aContributionManager);

		aContributionManager.addCodeOnce("dynapi.library.add('dynapi.widgets.VisualWindow','window.js','DynLayer');");
		aContributionManager.addCodeOnce("dynapi.library.include('ViewPane');");
		aContributionManager.addCodeOnce("dynapi.library.include('VisualWindow');");
	}
}