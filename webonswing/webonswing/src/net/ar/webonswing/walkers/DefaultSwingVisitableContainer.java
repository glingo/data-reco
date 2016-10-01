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

package net.ar.webonswing.walkers;

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.visitor.*;

/**
 * Recorrido mas comun de subcomponentes, los componentes estan en el arreglo obtenido con JComponent.getComponents() 
 * 
 * @author Fernando Damian Petrola
 */
public class DefaultSwingVisitableContainer implements VisitableContainer
{
	protected JComponent theComponent;
	
	public void setComponent(Object aComponent)
	{
		theComponent= (JComponent) aComponent;
	}

	public void accept(Visitor aVisitor)
	{
		ContainerVisitor theVisitor= (ContainerVisitor) aVisitor;
		
		theVisitor.visitContainerBegin(theComponent);

		Component[] theComponents= (theComponent).getComponents();
		for (int i= 0; i < theComponents.length; i++)
		{
			JComponent theJComponent= (JComponent) theComponents[i];
			VisitableContainer theVisitable= WosFramework.getInstance().getHierarchyWrapper().getContainerVisitable(theJComponent);
			theVisitable.setComponent(theJComponent);
			theVisitable.accept(aVisitor);
		}

		theVisitor.visitContainerEnd(theComponent);
	}
}
