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
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package weblog.contributors;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.ui.*;
import net.ar.webonswing.wrapping.*;

import org.apache.commons.lang.*;

public class ButtonUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JButton aButton= (JButton) getJComponent();

		Tag theTag= new Tag("input").setNeedsClosure(false).addAttribute(new TagAttribute("type", "button")).addAttribute(new TagAttribute("name", theComponent.getName())).addAttribute(new TagAttribute("value", aButton.getText()));

		if (aButton.getListeners(ActionListener.class).length > 0)
			theTag.addAttribute(new TagAttribute("onclick", "ed.dispatch(new ActionEvent(this.name, '" + StringUtils.replace(aButton.getActionCommand(), "'", "\\'") + "'));"));

		theContribManager.doContribution(theComponent, theTag, theTag, RemoteHelper.getListenersAdds(theComponent));
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JButton theButton= (JButton) ((AbstractComponentWrapper)theEvent.getSource()).getWrappedComponent();

			if (theEvent.getType().equals("actionPerformed"))
				theButton.doClick();
		}
	}
}