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

package net.ar.webonswing.render.templates;

import java.io.*;
import java.util.*;

/**
 * 
 * Es el encargado de manejar el reemplazo de los elementos sobre el cuerpo que tiene.
 * 
 * @author Fernando Damian Petrola
 */
public interface TemplateBody extends Cloneable, Serializable
{
	public void replace(List theElements);
	public Object getResult();
	public Object clone() throws CloneNotSupportedException;	
}
