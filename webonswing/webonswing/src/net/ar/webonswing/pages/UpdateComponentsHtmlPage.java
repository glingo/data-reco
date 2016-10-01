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

package net.ar.webonswing.pages;

import java.util.*;

import javax.servlet.http.*;

import net.ar.webonswing.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public class UpdateComponentsHtmlPage extends SaveContextDefaultHtmlPage
{
	protected boolean isRefreshing;
	protected List theChangedComponents;
	protected String automaticRefreshState= "false";

	public UpdateComponentsHtmlPage()
	{
		super();
	}

	public void prepareFromRequest(HttpServletRequest aRequest)
	{
		super.prepareFromRequest(aRequest);

		String theRefreshParameter= aRequest.getParameter("refreshComponents");
		isRefreshing= theRefreshParameter != null && theRefreshParameter.equals("true");
	}

	protected void processPersistedData()
	{
		theChangedComponents= theContributionContainer.getPersistenceContainer().getChangedComponents(getWindow().getRootComponent());
	}

	public void prepareResponse(HttpServletResponse aResponse) throws Exception
	{
		if (isRefreshing)
		{
			ComponentRenderer theContainerRenderer= getNewComponentRendererInstance();
			getContributions(getWindow().getRootComponent());
			theContainerRenderer.render(getWindow().getRootComponent());
			StringBuffer result= new StringBuffer();

			for (Iterator i= theChangedComponents.iterator(); i.hasNext();)
			{
				String theComponentName= (String)i.next();
				VisualComponent theComponent= getWindow().getRootComponent().findComponent(theComponentName);
				Visitable theRendering= theContainerRenderer.getRenderingContributionContainer().getComponentRendering(theComponent);

				Tag theTag= new Tag("span");
				theTag.addAttribute("id", theComponentName + ".place");
				theTag.addTextToContainer(new ContentRenderer(theRendering).getResult());
				result.append(new ContentRenderer(theTag).getResult());
			}

			String theInitScript= theContainerRenderer.getRenderingContributionContainer().getComponentInitScripts(getWindow().getRootComponent())[0];

			aResponse.getWriter().print("<html><head></head>");
			aResponse.getWriter().print("\n<script id=\"scr\">function updateComponents(){try{parent.hideRefreshFrame(document);} catch(e) {};\ntop.copyComponentsContent(document, \"" + getPersistenceScript() + "; theComponents= new Array(); " + theInitScript + "\");\n}</script>");
			aResponse.getWriter().print("<body onload=\"updateComponents()\" id=\"refreshBody\">");
			aResponse.getWriter().print(result);
			aResponse.getWriter().print("</body></html>");
		}
		else
			super.prepareResponse(aResponse);
	}

	protected ComponentRenderer getNewComponentRendererInstance()
	{
		return new MarkComponentComponentRenderer(getWindow().getClass().getName(), getCacheKey());
	}

	protected HtmlTemplate getMainTemplate()
	{
		return (HtmlTemplate)WosFramework.getInstance().getTemplateManager().getTemplateForName("UpdateComponentsMain");
	}

	public void renderPage(HttpServletResponse aResponse) throws Exception
	{
		theContributionContainer.getScriptManager().addCode("var automaticRefresh= " + automaticRefreshState + ";");

		if (!isRefreshing)
			super.renderPage(aResponse);
	}
}