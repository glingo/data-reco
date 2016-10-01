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

package net.ar.webonswing.managers.ui;

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.swing.layouts.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.walkers.*;

public class ComponentBackendWindow extends JFrame
{
	Window theOriginalWindow;

	public ComponentBackendWindow(Window anOriginalWindow)
	{
		theOriginalWindow= anOriginalWindow;
		new WindowTreeVisitor().walk(((RootPaneContainer) theOriginalWindow).getRootPane());
	}

	public Window getOriginalWindow()
	{
		return theOriginalWindow;
	}

	public void setOriginalWindow(Window originalWindow)
	{
		theOriginalWindow= originalWindow;
	}

	public class WindowTreeVisitor implements ContainerVisitor
	{
		JComponent theParent;

		public void walk(JComponent aComponent)
		{
			visitContainerBegin(aComponent);
		}

		public void visitContainerBegin(Object aComponent)
		{
			//TODO: continuar 
			VisitableContainer theComponentTreeWalker= null;
			theComponentTreeWalker.setComponent(aComponent);
			theComponentTreeWalker.accept(this);
		}

		public void visitContainerEnd(Object aComponent)
		{ 
			JComponent theComponent= (JComponent) aComponent;
			
			Container parent= theComponent.getParent();

			if (parent != null && parent.getLayout() instanceof TemplateLayout)
			{
				TemplateLayout theLayout= (TemplateLayout) parent.getLayout();
				String theComponentPos= theLayout.getName(theComponent);

				parent.remove(theComponent);
				parent.add(new JEditable(theComponent), theComponentPos);
			}
		}
	}
}
