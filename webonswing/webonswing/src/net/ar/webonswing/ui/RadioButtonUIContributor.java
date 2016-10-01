//WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package net.ar.webonswing.ui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.wrapping.*;

public class RadioButtonUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JRadioButton aRadioButton= (JRadioButton) getJComponent();

		DefaultButtonModel theButtonModel= (DefaultButtonModel) aRadioButton.getModel();
		ButtonGroup theGroup= theButtonModel.getGroup();
		AbstractButton theFirstButton= aRadioButton;
		if (theGroup != null)
			theFirstButton= (AbstractButton) theButtonModel.getGroup().getElements().nextElement();

		String name= WosFramework.getInstance().getHierarchyWrapper().getComponentWrapper(theFirstButton).getName();

		Tag theTag= new Tag("input");
		theTag.setNeedsClosure(false);
		theTag.addAttribute(new TagAttribute("type", "radio"));
		theTag.addAttribute(new TagAttribute("name", name));
		theTag.addAttribute(new TagAttribute("value", theComponent.getName()));
		theTag.addAttribute(new TagAttribute("id", theComponent.getName()));

		TagContent theTextContent= aRadioButton.getText() == null ? new TagContent("") : new TagContent(aRadioButton.getText());

		if (aRadioButton.getListeners(ActionListener.class).length > 0)
			theTag.addAttribute(new TagAttribute("onclick", "ed.dispatch(new ActionEvent(this.value, '" + RemoteHelper.escapeSingleQuotes(aRadioButton.getActionCommand()) + "'));"));

		if (aRadioButton.isSelected())
			theTag.addAttribute(new TagAttribute("CHECKED", ""));

		Template theTemplate= WosSwingHelper.getTemplateForComponent("JRadioButton", aRadioButton);

		Tag theTextTag= new Tag("label");
		theTextTag.addAttribute("for", theComponent.getName());
		theTextTag.getTheMarkupContainer().addElement(theTextContent);

		theTemplate.addElement(new IdTagTemplateElement("theButton", theTag));
		theTemplate.addElement(new IdTagTemplateElement("theText", theTextTag));

		theContribManager.doContribution(theComponent, theTemplate, theTag, RemoteHelper.getListenersAdds(theComponent));
	}

	public void dispatchEvents(List anEvents)
	{
		boolean actionFound= false;

		for (Iterator i= anEvents.iterator(); i.hasNext() && !actionFound;)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();

			VisualComponent theSourceComponent= (ComponentWrapper) theEvent.getSource();

			JRadioButton theRadioButton= (JRadioButton) getJComponent();
			DefaultButtonModel theButtonModel= (DefaultButtonModel) theRadioButton.getModel();
			ButtonGroup theGroup= theButtonModel.getGroup();

			if (theGroup != null)
			{
				if (actionFound= theEvent.getType().equals("actionPerformed"))
				{
					if (theGroup.getSelection() != theButtonModel)
						theRadioButton.doClick();
				}

				if (theEvent.getType().equals("update"))
				{
					JRadioButton theSelectedRadioButton;
					
					VisualComponent theFoundComponent= theSourceComponent.getTopParent().findComponent((String) theEvent.getParameters()[0]);
					theSelectedRadioButton= (JRadioButton) ((ComponentWrapper) theFoundComponent).getWrappedComponent();

					if (theGroup != null && theSelectedRadioButton != null && theGroup.getSelection() != theSelectedRadioButton.getModel())
						theSelectedRadioButton.doClick();
				}
			}
		}
	}
}