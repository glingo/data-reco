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

import javax.swing.*;

import org.wafer.weblog.om.*;

public class StoryViewWindow extends ApplicationFrame
{
	CompleteStoryPanel storyView;

	public StoryViewWindow()
	{
		loginRequired= true;
		storyView= new CompleteStoryPanel();
		initialize();
	}

	public StoryViewWindow(Story aStory)
	{
		loginRequired= true;
		storyView= new CompleteStoryPanel();
		initialize();
		storyView.setStory(aStory);
		storyView.initialize();
	}

	protected JComponent createBody()
	{
		storyView.setName("body");
		if (storyView.getStory() != null)
		{
			System.out.println("createBody");
			storyView.initialize();
		}
		return storyView;
	}

	public void afterAddComment(AddCommentView aView)
	{
		storyView.initialize();
		initialize();
	}

	public void afterReply(ReplyCommentView aView)
	{
		storyView.initialize();
		initialize();
	}
}
