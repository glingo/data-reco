// WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package weblog;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.layouts.*;

import org.apache.commons.lang.exception.*;
import org.wafer.weblog.om.*;
import org.wafer.weblog.servlet.*;
import org.wafer.weblog.util.*;

import weblog.contributors.*;

public class CompleteStoryPanel extends JPanel
{
	private Story story;

	public CompleteStoryPanel()
	{
		WosFramework.assignContributor(this, StoryPanelContributor.class);
	}

	public CompleteStoryPanel(Story aStory)
	{
		this();
		story= aStory;
		initialize();
	}

	public void initialize()
	{
		setLayout(new BorderLayout());
		removeAll();

		StoryPanel storyShortView= new StoryPanel(story);
		storyShortView.setName("story");
		storyShortView.setPreferredSize(new Dimension(400, 40));

		JButton addCommentButton= new JButton("Add comment");
		addCommentButton.addActionListener(new AddStoryCommentAction(getTopLevelAncestor(), story));
		addCommentButton.setName("addCommentButton");
		addCommentButton.setBackground(new Color(0xD1, 0xDC, 0xEB));

		JPanel commentsPanel= new JPanel(new GridLayout(0, 1));
		commentsPanel.setName("commentsPanel");

		JPanel topPanel= new JPanel(new BorderLayout());
		topPanel.add(storyShortView, BorderLayout.NORTH);
		topPanel.add(addCommentButton, BorderLayout.SOUTH);
		topPanel.setName("topPanel");

		add(topPanel, BorderLayout.NORTH);

		JScrollPane sp= new JScrollPane();
		sp.setViewportView(commentsPanel);
		sp.setPreferredSize(new Dimension(640, 300));
		add(sp, BorderLayout.CENTER);

		List comments= getStoryComments();

		for (Iterator i= comments.iterator(); i.hasNext();)
		{
			JPanel commentPanel= new JPanel(new BorderLayout());
			commentPanel.setName("commentPanel");

			final Comment comment= (Comment) i.next();

			CommentPanel commentView= new CommentPanel(comment);
			commentView.setName("commentView");
			commentPanel.add(commentView);

			JButton replyButton= new JButton("Reply");
			replyButton.setName("replyButton");
			replyButton.setBackground(new Color(0xD1, 0xDC, 0xEB));
			replyButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					ReplyCommentView replyComment= new ReplyCommentView(comment);
					replyComment.setModal(true);
					WosFramework.showAndExecute(getTopLevelAncestor(), replyComment, "afterReply");
				}
			});

			commentsPanel.add(commentPanel, BorderLayout.NORTH);
			commentPanel.add(replyButton, BorderLayout.SOUTH);
		}

		String bodyLook= WosFramework.getSessionContext().get("lookandfeel").equals("flat") ? "StoryView" : "StoryViewAqua";

		if (WosFramework.isActive())
			setLayout(new PropagateTemplateLayoutByName(WosFramework.getKeyPositionTemplateForName(bodyLook)));
	}

	private List getStoryComments()
	{
		try
		{
			return WebLogSystem.getCommentStore().getCommentsForStory(story.getId(), WebLogConstants.SETTING_MAX_COMMENTS);
		}
		catch (Exception e)
		{
			throw new NestableRuntimeException(e);
		}
	}

	public Story getStory()
	{
		return story;
	}

	public void setStory(Story aStory)
	{
		story= aStory;
	}
}