package net.ar.webonswing.tutorial;
import javax.swing.*;

import net.ar.webonswing.swing.components.validators.*;

public class Test2 extends JFrame
{

	private javax.swing.JPanel jContentPane= null;

	private javax.swing.JTextField jTextField= null;
	private javax.swing.JLabel jLabel= null;
	private javax.swing.JTextField jTextField1= null;
	private javax.swing.JLabel jLabel1= null;
	private JValidator validator1= null;
	private JCompareValidator validator2= null;
	private javax.swing.JButton jButton= null;
	private JGroupValidator pageValidator= null;
	private javax.swing.JLabel jLabel2= null;
	private net.ar.webonswing.swing.components.validators.JRequiredFieldValidator jRequiredFieldValidator = null;
	/**
	 * This is the default constructor
	 */
	public Test2()
	{
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(371, 292);
		this.setContentPane(getJContentPane());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane= new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJLabel(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJLabel1(), null);
			jContentPane.add(getValidator1(), null);
			jContentPane.add(getValidator2(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getPageValidator(), null);
			jContentPane.add(getJLabel2(), null);
			jContentPane.add(getJRequiredFieldValidator(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField()
	{
		if (jTextField == null)
		{
			jTextField= new javax.swing.JTextField();
			jTextField.setBounds(100, 20, 125, 29);
		}
		return jTextField;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel()
	{
		if (jLabel == null)
		{
			jLabel= new javax.swing.JLabel();
			jLabel.setBounds(20, 20, 66, 29);
			jLabel.setText("JLabel");
		}
		return jLabel;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1()
	{
		if (jTextField1 == null)
		{
			jTextField1= new javax.swing.JTextField();
			jTextField1.setBounds(100, 60, 122, 29);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1()
	{
		if (jLabel1 == null)
		{
			jLabel1= new javax.swing.JLabel();
			jLabel1.setBounds(20, 60, 66, 29);
			jLabel1.setText("JLabel");
		}
		return jLabel1;
	}
	/**
	 * This method initializes validator1
	 * 
	 * @return javax.swing.JLabel
	 */
	private JValidator getValidator1()
	{
		if (validator1 == null)
		{
			validator1= new JRequiredFieldValidator(); 
			validator1.setComponentToValidate(getJTextField());
			validator1.setBounds(240, 20, 86, 28);
			validator1.setText("");
			validator1.setRemoteValidation(true);
		}
		return validator1;
	}
	/**
	 * This method initializes validator2
	 * 
	 * @return javax.swing.JLabel
	 */
	private JValidator getValidator2()
	{
		if (validator2 == null)
		{
			validator2= new JCompareValidator();
			validator2.setComponentToValidate(getJTextField1());
			validator2.setBounds(240, 60, 87, 29);
			validator2.setText("");
			validator2.setOwnMessage("*");
			validator2.setGroupMessage("Must be greater than 4");
			validator2.setType(JCompareValidator.Type.INTEGER);
			validator2.setOperation(JCompareValidator.Operation.greaterThan);
			validator2.setValueToCompare("4");
			validator2.setRemoteValidation(true);
		}
		return validator2;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton()
	{
		if (jButton == null)
		{
			jButton= new javax.swing.JButton();
			jButton.setBounds(20, 220, 141, 41);
			jButton.setText("Save");
			jButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					if (pageValidator.doValidation())
						getJLabel2().setText("Congratulations, everything is valid!!!");
					else
						getJLabel2().setText("ERROR!!!");
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes pageValidator
	 * 
	 * @return javax.swing.JLabel
	 */
	private JValidator getPageValidator()
	{
		if (pageValidator == null)
		{
			pageValidator= new JGroupValidator("Please fix this errors", true);
			pageValidator.setBounds(20, 140, 305, 71);
			pageValidator.setText("JLabel");
			pageValidator.addValidator(getValidator1());
			pageValidator.addValidator(getValidator2());
		}
		return pageValidator;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2()
	{
		if (jLabel2 == null)
		{
			jLabel2= new javax.swing.JLabel();
			jLabel2.setBounds(20, 100, 307, 25);
			jLabel2.setText("JLabel");
		}
		return jLabel2;
	}

	public static void main(String[] args)
	{
		Test2 test2= new Test2();
		test2.setVisible(true);
	}
	/**
	 * This method initializes jRequiredFieldValidator
	 * 
	 * @return net.ar.webonswing.swing.components.validators.JRequiredFieldValidator
	 */
	private net.ar.webonswing.swing.components.validators.JRequiredFieldValidator getJRequiredFieldValidator() {
		if(jRequiredFieldValidator == null) {
			jRequiredFieldValidator = new net.ar.webonswing.swing.components.validators.JRequiredFieldValidator();
			jRequiredFieldValidator.setBounds(191, 224, 148, 28);
			jRequiredFieldValidator.setText("JLabel");
		}
		return jRequiredFieldValidator;
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
