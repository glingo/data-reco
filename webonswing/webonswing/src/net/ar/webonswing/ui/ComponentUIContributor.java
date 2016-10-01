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

package net.ar.webonswing.ui;

import java.util.*;

import net.ar.webonswing.managers.contributors.*;
import net.ar.webonswing.walkers.*;

/**
 * Aporta la graficacion de un componente, resuelve los eventos generados en la UI del cliente e
 * indica cual es el ComponentTreeWalker que utiliza para recorrer su estructura.
 * 
 * @author Fernando Damian Petrola
 */
public interface ComponentUIContributor extends ComponentContributor
{
	/**
	 * Resuelve los eventos generados en la UI del cliente
	 * 
	 * @param anEvents eventos del cliente
	 */
	public void dispatchEvents(List anEvents);
	
	/**
	 * Devuelve la forma de recorrer su estructura
	 * 
	 * @return
	 */
	public VisitableContainer getContainerVisitable();
	
	/**
	 * Hace el aporte de graficacion
	 * 
	 * @param aRenderingContribManager
	 */
	public void doRenderingContribution(RenderingContributionContainer aRenderingContribManager);
}
