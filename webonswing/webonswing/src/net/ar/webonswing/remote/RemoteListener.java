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

package net.ar.webonswing.remote;

import java.util.*;

/**
 * Interfaz que posibilita que los eventos sean escuchados del lado del cliente.
 * Agregando esta interfaz a cualquier "listener" de Swing se le informa al framework que tiene que agregar un listener
 * con nombre "getRemoteName()" y parametros "getRemoteParameters()" del lado del cliente. El nombre seria, por ejemplo,
 * una clase javascript que es subclase de algun listener equivalente al de Swing. Y los parametros van a traducirse, 
 * si son componentes, en los nombres de estos en web (ej: abab.JButton01); y si son cadenas van a quedar iguales.
 * 
 * @author Fernando Damian Petrola
 */
public interface RemoteListener extends EventListener
{
	public String getRemoteName();
	public Object[] getRemoteParameters();
}
