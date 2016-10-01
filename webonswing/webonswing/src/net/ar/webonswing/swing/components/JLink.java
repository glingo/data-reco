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
import net.ar.webonswing.helpers.*;

/**
 * Componente que representa un hipervinculo
 * 
 * @author Fernando Damian Petrola
 */
public class JLink extends JComponent
{
	private boolean mouseOver;
	private String url;
	private RootPaneContainer rootPaneContainer;
	private String label;
	private Class rootPaneContainerClass;
	private static final String uiClassID= "LinkUI";

	public JLink ()
	{
	}

	public JLink(String aLabel, RootPaneContainer aRootPaneContainer)
	{
		label= aLabel;
		rootPaneContainer= aRootPaneContainer;
		init();
	}

	public JLink(String aLabel, Class aRootPaneContainerClass)
	{
		label= aLabel;
		rootPaneContainerClass= aRootPaneContainerClass;
		rootPaneContainer= null;
		init();
	}

	public JLink(String aLabel, String anUrl)
	{
		label= aLabel;
		url= anUrl;
		init();
	}

	protected void init()
	{
		setPreferredSize(new Dimension(100, 100));

		if (!WosFramework.isActive())
		{
			addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					RootPaneContainer windowToShow= null;

					if (rootPaneContainerClass != null)
						windowToShow= (RootPaneContainer) WosHelper.createClassInstance(rootPaneContainerClass.getName());
					else if (rootPaneContainer != null)
						windowToShow= rootPaneContainer;

					if (windowToShow != null)
					{
						WosFramework.showAndExecute(getTopLevelAncestor(), windowToShow, null);
						WosFramework.hide(getTopLevelAncestor());
					}
				}

				public void mouseEntered(MouseEvent e)
				{
					mouseOver= true;
					repaint();
				}

				public void mouseExited(MouseEvent e)
				{
					mouseOver= false;
					repaint();
				}
			});
		}
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Font font= mouseOver ? new Font(getFont().getFontName(), Font.BOLD, getFont().getSize()) : getFont();
		g.setFont(font);
		int textWidth= SwingUtilities.computeStringWidth(Toolkit.getDefaultToolkit().getFontMetrics(font), label);
		g.drawString(label, getWidth() / 2 - textWidth / 2, getHeight() / 2);
	}

	public String getUIClassID()
	{
		return uiClassID;
	}

	public static String getUiClassID()
	{
		return uiClassID;
	}

	public String getLabel()
	{
		return label;
	}
	public void setLabel(String aLabel)
	{
		label= aLabel;
	}
	public RootPaneContainer getRootPaneContainer()
	{
		return rootPaneContainer;
	}
	public void setRootPaneContainer(RootPaneContainer aRootPaneContainer)
	{
		rootPaneContainer= aRootPaneContainer;
	}
	public Class getRootPaneContainerClass()
	{
		return rootPaneContainerClass;
	}
	public void setRootPaneContainerClass(Class aRootPaneContainerClass)
	{
		rootPaneContainerClass= aRootPaneContainerClass;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String aUrl)
	{
		url= aUrl;
	}
}
