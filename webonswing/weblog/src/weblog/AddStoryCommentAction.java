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

import net.ar.webonswing.*;

import org.wafer.weblog.om.*;

final class AddStoryCommentAction implements ActionListener
{
	private Container parentDialog;
	private Story story;

	public AddStoryCommentAction(Container aParentDialog, Story aStory)
	{
		story= aStory;
		parentDialog= aParentDialog;
	}

	public void actionPerformed(ActionEvent e)
	{
		AddCommentView addCommentDialog= new AddCommentView(story);
		addCommentDialog.setModal(true);
		WosFramework.showAndExecute(parentDialog, addCommentDialog, "afterAddComment");
	}
}