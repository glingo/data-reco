
package net.ar.webonswing.petstore;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.helpers.*;
import net.ar.webonswing.petstore.model.*;
import net.ar.webonswing.swing.layouts.*;

public class CartView extends JPanel
{
	protected Cart cart;

	public CartView(Cart aCart)
	{
		cart= aCart;
		init();
	}

	public void init()
	{
		removeAll();

		JPanel conditional= new JPanel();

		if (!cart.isEmpty())
		{
			final GenericTable table= new GenericTable(new String[]{"itemId", "description", "unitCost", "quantity", "subTotal", "remove"});

			for (Iterator i= cart.getCartItems().iterator(); i.hasNext();)
			{
				final CartItem cartItem= (CartItem) i.next();
				final Item item= cartItem.getItem();

				JLabel itemId= new JLabel(item.getItemId());
				itemId.addMouseListener(new ShowItemAction(item));

				JButton removeItem= new JButton(item.getItemId());
				removeItem.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent aE)
					{
						cart.removeItem(item);
						init();
					}
				});

				JTextField quantityField= new JTextField(cartItem.getQuantity() + "")
				{
					public void setText(String t)
					{
						super.setText(t);

						if (cartItem != null)
							cartItem.setQuantity(Integer.parseInt(t));
					}
				};

				table.addRow(new JComponent[]{itemId, new JLabel(item.getDescription()), new JLabel(item.getUnitCost() + ""), quantityField, new JLabel(cartItem.getTotal() + ""), removeItem});
			}

			JButton updateButton= new JButton("update");
			updateButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					init();
				}
			});

			JButton checkoutButton= new JButton("Checkout");
			checkoutButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Checkout checkout= new Checkout();
					checkout.setModal(true);
					WosFramework.showChildWindow(CartView.this.getTopLevelAncestor(), checkout);
				}
			});

			conditional.setName("cartActive");
			conditional.add(table);
			conditional.add(new JLabel(cart.getTotal() + "")).setName("total");
			conditional.add(updateButton).setName("updateButton");
			conditional.add(checkoutButton).setName("checkoutButton");
		}
		else
			conditional.setName("cartEmpty");

		add(conditional);
		setLayout(new PropagateTemplateLayoutByName(ApplicationFrame.getCurrentSkin().getCurrentTemplateFor("CartView.main"), new TemplateFlowLayout(), true));
	}
}