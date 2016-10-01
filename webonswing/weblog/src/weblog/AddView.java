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

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.layouts.*;

public abstract class AddView extends ApplicationFrame implements ActionListener
{
	protected String bodyName;
	protected String title;
	protected JTextField subjectField;
	protected JTextArea storyArea;

	protected MultipleFieldsPanel addPanel;

	public AddView(String aBodyName, String aTitle)
	{
		loginRequired= true;
		bodyName= aBodyName;
		title= aTitle;
	}

	protected JComponent createBody()
	{
		if (body == null)
		{
			addPanel= new MultipleFieldsPanel(new JLabel(title));
			addPanel.addFieldPanel("Subject", subjectField= new JTextField());
			addPanel.addFieldPanel(bodyName, storyArea= new JTextArea());

			addPanel.getSubmitButton().setText("Add");
			addPanel.getSubmitButton().addActionListener(this);

			body= new JPanel(new BorderLayout());
			body.add(addPanel, BorderLayout.NORTH);
			body.setName("body");

			if (WosFramework.isActive())
				body.setLayout(new PropagateTemplateLayoutByName(WosFramework.getKeyPositionTemplateForName("Add")));
		}

		body.setBackground(new Color(0xFF, 0xF9, 0xEE));

		return body;
	}

	public JTextArea getStoryArea()
	{
		return storyArea;
	}

	public JTextField getSubjectField()
	{
		return subjectField;
	}

	public void setStoryArea(JTextArea aArea)
	{
		storyArea= aArea;
	}

	public void setSubjectField(JTextField aField)
	{
		subjectField= aField;
	}
}
