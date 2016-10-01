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

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.ui.*;
import net.ar.webonswing.wrapping.*;

public class LabelUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		JLabel aLabel= (JLabel) getJComponent();
		RemoteHelper.removeToolTipListeners(aLabel);

		Tag theTag;

		if (aLabel.getIcon() != null)
			theTag= new Tag("img").addAttribute(new TagAttribute("src", "/" + ((ImageIcon) aLabel.getIcon()).getDescription()));
		else
			theTag= new Tag("span").addTextToContainer(aLabel.getText());

		RemoteHelper.addMouseListeners(aLabel, theComponent.getName(), theTag);
		theContribManager.doContribution(theComponent, theTag, theTag, RemoteHelper.getComponentInitScript(theComponent));
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			JLabel theLabel= (JLabel) ((AbstractComponentWrapper) theEvent.getSource()).getWrappedComponent();

			if (theEvent.getType().equals("mouseClicked"))
				RemoteHelper.fireClickEvent(theLabel);
		}
	}
}