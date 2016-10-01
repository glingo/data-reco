
package net.ar.webonswing.petstore;

import java.awt.event.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.model.*;

public class ShowItemAction extends MouseAdapter
{
	private final Item item;

	public ShowItemAction(Item anItem)
	{
		super();
		this.item= anItem;
	}
	public void mouseClicked(MouseEvent aE)
	{
		ItemView itemView= new ItemView(item);
		itemView.setModal(true);
		WosFramework.showChildWindow(((JComponent) aE.getSource()).getTopLevelAncestor(), itemView);
	}
}