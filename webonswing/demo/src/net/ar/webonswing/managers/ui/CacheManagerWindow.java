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

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import net.ar.webonswing.*;
import net.ar.webonswing.managers.cache.*;
import net.ar.webonswing.remote.*;
import net.ar.webonswing.swing.components.*;

public class CacheManagerWindow extends JFrame
{
	public CacheManagerWindow()
	{
		JPanel theCacheComponent= new CacheManagerComponent();

		setContentPane(new ManagementComponent(theCacheComponent, "Cache Manager"));
	}

	public class MyTreeListener implements TreeSelectionListener, RemoteListener
	{
		public void valueChanged(TreeSelectionEvent e)
		{
		}
		public String getRemoteName()
		{
			return "CheckTreeSelectionListener";
		}
		public Object[] getRemoteParameters()
		{
			return new Object[0];
		}
	}

	class CacheManagerComponent extends TreeStateManagerPanel
	{
		public CacheManagerComponent()
		{
			super(new CacheWindowTreeCreator(), WosFramework.getTemplateLayoutForName("CacheManager.theTable"), new MyTreeListener());
		}

		protected void updateNodeState(CheckNode theNode)
		{
			if (theWindow != null)
				new ComponentCacheStateContainer().setCached(theWindow.getClass().getName(), theNode.getUserObject().toString(), theNode.isSelected());
		}
	}

	public static void main(String s[])
	{
		JFrame frame= new CacheManagerWindow();
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		frame.pack();
		frame.setVisible(true);
	}
}
