package net.ar.webonswing.tutorial;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;

public class UserList extends JFrame
{
	public UserList()
	{
		init();
	}

	protected void init()
	{
		JPanel usersPanel= new JPanel();
		usersPanel.setName("usersPanel");
		usersPanel.setLayout(new GridLayout(0, 1));
		
		for (int i= 0; i < User.users.length; i++)
		{
			final User user= User.users[i];

			JButton button= new JButton(User.users[i].getLastName());
			button.setName("button");
			button.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					UserDetails newWindow= new UserDetails(user);
					newWindow.setModal(true);
					WosFramework.showAndExecute(UserList.this, newWindow, "processResult");
				}
			});

			usersPanel.add(button);
		}

		JPanel contentPane= new JPanel();
		contentPane.setName("contentPane");
		setContentPane(contentPane);
		contentPane.add(usersPanel);

		if (WosFramework.isActive())
			contentPane.setLayout(WosFramework.getPropagateTemplateLayoutByNameFor("UserList"));
	}

	public void processResult(UserDetails userDetails)
	{
		User user= userDetails.user;

		User.users[user.getId()].setFirstName(user.getFirstName());
		User.users[user.getId()].setLastName(user.getLastName());
		init();
	}
}
