
package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.petstore.actions.*;
import net.ar.webonswing.petstore.model.*;

public class Checkout extends ApplicationFrame 
{
	public Checkout()
	{
		init();
	}

	protected JComponent getBody()
	{
		Customer customer= getCurrentCustomer();
		JPanel panel= new JPanel();

		if (customer != null)
		{
			AddressView addressView= new AddressView(customer.getAddress(), false);
			CreditCardView creditCardView= new CreditCardView(customer.getCreditCard(), false);
			CartView cartView= new CartView(getCart());
			JButton submitButton= new JButton("submit");
			submitButton.addActionListener(new CheckOutAction(this));

			panel.add(addressView).setName("address");
			panel.add(creditCardView).setName("creditCard");
			panel.add(cartView).setName("cart");
			panel.add(submitButton).setName("submitButton");

			addressView.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("CustomerView.address"));
			creditCardView.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("CustomerView.creditCard"));

			panel.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("Checkout.main"));
		}
		else
		{
			panel.add(new JLabel("Error")).setName("title");
			panel.add(new JLabel("Cannot make the checkout, you have to sign in first!")).setName("message");
			panel.setLayout(ApplicationFrame.getCurrentSkin().getCurrentPropagateTemplateFor("Message.main"));
		}

		return panel;
	}
}