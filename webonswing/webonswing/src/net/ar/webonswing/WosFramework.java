// WebOnSwing - Web Application Framework
//	Copyright (C) 2003 Fernando Damian Petrola
//	
//	This library is free software; you can redistribute it and/or
//	modify it under the terms of the GNU Lesser General Public
//	License as published by the Free Software Foundation; either
//	version 2.1 of the License, or (at your option) any later version.
//	
//	This library is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//	Lesser General Public License for more details.
//	
//	You should have received a copy of the GNU Lesser General Public
//	License along with this library; if not, write to the Free Software
//	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package net.ar.webonswing;

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.managers.names.*;
import net.ar.webonswing.managers.pages.*;
import net.ar.webonswing.managers.state.*;
import net.ar.webonswing.managers.templates.*;
import net.ar.webonswing.managers.windows.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.swing.layouts.*;
import net.ar.webonswing.wrapping.*;

import org.apache.commons.logging.*;

/**
 * Contiene todos los administradores necesarios para interactuar con el
 * framework desde Swing y los contribuyentes
 * 
 * @author Fernando Damian Petrola
 */
public class WosFramework
{
	public String pageManagerConfigXmlFile;
	public String templateManagerConfigXmlFile;
	public String contributorManagerConfigXmlFile;

	public String windowTreeStateManagerConfigSerializedFile;

	public String windowManagerClassName;
	public String hierarchyWrapperClassName;
	public String componentNameManagerClassName;

	public String resourcesPath;
	public boolean compressPersistedData= false;
	public boolean catchExceptions= true;

	transient protected static WosFramework theInstance;

	transient public String contextPath= "";
	public static final String TRANSFORMATION_PACKAGE= "transformation-package";
	transient public static final String contributor_property= "theContributor";
	transient public static String wosframework_config_file= "/net/ar/webonswing/config/webonswing-framework.config.xml";
	transient protected PageManager thePageManager;
	transient protected TemplateManager theTemplateManager;
	transient protected ContributorManager theContributorManager;
	transient protected WindowTreeStateManager theWindowTreeStateManager;
	transient protected static boolean active= false;

	transient protected static InheritableThreadLocal sessionContext= new InheritableThreadLocal();
	transient protected static Hashtable applicationContext= new Hashtable();

	public static final String USE_WOS_CLASSLOADER= "use-wos-classloader";

	public static WosFramework getInstance()
	{
		if (theInstance == null)
			theInstance= (WosFramework)WosHelper.restoreObjectFromXml(wosframework_config_file);

		return theInstance;
	}

	public void init()
	{
		try
		{
			initPageManager();
			initTemplateManager();
			initContributorManager();
			initWindowTreeStateManager();

			System.setProperty("awt.toolkit", "net.ar.webonswing.toolkit.WosToolkit");
			System.setProperty("java.awt.graphicsenv", "net.ar.webonswing.toolkit.WosGraphicsEnvironment");
			UIManager.installLookAndFeel("WebOnSwing", "net.ar.webonswing.toolkit.lookandfeel.DummyLookAndFeel");
			UIManager.setLookAndFeel("net.ar.webonswing.toolkit.lookandfeel.DummyLookAndFeel");
		}
		catch (Exception e)
		{
			LogFactory.getLog(WosFramework.class).error("WosFramework initialization error", e);
			throw new WebOnSwingException("WosFramework initialization error", e);
		}

		active= true;
	}

	public void initTemplateManager()
	{
		theTemplateManager= (TemplateManager)WosHelper.restoreObjectFromXml(templateManagerConfigXmlFile);
	}

	public void initPageManager()
	{
		thePageManager= (PageManager)WosHelper.restoreObjectFromXml(pageManagerConfigXmlFile);
	}

	public void initContributorManager()
	{
		theContributorManager= (ContributorManager)WosHelper.restoreObjectFromXml(contributorManagerConfigXmlFile);
	}

	public void initWindowTreeStateManager()
	{
		theWindowTreeStateManager= (WindowTreeStateManager)WosHelper.getObjectFromFile(WosHelper.getResourceAsStream(windowTreeStateManagerConfigSerializedFile));
	}

	public void persistWosFramework()
	{
		WosHelper.persistObjectToXml(getInstance(), wosframework_config_file);
	}
	public void persistTemplateManager()
	{
		WosHelper.persistObjectToXml(getTemplateManager(), templateManagerConfigXmlFile);
	}
	public void persistPageManager()
	{
		WosHelper.persistObjectToXml(getPageManager(), pageManagerConfigXmlFile);
	}
	public void persistContributorManager()
	{
		WosHelper.persistObjectToXml(getContributorManager(), contributorManagerConfigXmlFile);
	}

	public void persistWindowTreeStateManager()
	{
		WosHelper.serializeObjectToFile(getWindowTreeStateManager(), windowTreeStateManagerConfigSerializedFile);
		//WosHelper.persistObjectToXml(theWindowTreeStateManager, StringUtils.replace(window_tree_state_manager_config_serialized_file, "serialized", "xml"));
	}

	/**
	 * Encuentra una plantilla con el nombre o ruta especificada
	 * 
	 * @param aTemplateName
	 *           nombre o ruta de la plantilla a encontrar
	 * @return
	 */
	public static KeyPositionTemplate getKeyPositionTemplateForName(String aTemplateName)
	{
		return getInstance().getTemplateManager().getKeyPositionTemplateForName(aTemplateName);
	}

