function OpenDialogListener (windowNumber)
{
	this.inheritFrom= ActionListener;
	this.inheritFrom();
	
	this.super_actionPerformed= this.actionPerformed; 

	this.actionPerformed= function (anActionEvent) 
	{
		var windowName= "windowNumber" + windowNumber;
		window.open("", windowName, ""); 
		document.mainForm.target= windowName;
		this.super_actionPerformed (anActionEvent);
		theState= "";
		document.mainForm.target= "";
	};
}
