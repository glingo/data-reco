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

import javax.swing.*;

public class MultipleFieldsPanel extends JPanel
{
	protected JPanel fieldsPanel;
	protected JButton submitButton;
	protected JComponent titleComponent;

	public MultipleFieldsPanel(JComponent aTitleComponent)
	{
		titleComponent= aTitleComponent;
		initialize();
	}

	protected void initialize()
	{
		setName("MultipleFieldsPanel");
		setLayout(new BorderLayout());

		titleComponent.setName("titleLabel");

		fieldsPanel= new JPanel();
		fieldsPanel.setName("fieldsPanel");
		fieldsPanel.setLayout(new GridLayout(0, 1));
		fieldsPanel.setBackground(new Color(0xFF, 0xF9, 0xEE));

		submitButton= submitButton= new JButton();
		submitButton.setName("submitButton");
		submitButton.setText("Submit");
		submitButton.setPreferredSize(new Dimension(102, 30));
		submitButton.setBackground(new Color(0xD1, 0xDC, 0xEB));
		
		add(titleComponent, BorderLayout.NORTH);
		
		JScrollPane sp= new JScrollPane(fieldsPanel);
		add(sp, BorderLayout.CENTER);
		add(submitButton, BorderLayout.SOUTH);
		setBackground(new Color(0xF9, 0xF9, 0xF9));
		setSize(513, 200);
	}

	public void addFieldPanel(String aFieldName, JComponent aComponent)
	{
		JPanel panel= new JPanel();
		panel.setName("fieldPanel");

		JLabel label= new JLabel();
		label.setText(aFieldName);
		label.setName("label");

		aComponent.setName("field");

		panel.setLayout(new BorderLayout());
		panel.add(label, BorderLayout.WEST);
		panel.add(aComponent, BorderLayout.CENTER);
		panel.setBackground(new Color(0xFF, 0xF9, 0xEE));
		
		fieldsPanel.add(panel);
	}
	public JPanel getFieldsPanel()
	{
		return fieldsPanel;
	}

	public JButton getSubmitButton()
	{
		return submitButton;
	}

	public void setFieldsPanel(JPanel aPanel)
	{
		fieldsPanel= aPanel;
	}

	public void setSubmitButton(JButton aButton)
	{
		submitButton= aButton;
	}
}
