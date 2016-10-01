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

package net.ar.webonswing.toolkit.lookandfeel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class DummyScrollBarUI extends BasicScrollBarUI 
{
	public static ComponentUI createUI(JComponent c)
	{
		return new DummyScrollBarUI();
	}

	public void update(Graphics g, JComponent c)
	{
	}
	
	public void installUI(JComponent c)
	{
		super.installUI(c);

		incrButton.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				scrollByUnit(1);
			}
		});

		decrButton.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				scrollByUnit(-1);
			}
		});
	}
	
	protected void layoutHScrollbar(JScrollBar sb)
	{
	}

	protected void layoutVScrollbar(JScrollBar sb)
	{
	}
	
	
}