	public static KeyPositionTemplate getKeyPositionTemplateForSubTemplate(Template aTemplate, String aPath)
	{
		return getInstance().getTemplateManager().getKeyPositionTemplateForSubTemplate(aTemplate, aPath);
	}

	public static PropagateTemplateLayoutByName getPropagateTemplateLayoutByNameFor(String aTemplateName)
	{
		return new PropagateTemplateLayoutByName(getKeyPositionTemplateForName(aTemplateName));
	}

	/**
	 * Devuelve un TemplateLayout a partir del nombre o ruta de una plantilla
	 * 
	 * @param aName
	 *           nombre o ruta de plantilla
	 * @return
	 */
	public static TemplateLayout getTemplateLayoutForName(String aName)
	{
		return new TemplateLayout(getKeyPositionTemplateForName(aName));
	}
	public static TemplateLayout getTemplateLayoutForSubTemplate(Template aTemplate, String aPath)
	{
		return new TemplateLayout(getKeyPositionTemplateForSubTemplate(aTemplate, aPath));
	}

	public static void showChildWindow(Object anOpenerWindow, Object aWindowToShow)
	{
		getInstance().getWindowManager().showChildWindow(anOpenerWindow, aWindowToShow);
	}

	public static void showAndExecute(Object anOpenerWindow, Object aWindowToShow, String aMethodName)
	{
		getInstance().getWindowManager().showAndExecute(anOpenerWindow, aWindowToShow, aMethodName);
	}

	public static void hide(Object aWindow)
	{
		getInstance().getWindowManager().hide(aWindow);
	}

	public static void assignContributor(JComponent aComponent, Class aContributor)
	{
		aComponent.putClientProperty(contributor_property, aContributor.getName());
	}

	public static void assignContributor(JComponent aComponent, String aContributorClassName)
	{
		aComponent.putClientProperty(contributor_property, aContributorClassName);
	}

	public static void assignContributor(JComponent aComponent, ComponentContributor aContributor)
	{
		aComponent.putClientProperty(contributor_property, aContributor);
	}

	public static void assignContributor(RootPaneContainer aRootPaneContainer, Class aContributor)
	{
		assignContributor(aRootPaneContainer.getRootPane(), aContributor);
	}

	public static void assignContributor(RootPaneContainer aRootPaneContainer, String aContributorClassName)
	{
		assignContributor(aRootPaneContainer.getRootPane(), aContributorClassName);
	}

	public static void assignContributor(RootPaneContainer aRootPaneContainer, ComponentContributor aContributor)
	{
		assignContributor(aRootPaneContainer.getRootPane(), aContributor);
	}

	public void setPageManager(PageManager anPageManager)
	{
		thePageManager= anPageManager;
	}
	public void setTemplateManager(TemplateManager anTemplateManager)
	{
		theTemplateManager= anTemplateManager;
	}
	public void setWindowTreeStateManager(WindowTreeStateManager anWindowTreeStateManager)
	{
		theWindowTreeStateManager= anWindowTreeStateManager;
	}
	public WindowTreeStateManager getWindowTreeStateManager()
	{
		return theWindowTreeStateManager;
	}
	public PageManager getPageManager()
	{
		return thePageManager;
	}
	public TemplateManager getTemplateManager()
	{
		return theTemplateManager;
	}

	public static ComponentNameManager getComponentNameManager()
	{
		if (getInstance().componentNameManagerClassName.length() > 0)
			return (ComponentNameManager)WosHelper.createClassInstance(getInstance().componentNameManagerClassName);
		else
			return null;
	}

	public String getCompleteResourcePath()
	{
		return getContextPath() + getResourcesPath();
	}

	public static Map getSessionContext()
	{
		Map sessionContextInstance= (Map)sessionContext.get();

		if (sessionContextInstance == null)
		{
			sessionContextInstance= new Hashtable();
			sessionContext.set(sessionContextInstance);
		}

		return sessionContextInstance;
	}

	public static Map getApplicationContext()
	{
		return applicationContext;
	}

	public ContributorManager getContributorManager()
	{
		return theContributorManager;
	}
	public void setContributorManager(ContributorManager unContributorManager)
	{
		theContributorManager= unContributorManager;
	}

	public static boolean isActive()
	{
		return active;
	}

	public static void setActive(boolean anActive)
	{
		active= anActive;
	}

	public String getResourcesPath()
	{
		return resourcesPath;
	}

	public void setResourcesPath(String aString)
	{
		getInstance().resourcesPath= aString;
	}

	public HierarchyWrapper getHierarchyWrapper()
	{
		if (getInstance().hierarchyWrapperClassName.length() > 0)
			return (HierarchyWrapper)WosHelper.createClassInstance(getInstance().hierarchyWrapperClassName);
		else
			return null;
	}

	public WindowManager getWindowManager()
	{
		return (WindowManager)WosHelper.createClassInstance(getInstance().windowManagerClassName);
	}

	public boolean isCompressPersistedData()
	{
		return compressPersistedData;
	}

	public void setCompressPersistedData(boolean aB)
	{
		compressPersistedData= aB;
	}

	public String getContextPath()
	{
		return contextPath;
	}

	public void setContextPath(String aString)
	{
		contextPath= aString;
	}

	public boolean isCatchExceptions()
	{
		return catchExceptions;
	}

	public void setCatchExceptions(boolean b)
	{
		catchExceptions= b;
	}

	public static boolean useWosClassLoader()
	{
		return System.getProperty(WosFramework.USE_WOS_CLASSLOADER) != null && System.getProperty(WosFramework.USE_WOS_CLASSLOADER).equals("true");
	}

}