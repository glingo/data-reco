package examples.swingtemplate;
import javax.swing.*;


public class TestTemplateLayout1 extends JFrame
{

	private javax.swing.JPanel jContentPane= null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	/**
	 * This is the default constructor
	 */
	public TestTemplateLayout1()
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
		this.setSize(408, 295);
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
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.setBounds(19, 18, 107, 77);
			jPanel.setName("ph1");
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setBounds(190, 48, 128, 100);
			jPanel1.setName("ph2");
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			jPanel2.setBounds(53, 146, 116, 96);
			jPanel2.setName("ph3");
		}
		return jPanel2;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
