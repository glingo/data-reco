function AddProductToList (aTextField, aList)
{
	this.inheritFrom= ActionListener;
	this.inheritFrom();

	this.theTextField= aTextField;
	this.theList= aList;

	this.actionPerformed= function (anActionEvent) 
	{
		var theNewOption = document.createElement("OPTION");
		theNewOption.text= getElement(this.theTextField).value;
		theNewOption.value= getElement(this.theTextField).value;
		var theListElement= getElement(this.theList);
		theListElement.options.add(theNewOption);
	};
}
