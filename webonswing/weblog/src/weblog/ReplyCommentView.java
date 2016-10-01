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

import java.awt.event.*;

import net.ar.webonswing.*;
import weblog.contributors.*;

import org.wafer.weblog.om.*;
import org.wafer.weblog.servlet.*;

public class ReplyCommentView extends AddView
{
	protected Comment comment;

	public ReplyCommentView()
	{
		super("Reply", "Reply the comment");
		loginRequired= true;
		initialize();
		
		WosFramework.assignContributor(body, ReplyCommentContributor.class);
	}

	public ReplyCommentView(Comment aComment)
	{
		this();
		
		comment= aComment;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			WebLogSystem.getCommentStore().create(activeUser.getUsername(), getSubjectField().getText(), getStoryArea().getText(), comment);
			WosFramework.hide(ReplyCommentView.this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public Comment getComment()
	{
		return comment;
	}

	public void setComment(Comment aComment)
	{
		comment= aComment;
	}

}
