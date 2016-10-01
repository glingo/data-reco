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

import java.awt.*;
import java.util.*;

import net.ar.webonswing.wrapping.*;

public class Window1 extends DefaultVisualWindow
{
	public Window1()
	{
		setName("Window1");

		Panel thePanel= new Panel();
		final Label theLabel= new Label("label 1");

		thePanel.setBounds(new Rectangle(0, 0, 500, 500));
		theLabel.setBounds(new Rectangle(0, 0, 190, 50));

		for (int i= 1; i < 10; i++)
		{
			Button theButton= new Button("button " + i);
			theButton.setBounds(new Rectangle(50 * i, 50 * i, 45, 45));

			theButton.addClickListener(new ClickListener()
			{
				public void clickPerformed(EventObject anEvent)
				{
					theLabel.setText(theLabel.getText() + " - " + ((Button) anEvent.getSource()).getText());
				}
			});

			thePanel.addChild(theButton);
		}

		thePanel.addChild(theLabel);
		setRootComponent(thePanel);
	}
}
