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

package examples.inspector;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import net.ar.webonswing.*;
import net.ar.webonswing.managers.pages.*;
import net.ar.webonswing.managers.ui.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.wrapping.*;

public class PageInspector extends JFrame
{
	protected JList thePagesList;
	protected VisualWindow theWindow;

	public PageInspector()
	{
		initComponents();
	}

	private void initComponents()
	{
		thePagesList= new JList();
		updatePageList();
		theWindow= WosFramework.getInstance().getPageManager().getWindowForPath("/WosExamples/WebOnSwingDemo");

		thePagesList.addListSelectionListener(new ListSelectionListener()
		{

			public void valueChanged(ListSelectionEvent e)
			{
				if (e.getValueIsAdjusting() == false)
				{
					int theIndex= thePagesList.getSelectedIndex();

					String theElement= (String) ((DefaultListModel) thePagesList.getModel()).getElementAt(theIndex);

					theWindow= WosFramework.getInstance().getPageManager().getWindowForPath(theElement);

					updatePageList();
					updateContentPane();
				}
			}
		});

		updateContentPane();
	}

	void updateContentPane()
	{
		JPanel theBodyPanel= new JPanel();

		theBodyPanel.setLayout(WosFramework.getTemplateLayoutForName("PageInspector.theTable"));
		theBodyPanel.add(thePagesList, "theList");
		WindowWrapper theWrapper= ((WindowWrapper)theWindow);
		
		Window theRealWindow= (Window) theWrapper.getWrappedComponent();
		
		
		((RootPaneContainer) theRealWindow).getContentPane().setBounds(0, 0, 640, 480);
		theBodyPanel.add(((RootPaneContainer) theRealWindow).getContentPane(), "thePageView");
 
		JLink unLink= new JLink("goto page of window: " + theWrapper.getWrappedComponent().getClass().getName(), theWrapper.getWrappedComponent().getClass());
		theBodyPanel.add(unLink, "theLink");

		setContentPane(new ManagementComponent(theBodyPanel, "Page Inspector"));
	}

	protected void updatePageList()
	{
		DefaultListModel theListModel= new DefaultListModel();

		for (Iterator i= WosFramework.getInstance().getPageManager().getIterator(); i.hasNext();)
		{
			HtmlPageManagerEntry theElement= (HtmlPageManagerEntry) i.next();
			theListModel.addElement(theElement.getPath());
		}

		if (theListModel.getSize() != thePagesList.getModel().getSize())
			thePagesList.setModel(theListModel);
	}

}
