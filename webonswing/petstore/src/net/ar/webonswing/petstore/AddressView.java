
package net.ar.webonswing.petstore;

import javax.swing.*;

import net.ar.webonswing.petstore.helpers.*;
import net.ar.webonswing.petstore.model.*;

public class AddressView extends JPanel
{
	protected Address address;
	protected Binder binder= new Binder();
	private boolean editing= true;

	public AddressView()
	{
		init();
	}

	public AddressView(Address anAddress, boolean isEditing)
	{
		editing= isEditing;
		init();
		setAddress(anAddress);
	}

	protected void init()
	{
		binder.add(getTextComponent(), "street1");
		binder.add(getTextComponent(), "street2");
		binder.add(getTextComponent(), "city");
		binder.add(getTextComponent(), "state");
		binder.add(getTextComponent(), "zipcode");
		binder.add(getTextComponent(), "country");

		binder.addViewsToContainer(this);
		binder.modelToView();
	}

	protected JComponent getTextComponent()
	{
		if (editing)
			return new JTextField();
		else
			return new JLabel();
	}

	public Address getAddress()
	{
		binder.viewToModel();
		return address;
	}

	public void setAddress(Address anAddress)
	{
		this.address= anAddress;
		binder.setModel(anAddress);
		binder.modelToView();
	}
}