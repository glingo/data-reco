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
import javax.swing.event.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.managers.script.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.walkers.*;
import net.ar.webonswing.wrapping.*;

import org.apache.commons.lang.*;

public class ListUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JList aList= (JList) getJComponent();

		ToolTipManager toolTipManager= ToolTipManager.sharedInstance();
		toolTipManager.unregisterComponent(aList);
		aList.setAutoscrolls(false);

		Tag theTag= new Tag("select");

		theTag.addAttribute(new TagAttribute("name", theComponent.getName()));
		theTag.addAttribute(new TagAttribute("size", "2"));
		if (aList.getWidth() != 0 || aList.getHeight() != 0)
			theTag.addAttribute(new TagAttribute("style", "position:absolute; width:" + aList.getWidth() + "; height:" + aList.getHeight()));

		if (aList.getListeners(ListSelectionListener.class).length > 0)
			theTag.addAttribute(new TagAttribute("onchange", "ed.dispatch(new ListSelectionEvent(this.name));"));

		if (aList.getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
			theTag.addAttribute(new TagAttribute("MULTIPLE", ""));

		for (int i= 0; i < aList.getModel().getSize(); i++)
		{
			Tag theOption= new Tag("option");
			String theValue= aList.getModel().getElementAt(i).toString();

			theOption.addAttribute(new TagAttribute("value", i));

			if (aList.isSelectedIndex(i))
				theOption.addAttribute(new TagAttribute("SELECTED", ""));

			theOption.getTheMarkupContainer().addElement(new TagContent(theValue));

			theTag.getTheMarkupContainer().addElement(theOption);
		}

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JList", aList);
		theTemplate.addElement(new IdTagTemplateElement("theList", theTag));

		theContribManager.doContribution(theComponent, theTemplate, theTag, getInitScript(theComponent));
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator it= anEvents.iterator(); it.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) it.next();

			JList aList= (JList) ((ComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (theEvent.getType().equals("update") && theEvent.getName().endsWith(".values"))
			{
				boolean isDifferent= false;

				final String[] theElements= ComboBoxUIContributor.getSplitRe().split(RemoteHelper.wosunescape((String) theEvent.getParameters()[0]));
				Vector theSelectedVector= new Vector();

				for (int i= 0; i < theElements.length; i++)
				{
					String theValue= theElements[i];
					String theReplacedValue= StringUtils.replace(theValue, "_s", "");

					if (!aList.getModel().getElementAt(i).toString().equals(theReplacedValue))
						isDifferent= true;

					if (aList.getModel() instanceof DefaultListModel)
					{
						DefaultListModel theModel= (DefaultListModel) aList.getModel();

						if (theModel.getSize() > i)
						{
							if (!theModel.getElementAt(i).equals(theReplacedValue))
								theModel.set(i, theReplacedValue);
						}
						else
							theModel.add(i, theReplacedValue);
					}

					if (!theValue.equals(theReplacedValue))
						theSelectedVector.add(new Integer(i));

					theElements[i]= theReplacedValue;
				}

				if (!(aList.getModel() instanceof DefaultListModel) && isDifferent)
				{
					aList.setModel(new AbstractListModel()
					{
						public int getSize()
						{
							return theElements.length;
						}
						public Object getElementAt(int i)
						{
							return theElements[i];
						}
					});
				}

				int j= 0;
				int[] theSelectedIndexs= new int[theSelectedVector.size()];
				for (Iterator i= theSelectedVector.iterator(); i.hasNext(); theSelectedIndexs[j++]= ((Integer) i.next()).intValue());

				if (!Arrays.equals(theSelectedIndexs, aList.getSelectedIndices()))
				{
					aList.setValueIsAdjusting(true);
					aList.clearSelection();
					aList.setSelectedIndices(theSelectedIndexs);
					aList.setValueIsAdjusting(false);
				}
			}
		}
	}
	public String getInitScript(VisualComponent aComponent)
	{
		StringBuffer theScript= new StringBuffer();
		theScript.append("getComponent('page').addListener(new ListFinishListener('" + aComponent.getName() + "'));");
		theScript.append(RemoteHelper.getListenersAdds(aComponent));

		return theScript.toString();
	}

	public void doScriptContribution(ScriptContributionContainer aContributionManager)
	{
		aContributionManager.addInclude(WosFramework.getInstance().getCompleteResourcePath() + "/js/JList.js");
	}

	public VisitableContainer getContainerVisitable()
	{
		return new NullContainerVisitable();
	}
}