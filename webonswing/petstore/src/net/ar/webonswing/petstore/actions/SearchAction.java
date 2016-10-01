package net.ar.webonswing.petstore.actions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.*;

public final class SearchAction implements ActionListener
{
	protected ApplicationFrame frame;
	protected JTextField keywordTextField;

	public SearchAction(ApplicationFrame aFrame, JTextField aKeywordTextField)
	{
		this.frame= aFrame;
		keywordTextField= aKeywordTextField;
	}

	public void actionPerformed(ActionEvent e)
	{
		List products= frame.getDao().searchProducts(keywordTextField.getText());
		
		WosFramework.showChildWindow(((JComponent)e.getSource()).getTopLevelAncestor(), new Search(products, keywordTextField.getText()));
	}
}