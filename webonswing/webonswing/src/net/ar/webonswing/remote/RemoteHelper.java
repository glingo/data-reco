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

package net.ar.webonswing.remote;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.wrapping.*;
import net.ar.webonswing.wrapping.swing.*;

import org.apache.commons.lang.*;
import org.apache.commons.logging.*;

public class RemoteHelper
{
	public static String str2web(String aString)
	{
		return StringUtils.replace(aString, "\n", "<br>");
	}

	public static String escapeSingleQuotes(String aString)
	{
		return StringUtils.replace(StringUtils.replace(StringUtils.replace(aString, "'", "\\'"), "\n", " "), "\r", " ");
	}

	public static String createJsInstance(String instanceName, String className, Object[] parameters)
	{
		Vector result= new Vector();

		for (int i= 0; i < parameters.length; i++)
		{
			String between= "";
			Object value= parameters[i];

			if (value instanceof Object[])
				value= createJsInstance("", "Array", (Object[])value);
			else if (value instanceof String)
				between= "'";

			result.add(between + value.toString() + between);
		}

		String var= instanceName.length() > 0 ? "var " : "";
		String assign= instanceName.length() > 0 ? "= " : "";
		String semicolon= instanceName.length() > 0 ? ";" : "";

		return var + instanceName + assign + "new " + className + "(" + StringUtils.join(result.iterator(), ", ") + ")" + semicolon;
	}

	public static void fireClickEvent(JComponent aComponent)
	{
		try
		{
			MouseEvent theClickEvent= new MouseEvent(aComponent, 500, System.currentTimeMillis(), 16, 1, 1, 1, false);
			Method theMethod= Component.class.getDeclaredMethod("processEvent", new Class[] { AWTEvent.class });
			theMethod.setAccessible(true);
			theMethod.invoke(aComponent, new Object[] { theClickEvent });
		}
		catch (Exception e)
		{
			throw new WebOnSwingException(e);
		}
	}

	public static void addMouseListeners(JComponent aComponent, String theComponentName, Tag theTag)
	{
		try
		{
			EventListener[] theListeners= aComponent.getListeners(MouseListener.class);

			if (theListeners.length > 0)
			{
				if (!theListeners[0].getClass().getMethod("mouseClicked", new Class[] { MouseEvent.class }).getDeclaringClass().equals(MouseAdapter.class))
					theTag.addAttribute(new TagAttribute("onclick", "ed.dispatch(new MouseClickedEvent('" + theComponentName + "'));"));

				if (!theListeners[0].getClass().getMethod("mousePressed", new Class[] { MouseEvent.class }).getDeclaringClass().equals(MouseAdapter.class))
					theTag.addAttribute(new TagAttribute("onmousedown", "ed.dispatch(new MousePressedEvent('" + theComponentName + "'));"));

				if (!theListeners[0].getClass().getMethod("mouseReleased", new Class[] { MouseEvent.class }).getDeclaringClass().equals(MouseAdapter.class))
					theTag.addAttribute(new TagAttribute("onmouseup", "ed.dispatch(new MouseReleasedEvent('" + theComponentName + "'));"));
			}

			if (aComponent.getListeners(MouseMotionListener.class).length > 0 && theTag != null)
				theTag.addAttribute(new TagAttribute("onmouseover", "ed.dispatch(new MouseMovedEvent('" + theComponentName + "'));"));
		}
		catch (Exception e)
		{
			LogFactory.getLog(RemoteHelper.class).error("Cannot do reflection", e);
		}
	}

	public static String getComponentInitScript(VisualComponent aComponent)
	{
		return getListenersAdds(aComponent).toString();
	}

