
package net.ar.webonswing.petstore;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.actions.*;
import net.ar.webonswing.petstore.model.*;

public class Header extends JPanel
{
	public Header(Customer aCustomer, final Cart aCart, final ApplicationFrame aFrame)
	{
		JButton cartButton= new JButton("cart");
		cartButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WosFramework.showChildWindow(Header.this.getTopLevelAncestor(), new CartViewPage(aCart));
			}
		});

		final JComboBox skinCombo= new JComboBox(ApplicationFrame.getSkinManager().getSkins());
		
		JButton changeSkinButton= new JButton("change skin");
		changeSkinButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent aE)
			{
				aFrame.setCurrentSkin(skinCombo.getSelectedItem().toString());
			}
		});

		JButton searchButton= new JButton("search");
		JTextField keyword= new JTextField();
		SearchAction searchAction= new SearchAction(aFrame, keyword);
		searchButton.addActionListener(searchAction);

		JPanel signBlock= new JPanel();
		signBlock.setLayout(new GridLayout(0, 2));
		JPanel conditional= new JPanel();
		signBlock.add(conditional);

		if (aCustomer != null)
		{
			JButton signOutButton= new JButton("sign out");
			signOutButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent aE)
				{
					aFrame.setCurrentCustomer(null);
					WosFramework.showChildWindow(Header.this.getTopLevelAncestor(), new Home());
				}
			});

			JButton myAccountButton= new JButton("my account");
			myAccountButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent aE)
				{
					WosFramework.showChildWindow(Header.this.getTopLevelAncestor(), new AccountView(aFrame.getCurrentCustomer()));
				}
			});

			conditional.add(signOutButton).setName("signOutButton");
			conditional.add(myAccountButton).setName("myAccountButton");
			conditional.setName("known");
		}
		else
		{
			JButton signInButton= new JButton("sign in");
			signInButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					WosFramework.showChildWindow(Header.this.getTopLevelAncestor(), new LoginPage());
				}
			});

			conditional.add(signInButton).setName("signInButton");
			conditional.setName("anonymous");
		}

		add(cartButton).setName("cartButton");
		add(skinCombo).setName("skinCombo");
		add(changeSkinButton).setName("changeSkinButton");
		add(searchButton).setName("searchButton");
		add(keyword).setName("keyword");
		add(signBlock).setName("signBlock");
	}
}