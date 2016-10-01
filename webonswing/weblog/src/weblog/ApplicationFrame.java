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

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.swing.components.*;
import net.ar.webonswing.swing.layouts.*;

import org.apache.commons.logging.*;
import org.wafer.weblog.om.*;
import org.wafer.weblog.servlet.*;

public class ApplicationFrame extends JDialog
{
	public static final String ACTIVE_USER= "activeUser";
	public static final String LOOKANDFEEL= "lookandfeel";
	public static final String WEBLOGSYSTEM= "weblogsystem";

	protected boolean loginRequired;
	protected JComponent header;
	protected JComponent body;
	protected JComponent menu;
	protected JComponent footer;
	protected User activeUser;

	public ApplicationFrame()
	{
		loginRequired= false;

		if (WosFramework.getSessionContext().get(WEBLOGSYSTEM) == null || WebLogSystem.getUserStore() == null)
			try
			{
				Properties p= new Properties();
				p.load(Home.class.getResourceAsStream("/weblog.properties"));
				WebLogSystem.init(p);
				WosFramework.getSessionContext().put(WEBLOGSYSTEM, "init");
				WosFramework.getSessionContext().put(LOOKANDFEEL, "flat");
			}
			catch (Exception e)
			{
				LogFactory.getLog(ApplicationFrame.class).error("Cannot init ApplicationFrame", e);
			}
	}

	public void initialize()
	{
		activeUser= (User) WosFramework.getSessionContext().get(ACTIVE_USER);

		initBody();

		header= createHeader();
		menu= createMenu();
		footer= createFooter();

		JPanel panel= (JPanel) getContentPane();

		panel.setLayout(new BorderLayout());

		panel.removeAll();

		panel.add(header, BorderLayout.NORTH);
		panel.add(menu, BorderLayout.WEST);
		panel.add(body, BorderLayout.CENTER);
		panel.add(footer, BorderLayout.SOUTH);

		String look= WosFramework.getSessionContext().get(LOOKANDFEEL).equals("flat") ? "ApplicationFrame" : "Aqua";

		if (WosFramework.isActive())
			getContentPane().setLayout(new PropagateTemplateLayoutByName(WosFramework.getKeyPositionTemplateForName(look)));
		else
			pack();
	}

	public void initBody()
	{
		if (!loginRequired || activeUser != null)
			body= createBody();
		else
			body= Login.getLoginPanel();

		body.setPreferredSize(new Dimension(640, 300));
	}

	protected JComponent createBody()
	{
		return new JPanel();
	}

	protected JComponent createFooter()
	{
		JPanel newFooter= new JPanel();
		newFooter.setName("footer");

		JLabel by= new JLabel("Fernando Damian Petrola");
		by.setName("by");
		newFooter.add(by);
		newFooter.setBackground(new Color(0xFF, 0xFF, 0xEE));

		return newFooter;
	}

	protected JComponent createMenu()
	{
		JPanel newMenu= new JPanel(new BorderLayout());
		newMenu.setName("menu");

		JLink homeLink= new JLink("Home", Home.class);
		homeLink.setName("home");

		JLink aquaLink= new JLink("Aqua Look&Feel", "");
		aquaLink.setName("aqua");
		aquaLink.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				WosFramework.getSessionContext().put(LOOKANDFEEL, "aqua");
				initialize();
			}
		});

		JLink flatLink= new JLink("Flat Look&Feel", "");
		flatLink.setName("flat");
		flatLink.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				WosFramework.getSessionContext().put(LOOKANDFEEL, "flat");
				initialize();
			}
		});

		newMenu.add(homeLink, BorderLayout.NORTH);
		newMenu.add(aquaLink, BorderLayout.CENTER);
		newMenu.add(flatLink, BorderLayout.SOUTH);
		newMenu.setBackground(new Color(0xF8, 0xFF, 0xEE));

		return newMenu;
	}

	protected JComponent createHeader()
	{
		JComponent log= null;

		if (activeUser == null)
			log= new JLink("Login", Login.class);
		else
		{
			log= new JLink("Logout", "");
			log.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg0)
				{
					activeUser= null;
					WosFramework.getSessionContext().remove(ACTIVE_USER);
					WosFramework.showAndExecute(ApplicationFrame.this, new Home(), null);
					WosFramework.hide(ApplicationFrame.this);
				}
			});
		}

		log.setName("login");

		JLabel image= new JLabel();
		image.setName("image");
		image.setText("");
		image.setIcon(new ImageIcon((WosFramework.isActive() ? "WebOnSwingWeblog/" : "") + "resources/images/title.gif"));
		image.setPreferredSize(new Dimension(0, 60));
		image.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel help= new JLabel("Help");
		help.setName("help");
		help.setPreferredSize(new Dimension(170, 16));
		help.setVerticalTextPosition(SwingConstants.CENTER);
		help.setVerticalAlignment(SwingConstants.CENTER);
		help.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel username= new JLabel(activeUser != null ? "Hello " + activeUser.getUsername() + "!" : "");
		username.setName("user");
		username.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel= new JPanel();

		panel.setLayout(new BorderLayout());

		panel.add(image, BorderLayout.NORTH);
		panel.add(log, BorderLayout.WEST);
		panel.add(username, BorderLayout.CENTER);
		panel.add(help, BorderLayout.EAST);

		panel.setSize(354, 144);
		panel.setName("header");
		panel.setBackground(Color.white);

		return panel;
	}
	public User getActiveUser()
	{
		return activeUser;
	}

	public void setActiveUser(User aUser)
	{
		activeUser= aUser;
	}

}
