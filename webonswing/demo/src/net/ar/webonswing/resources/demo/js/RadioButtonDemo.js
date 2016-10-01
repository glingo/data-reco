function ChangePicture (anImage)
{
	this.inheritFrom= ActionListener;
	this.inheritFrom();
	this.theImage= anImage;
	
	this.actionPerformed= function (anActionEvent) 
	{
		getElement(this.theImage).src= "/test/images/" + anActionEvent.getActionCommand() + ".gif";
//		new ActionListener().actionPerformed (anActionEvent);
	};
}
