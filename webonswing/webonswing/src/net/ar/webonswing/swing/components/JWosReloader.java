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

package net.ar.webonswing.swing.components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;

/**
 * Componente que permite recargar ciertos administradores del framework
 * 
 * @author Fernando Damian Petrola
 */
public class JWosReloader extends JPanel
{
	public JWosReloader(LayoutManager layout)
	{
		super(layout);
		addComponents();
	}

	public JWosReloader()
	{
		addComponents();
	}

	private void addComponents()
	{
		JButton thePageManagerButton= new JButton("Reload Page Manager");
		JButton theTemplateManagerButton= new JButton("Reload Template Manager");
		JButton theTreeStateManager= new JButton("Reload WindowTreeState Manager");

		thePageManagerButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WosFramework.getInstance().initPageManager();
				WosFramework.getInstance().persistPageManager();
			}
		});

		theTemplateManagerButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WosFramework.getInstance().initTemplateManager();
				WosFramework.getInstance().persistTemplateManager();
			}
		});

		theTreeStateManager.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WosFramework.getInstance().initWindowTreeStateManager();
				WosFramework.getInstance().persistWindowTreeStateManager();
			}
		});

		add(thePageManagerButton, "theElement1");
		add(theTemplateManagerButton, "theElement2");
		add(theTreeStateManager, "theElement3");
	}
}
