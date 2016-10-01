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

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.basic.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.wrapping.*;

import org.apache.commons.lang.*;
import org.apache.regexp.*;

public class ComboBoxUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JComboBox aComboBox= (JComboBox) getJComponent();

		String theComponentPath= theComponent.getName();

		Tag theTag= new Tag("select");

		theTag.addAttribute(new TagAttribute("name", theComponentPath));
		theTag.addAttribute(new TagAttribute("size", "1"));

		if (!aComboBox.isEnabled())
			theTag.addAttribute(new TagAttribute("DISABLED", ""));
		
		if (aComboBox.getListeners(ActionListener.class).length > 0)
			theTag.addAttribute(new TagAttribute("onchange", "ed.dispatch(new ActionEvent(this.name, '" + RemoteHelper.escapeSingleQuotes(aComboBox.getActionCommand()) + "'));"));

		RemoteHelper.addMouseListeners(aComboBox, theComponentPath, theTag);

		if (aComboBox.getWidth() != 0 || aComboBox.getHeight() != 0)
			theTag.addAttribute(new TagAttribute("style", "position:absolute; width:" + aComboBox.getWidth() + "; height:" + aComboBox.getHeight()));

		for (int i= 0; i < aComboBox.getModel().getSize(); i++)
		{
			Tag theOption= new Tag("option");
			String theValue= aComboBox.getModel().getElementAt(i).toString();

			theOption.addAttribute(new TagAttribute("value", i));

			if (aComboBox.getSelectedIndex() == i)
				theOption.addAttribute(new TagAttribute("SELECTED", ""));

			theOption.getTheMarkupContainer().addElement(new TagContent(theValue));

			theTag.getTheMarkupContainer().addElement(theOption);
		}

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JComboBox", aComboBox);
		theTemplate.addElement(new IdTagTemplateElement("theComboBox", theTag, theTag));

		theContribManager.doContribution(theComponent, theTemplate, theTag, getInitScript(theComponent));
	}

	public String getInitScript(VisualComponent aComponent)
	{
		String theComponentName= aComponent.getName();
		StringBuffer theScript= new StringBuffer();
		theScript.append("getComponent('page').addListener(new ListFinishListener('" + theComponentName + "'));");
		theScript.append(RemoteHelper.getListenersAdds(aComponent));

		return theScript.toString();
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator it= anEvents.iterator(); it.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) it.next();

			JComboBox aComboBox= (JComboBox) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (aComboBox.getModel() instanceof DefaultComboBoxModel)
			{
				if (theEvent.getName().endsWith(".values") && theEvent.getType().equals("update"))
					processUpdateEvent(theEvent, aComboBox);
				else if (theEvent.getType().equals("mouseClicked"))
					RemoteHelper.fireClickEvent(aComboBox);
			}
		}
	}

	private void processUpdateEvent(RemoteEvent theEvent, JComboBox aComboBox)
	{
		DefaultComboBoxModel theModel= (DefaultComboBoxModel) aComboBox.getModel();
		Vector theSelectedVector= new Vector();
		int theSelectedIndex= aComboBox.getSelectedIndex();

		String[] theElements= getSplitRe().split(RemoteHelper.wosunescape((String) theEvent.getParameters()[0]));

		for (int i= 0; i < theElements.length; i++)
		{
			String theValue= theElements[i];
			String theReplacedValue= StringUtils.replace(theValue, "_s", "");

			if (theModel.getSize() > i)
			{
				if (!theModel.getElementAt(i).toString().equals(theReplacedValue))
					theModel.insertElementAt(theReplacedValue, i);
			}
			else
				theModel.insertElementAt(theReplacedValue, i);

			if (!theValue.equals(theReplacedValue))
				theSelectedVector.add(new Integer(i));
		}

		for (Iterator i= theSelectedVector.iterator(); i.hasNext();)
		{
			if (aComboBox.getEditor() == null)
				aComboBox.setEditor(new BasicComboBoxEditor()); //TODO: ojo vieja - (aguante Moyo!) - bug de IBM VM

			int theNewSelectedIndex= ((Integer) i.next()).intValue();

			if (theSelectedIndex != theNewSelectedIndex)
				aComboBox.setSelectedIndex(theNewSelectedIndex);
		}
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/JList.js");
	}

	public static RE getSplitRe()
	{
		try
		{
			return new RE("_,");
		}
		catch (RESyntaxException e)
		{
			return null;
		}
	}
}