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

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.managers.styles.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.wrapping.*;

public class DefaultHtmlPage extends DefaultPage implements HtmlPage
{
	public static final String PERSISTED_DATA= "persisted-data";
	private static final String CACHE_KEY_PROPERTY= "theCacheKey";
	protected ContributionContainer theContributionContainer;
	protected Tag theBeforeFormScript;
	protected Tag theAfterFormScript;
	protected Map theRequestParameters;
	protected String theRequestUrl;
	protected boolean windowInSession;

	public DefaultHtmlPage()
	{
		init();
	}

	public void init()
	{
		theContributionContainer= new ContributionContainer(new ScriptContributionContainer(), new StyleContributionContainer(), new DefaultPersistenceContributionContainer());

		theContributionContainer.getScriptManager().addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/WebOnSwing.js");
		theContributionContainer.getStyleManager().addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/styles/WebOnSwing.css");

		theBeforeFormScript= new Tag("script");
		theAfterFormScript= new Tag("script");
	}

	public void prepareFromRequest(HttpServletRequest aRequest)
	{
		theRequestParameters= WosHelper.RequestParametersToMap(aRequest);
		theRequestUrl= aRequest.getContextPath() + aRequest.getServletPath();

		String[] thePersistedData= (String[])theRequestParameters.get(PERSISTED_DATA);
		if (thePersistedData != null && thePersistedData[0] != null)
			theContributionContainer.setPersistenceContainer((PersistenceContributionContainer)Base64.decodeToObject(thePersistedData[0]));
	}

	public void processRequest(HttpServletRequest aRequest)
	{
	}

	public void dispatchEvents()
	{
		new UpdatesFirstEventDispatcher(getWindow().getRootComponent()).generateAndDispatchEvents(theRequestUrl, theRequestParameters);

		processPersistedData();

		theContributionContainer.getPersistenceContainer().clearData();
	}

	protected void processPersistedData()
	{
	}

	public void prepareResponse(HttpServletResponse aResponse) throws Exception
	{
		if (getWindow().getBounds().equals(new Rectangle(0, 0, 0, 0)))
			getWindow().setBounds(new Rectangle(0, 0, 640, 480));

		getContributions(getWindow().getRootComponent());
	}

	protected void getContributions(VisualComponent aComponent)
	{
		new ContributionMaker(theContributionContainer).getContributionsFrom(aComponent);
	}

	public void renderPage(HttpServletResponse aResponse) throws Exception
	{
		VisualComponent aRootComponent= getWindow().getRootComponent();

		HtmlTemplate theMainTemplate= getMainTemplate();
		HtmlTemplate theFormTemplate= HtmlTemplateManager.getClonedSubTemplate(theMainTemplate, "mainForm");

		ComponentRenderer theComponentRenderer= getNewComponentRendererInstance();

		theContributionContainer.getScriptManager().addCode("var thePageName='" + getPageName() + "';");

		theFormTemplate.addIdTagTemplateElement("theBody", theComponentRenderer.render(aRootComponent), null);
		theMainTemplate.addIdTagTemplateElement("mainForm", theFormTemplate, null);

		HtmlTemplate theHeadTemplate= getHeadTemplate();
		theHeadTemplate.addIdTagTemplateElement("theIncludedScripts", theContributionContainer.getScriptManager().getIncludesCode());
		theHeadTemplate.addIdTagTemplateElement("theIncludedStyles", theContributionContainer.getStyleManager().getIncludesCode());
		theHeadTemplate.addIdTagTemplateElement("theScriptCode", theContributionContainer.getScriptManager().getCode().toString());
		theHeadTemplate.addIdTagTemplateElement("theStyleCode", theContributionContainer.getStyleManager().getCode().toString());

		theMainTemplate.addIdTagTemplateElement("theHead", theHeadTemplate, null);

		theBeforeFormScript.addTextToContainer(theContributionContainer.getScriptManager().getCodeBeforeForm().toString());
		theMainTemplate.addIdTagTemplateElement("theBeforeFormScript", theBeforeFormScript, null);

		theAfterFormScript.addTextToContainer(getPersistenceScript());
		theAfterFormScript.addTextToContainer(theComponentRenderer.getRenderingContributionContainer().getComponentInitScripts(aRootComponent)[0]);
		theMainTemplate.addIdTagTemplateElement("theAfterFormScript", theAfterFormScript, null);

		aResponse.getWriter().print(new ContentRenderer(theMainTemplate).getResult());
	}

	protected String getPersistenceScript()
	{
		//TODO: optimizar el zip y serializacion
		int options= Base64.DONT_BREAK_LINES;
		options|= WosFramework.getInstance().isCompressPersistedData() ? Base64.GZIP : 0;

		return "addOrUpdateHidden('" + PERSISTED_DATA + "', '" + Base64.encodeObject(theContributionContainer.getPersistenceContainer(), options) + "');";
	}

	protected ComponentRenderer getNewComponentRendererInstance()
	{
		return new CachingComponentRenderer(getWindow().getTypeId(), getCacheKey());
	}

	protected String getCacheKey()
	{
		return (String)getWindow().getClientProperty(CACHE_KEY_PROPERTY);
	}

	protected void setCacheKey(String aKey)
	{
		getWindow().putClientProperty(CACHE_KEY_PROPERTY, aKey);
	}

	protected HtmlTemplate getMainTemplate()
	{
		return (HtmlTemplate)WosFramework.getInstance().getTemplateManager().getTemplateForName("Main");
	}

	protected HtmlTemplate getHeadTemplate()
	{
		return (HtmlTemplate)WosFramework.getInstance().getTemplateManager().getTemplateForName("Main.theHead");
	}

	protected String getPageName()
	{
		String result= "";

		if (getWindow() != null)
			result= WosFramework.getInstance().getPageManager().getPathForWindowName(getWindow().getTypeId());
		else if (theWindowClassName != null)
			result= WosFramework.getInstance().getPageManager().getPathForWindowName(theWindowClassName);
		else
			result= "unnamed";

		return WosFramework.getInstance().getContextPath() + result;
	}

	public String getUrl()
	{
		return getPageName() + ".page";
	}

	public Map getPersistedData()
	{
		return theContributionContainer.getPersistenceContainer().getPersistedData();
	}

	public void setPersistedData(Map aData)
	{
		theContributionContainer.getPersistenceContainer().setPersistedData(aData);
	}

	public void persistValue(String aKey, Serializable aSerializable)
	{
		theContributionContainer.getPersistenceContainer().persistValue(aKey, aSerializable);
	}

	public Serializable restoreValue(String aKey)
	{
		return theContributionContainer.getPersistenceContainer().restoreValue(aKey);
	}

	public VisualWindow getPersitableWindow()
	{
		try
		{
			return (VisualWindow)((Virtualizable)getWindow()).getVirtualInstance((PersistenceContributionContainer)theContributionContainer.getPersistenceContainer().clone());
		}
		catch (CloneNotSupportedException e)
		{
			throw new WebOnSwingException("Cannot clone the persistence manager", e);
		}
	}

	public boolean isWindowInSession()
	{
		return windowInSession;
	}

	public void setWindowInSession(boolean isWindowInSession)
	{
		windowInSession= isWindowInSession;
	}

	public void setRequest(HttpServletRequest aRequest)
	{
	}
}