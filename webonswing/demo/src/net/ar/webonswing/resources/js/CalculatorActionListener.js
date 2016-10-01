function CalculatorActionListener (result, firstNumber, secondNumber, operation)
{
	this.inheritFrom= ActionListener;
	this.inheritFrom();

	this.result= result;
	this.firstNumber= firstNumber;
	this.secondNumber= secondNumber;
	this.operation= operation;

	this.actionPerformed= function (anActionEvent) 
	{
		var op= getElement(this.operation);
		getElement(this.result).innerHTML= eval (getElement(this.firstNumber).value + op.options[op.selectedIndex].text + getElement(this.secondNumber).value);
	};
}
