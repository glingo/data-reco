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

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class DummyButtonUI extends BasicButtonUI
{
	protected boolean defaults_initialized= false;

	public static ComponentUI createUI(JComponent c)
	{
		return new DummyButtonUI();
	}

	public void installUI(JComponent c)
	{
		AbstractButton abstractButton= (AbstractButton) c;

		String pp= getPropertyPrefix();
		c.setFont(UIManager.getFont(pp + "font"));

		if (!defaults_initialized)
		{
			defaultTextIconGap= ((Integer) UIManager.get(pp + "textIconGap")).intValue();
			defaultTextShiftOffset= ((Integer) UIManager.get(pp + "textShiftOffset")).intValue();

			defaults_initialized= true;
		}

		abstractButton.setOpaque(abstractButton.isContentAreaFilled());

		if (abstractButton.getMargin() == null || (abstractButton.getMargin() instanceof UIResource))
			abstractButton.setMargin(UIManager.getInsets(pp + "margin"));

		Border defaultBorder = UIManager.getBorder(pp + "border");
		Border currentBorder= abstractButton.getBorder();
		if (currentBorder == null || currentBorder instanceof UIResource)
			abstractButton.setBorder(defaultBorder);
	}

	protected void paintButtonPressed(Graphics g, AbstractButton b)
	{
	}

	public void update(Graphics g, JComponent c)
	{
	}
}