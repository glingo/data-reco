
package net.ar.webonswing.petstore;

import java.util.*;

import javax.swing.*;

import net.ar.webonswing.petstore.helpers.*;
import net.ar.webonswing.petstore.model.*;
import net.ar.webonswing.swing.components.validators.*;

public class CreditCardView extends JPanel
{
	protected CreditCard creditCard;
	protected Binder binder= new Binder();
	protected Collection validators= new Vector();
	private boolean editing= true;

	public CreditCardView()
	{
		init();
	}

	public CreditCardView(CreditCard aCreditCard, boolean isEditing)
	{
		editing= isEditing;
		setCreditCard(aCreditCard);
		init();
	}

	protected void init()
	{
		JComponent number= getTextComponent();
		JComponent expiryDate= getTextComponent();
		JComponent type= new JLabel();
		if (editing)
			type= new JComboBox(CreditCard.getTypes());

		binder.add(number, "number");
		binder.add(expiryDate, "expiryDate");
		binder.add(type, editing ? "selectedItem" : "text", "type");

		binder.addViewsToContainer(this);
		binder.modelToView();

		if (editing)
		{
			JValidator numberValidator= new JRequiredFieldValidator(number, "You must enter a number");
			JValidator expiryDateValidator= new JCompareValidator(expiryDate, "*", "You must enter a valid date", true, JCompareValidator.Operation.dataTypeCheck, JCompareValidator.Type.DATE("MM-yy"));
			JValidator typeValidator= new JRequiredFieldValidator(type, "You must enter a card type");

			add(numberValidator).setName("numberValidator");
			add(expiryDateValidator).setName("expiryDateValidator");
			add(typeValidator).setName("typeValidator");

			validators= new Vector(Arrays.asList(new JValidator[]{numberValidator, expiryDateValidator, typeValidator}));
		}
	}

	protected JComponent getTextComponent()
	{
		if (editing)
			return new JTextField();
		else
			return new JLabel();
	}

	public CreditCard getCreditCard()
	{
		binder.viewToModel();
		return creditCard;
	}

	public void setCreditCard(CreditCard aCreditCard)
	{
		this.creditCard= aCreditCard;
		binder.setModel(aCreditCard);
		binder.modelToView();
	}

	public Collection getValidators()
	{
		return validators;
	}
}