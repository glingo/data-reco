function CheckBoxListener (anImage)
{
	this.inheritFrom= ItemListener;
	this.inheritFrom();
	this.theImage= anImage;
	
	this.itemStateChanged= function (aItemEvent) 
	{
		var theName= "cght";
		var theSource= aItemEvent.getSource();
		var index= new Number(theSource.charAt(theSource.length-1));
		var theCurrentImage= document.getElementsByName (this.theImage)[0];
		var theFileNameIndex= theCurrentImage.src.length-8+index;
		
		var theNewCharacter= "-"
		if (theCurrentImage.src.charAt(theFileNameIndex) != theName.charAt(index))
			theNewCharacter= theName.charAt(index);

		theCurrentImage.src= theCurrentImage.src.substring(0, theFileNameIndex) + theNewCharacter + theCurrentImage.src.substring(theFileNameIndex+1, theCurrentImage.src.length);
	};
}