	public static String getListenersAdds(VisualComponent aComponent)
	{

		StringBuffer theContainer= new StringBuffer();

		theContainer.append(addListeners(aComponent, ActionListener.class));
		theContainer.append(addListeners(aComponent, MouseListener.class));
		theContainer.append(addListeners(aComponent, MouseMotionListener.class));
		theContainer.append(addListeners(aComponent, ItemListener.class));
		theContainer.append(addListeners(aComponent, ListSelectionListener.class));
		theContainer.append(addListeners(aComponent, TreeSelectionListener.class));
		theContainer.append(addListeners(aComponent, ChangeListener.class));
		theContainer.append(addListeners(aComponent, PopupMenuListener.class));

		return theContainer.toString();
	}

	public static String addListeners(VisualComponent aComponent, Class theListenerClass)
	{
		EventListener[] theListeners= ((SwingComponentWrapper)aComponent).getJComponent().getListeners(theListenerClass);

		StringBuffer theContainer= new StringBuffer();

		for (int i= 0; i < theListeners.length; i++)
		{
			StringBuffer theCreationScript= new StringBuffer();

			if (theListeners[i] instanceof RemoteListener)
				theCreationScript.append(getRemoteListenerCreation((RemoteListener)theListeners[i]));
			else
				theCreationScript.append("new " + WosHelper.getNoPackageClassName(theListenerClass) + "()");

			theContainer.append("getComponent('" + aComponent.getName() + "').addListener(" + theCreationScript + ");");
		}

		return theContainer.toString();
	}

	public static String getRemoteListenerCreation(RemoteListener theRemoteListener)
	{
		List theParametersNames= new Vector();

		for (int i= 0; i < theRemoteListener.getRemoteParameters().length; i++)
		{
			Object theParameter= theRemoteListener.getRemoteParameters()[i];
			String theStringParameter= "";

			if (theParameter instanceof JComponent)
			{
				VisualComponent theComponent= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theParameter);
				theStringParameter= theComponent.getName();
			}
			else
				theStringParameter= theParameter.toString();

			theParametersNames.add("'" + theStringParameter + "'");
		}

		return "new " + theRemoteListener.getRemoteName() + " (" + StringUtils.join(theParametersNames.iterator(), ", ") + ")";
	}

	public static boolean hasRemoteListeners(Container aContainer, Class aListenerClass)
	{
		boolean hasRemote= false;
		EventListener[] theListeners= aContainer.getListeners(aListenerClass);
		for (int i= 0; i < theListeners.length; i++)
			hasRemote|= theListeners[i] instanceof RemoteListener;

		return hasRemote;
	}

	public static void removeToolTipListeners(JComponent aComponent)
	{
		MouseListener[] theMouseListeners= (MouseListener[])aComponent.getListeners(MouseListener.class);
		for (int i= 0; i < theMouseListeners.length; i++)
			if (theMouseListeners[i] instanceof ToolTipManager)
				aComponent.removeMouseListener(theMouseListeners[i]);

		MouseMotionListener[] theMouseMotionListeners= (MouseMotionListener[])aComponent.getListeners(MouseMotionListener.class);
		for (int i= 0; i < theMouseMotionListeners.length; i++)
			if (theMouseMotionListeners[i] instanceof MouseMotionAdapter)
				aComponent.removeMouseMotionListener(theMouseMotionListeners[i]);
	}

	public static String wosunescape(String aString)
	{
		return StringUtils.replace(aString, "__", "_");
	}

	public static void addDynApiInitCode(ScriptContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/dynapi/dynapi.js");
		aContributionManager.addCodeOnce("dynapi.library.setPath('" + WosFramework.getInstance().getCompleteResourcePath() + "/dynapi/');");
		aContributionManager.addCodeOnce("dynapi.library.addPackage('dynapi.widgets', dynapi.library.path + 'widgets/');");
		aContributionManager.addCodeOnce("dynapi.library.include('dynapi.library');");
		aContributionManager.addCodeOnce("dynapi.library.include('dynapi.api');");
		aContributionManager.addCodeOnce("dynapi.library.include('DynLayerInline');");
		aContributionManager.addCodeOnce("dynapi.library.include('DragEvent');");
	}
}