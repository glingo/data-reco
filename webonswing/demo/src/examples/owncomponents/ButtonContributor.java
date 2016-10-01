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

package examples.owncomponents;

import java.util.*;

import net.ar.webonswing.remote.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.ui.*;

public class ButtonContributor extends AbstractComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer aRenderingContribManager)
	{
		Button theButton= (Button) theComponent;

		Tag theTag= new Tag("input").addAttribute("type", "button");
		theTag.addAttribute("value", theButton.getText());
		theTag.addAttribute("name", theComponent.getName());

		if (theButton.getEvents().getListenerCount() > 0)
			theTag.addAttribute(new TagAttribute("onclick", "ed.dispatch(new ActionEvent(this.name, ''));"));

		String theInitString= "getComponent('" + theComponent.getName() + "').addListener(new ActionListener());";

		aRenderingContribManager.doContribution(theComponent, theTag, null, theInitString);
	}

	public void dispatchEvents(List anEvents)
	{
		for (Iterator i= anEvents.iterator(); i.hasNext();)
		{
			RemoteEvent theEvent= (RemoteEvent) i.next();
			Button theButton= (Button) theEvent.getSource();

			if (theEvent.getType().equals("actionPerformed"))
				theButton.doClick();
		}
	}
}
