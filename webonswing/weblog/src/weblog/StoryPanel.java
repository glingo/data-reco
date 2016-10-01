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
import java.text.*;

import javax.swing.*;

import org.wafer.weblog.om.*;

public class StoryPanel extends JPanel
{
	protected Story story;

	public StoryPanel(Story anStory)
	{
		story= anStory;
		initialize();
	}

	protected void initialize()
	{
		setLayout(new BorderLayout());
		setName("headerPanel");

		JLabel subjectLabel= new JLabel(story.getSubject());
		subjectLabel.setName("subjectLabel");

		JLabel dateLabel= new JLabel(DateFormat.getDateInstance().format(story.getDate()));
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setName("dateLabel");

		JLabel userLabel= new JLabel(story.getUserId());
		userLabel.setName("userLabel");

		JLabel contentLabel= new JLabel(story.getContent());
		contentLabel.setName("contentLabel");

		add(subjectLabel, BorderLayout.WEST);
		add(dateLabel, BorderLayout.CENTER);
		add(userLabel, BorderLayout.EAST);
		add(contentLabel, BorderLayout.SOUTH);
		setBackground(new Color(0xFF, 0xF9, 0xEE));
		
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	}
}
