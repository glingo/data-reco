function ChangeLinkColor ()
{
	this.inheritFrom= MouseListener;
	this.inheritFrom();
	
	this.mouseMoved= function (aMouseEvent) 
	{
		getElement(aMouseEvent.getSource()).style.color = 'red';
	}
}
