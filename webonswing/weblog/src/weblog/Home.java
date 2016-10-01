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

package weblog;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.layouts.*;

import org.apache.commons.lang.exception.*;
import org.apache.commons.logging.*;
import org.wafer.weblog.om.*;
import org.wafer.weblog.servlet.*;

public class Home extends ApplicationFrame
{
	private final class ViewCommentsAction implements ActionListener
	{
		private Story story;

		public ViewCommentsAction(Story aStory)
		{
			story= aStory;
		}

		public void actionPerformed(ActionEvent e)
		{
			WosFramework.showChildWindow(Home.this, new StoryViewWindow(story));
			WosFramework.hide(Home.this);
		}
	}

	public Home()
	{
		initialize();
	}

	protected JComponent createBody()
	{
		JPanel newBody= new JPanel();
		newBody.setName("body");

		String lf= (String) WosFramework.getSessionContext().get("lookandfeel");
		String bodyLook= lf != null && lf.equals("aqua") ? "Aqua.body" : "Home";

		if (WosFramework.isActive())
			newBody.setLayout(new PropagateTemplateLayoutByName(WosFramework.getKeyPositionTemplateForName(bodyLook)));
		else
			newBody.setLayout(new BorderLayout());

		JButton addStoryButton= new JButton("Add story");
		addStoryButton.setName("addStoryButton");
		addStoryButton.setBackground(new Color(0xD1, 0xDC, 0xEB));
		addStoryButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				AddStoryView addStoryDialog= new AddStoryView();
				addStoryDialog.setModal(true);
				WosFramework.showAndExecute(Home.this, addStoryDialog, "afterStoryAdd");
			}
		});

		JPanel storiesPanel= new JPanel();
		storiesPanel.setName("storiesPanel");
		storiesPanel.setLayout(new GridLayout(0, 1));

		newBody.add(addStoryButton, BorderLayout.NORTH);

		JScrollPane scrollPanel= new JScrollPane();
		scrollPanel.setViewportView(storiesPanel);
		newBody.add(scrollPanel, BorderLayout.CENTER);

		List stories= getRecentStories();
		for (Iterator i= stories.iterator(); i.hasNext();)
		{
			JPanel storyPanel= new JPanel(new BorderLayout());
			storyPanel.setName("storyPanel");

			Story story= ((Story) i.next());

			StoryPanel storyView= new StoryPanel(story);
			storyView.setName("story");

			JButton viewCommentsButton= new JButton("View Comments");
			viewCommentsButton.setName("viewCommentsButton");
			viewCommentsButton.addActionListener(new ViewCommentsAction(story));
			viewCommentsButton.setBackground(new Color(0xD1, 0xDC, 0xEB));

			JButton addCommentButton= new JButton("Add Comment");
			addCommentButton.setName("addCommentButton");
			addCommentButton.addActionListener(new AddStoryCommentAction(this, story));
			addCommentButton.setBackground(new Color(0xD1, 0xDC, 0xEB));

			storyPanel.add(storyView, BorderLayout.NORTH);
			storyPanel.add(viewCommentsButton, BorderLayout.WEST);
			storyPanel.add(addCommentButton, BorderLayout.CENTER);

			storiesPanel.add(storyPanel);
		}

		return newBody;
	}

	public void afterAddComment(AddCommentView aView)
	{
		initialize();
	}

	public void afterStoryAdd(AddStoryView aView)
	{
		initialize();
	}

	private List getRecentStories()
	{
		try
		{
			StoryStore storyStore= WebLogSystem.getStoryStore();
			return storyStore.getRecentStories(6);
		}
		catch (Exception e)
		{
			LogFactory.getLog(Home.class).error("Cannot get recent stories", e);
			throw new NestableRuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception
	{
		Home home= new Home();
		home.setVisible(true);
	}

}
