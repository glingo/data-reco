//WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

function TabbedPaneChangeListener()
{
	this.inheritFrom= ChangeListener;
	this.inheritFrom();

	this.stateChanged= function(aChangeEvent)
	{
		var theTabNames= getElement(aChangeEvent.getSource()).value.split("-");

		if (theTabNames[0] != theTabNames[1])
		{
			document.getElementById(aChangeEvent.getSource() + "_" + theTabNames[1]).className= 'tabDown';
			document.getElementById(theTabNames[1]).style.display= 'block';

			document.getElementById(aChangeEvent.getSource() + "_" + theTabNames[0]).className= 'tabUp';
			document.getElementById(theTabNames[0]).style.display= 'none';
		}

		getElement(aChangeEvent.getSource()).value= theTabNames[1];

	};
}
