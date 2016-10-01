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

import net.ar.webonswing.render.*;
import net.ar.webonswing.render.markup.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

/**
 * Almacena los resultados de graficacion, tags de identificacion y scripts de un componente dado.
 *  
 * @author Fernando Damian Petrola
 */
public class RenderingContributionContainer
{
	protected ComponentRenderer theContainerRenderer;
	protected HashMap theComponentProps;

	public RenderingContributionContainer(ComponentRenderer aContainerRenderer)
	{
		theComponentProps= new HashMap();
		theContainerRenderer= aContainerRenderer;
	}

	/**
	 * Agrega un aporte de graficacion del componente "aComponent".
	 * Estos resultados van a ser utilizados por otro componente o la pagina que lo contiene para 
	 * representarlo graficamente; ademas el tag identificador va a ser utilizado para mezclarlo con el tag
	 * encontrado en la plantilla asociada, y los scripts generados serviran para inicializar al componente.
	 * 
	 * @param aComponent Componente graficado
	 * @param aContent Resultado de graficacion
	 * @param anIdTag Tag identificador del componente que va a ser mezclado con el tag encontrado en la plantilla
	 * @param anInitScript Scripts de inicializacion del componente
	 */
	public void doContribution(VisualComponent aComponent, Visitable aContent, Tag anIdTag, String[] anInitScript)
	{
		List theProps= new Vector();
		theProps.add(0, aContent);
		theProps.add(1, anIdTag);
		theProps.add(2, anInitScript);

		theComponentProps.put(aComponent, theProps);
	}

	public void doContribution(VisualComponent aComponent, Visitable aContent, Tag anIdTag, String anInitScript)
	{
		List theProps= new Vector();
		theProps.add(0, aContent);
		theProps.add(1, anIdTag);
		theProps.add(2, new String[] { anInitScript });

		theComponentProps.put(aComponent, theProps);
	}

	public List getComponentProps(VisualComponent aComponent)
	{
		return (List) theComponentProps.get(aComponent);
	}

	public void setComponentProps(VisualComponent aComponent, List aList)
	{
		theComponentProps.put(aComponent, aList);
	}

	public Visitable getComponentRendering(VisualComponent aComponent)
	{
		List theProps= (List) theComponentProps.get(aComponent);

		if (theProps == null)
			return new HtmlDivsTemplate();
		else
			return (Visitable) theProps.get(0);
	}

	public Tag getComponentIdTag(VisualComponent aComponent)
	{
		return (Tag) ((List) theComponentProps.get(aComponent)).get(1);
	}

	public String[] getComponentInitScripts(VisualComponent aComponent)
	{
		return (String[]) ((List) theComponentProps.get(aComponent)).get(2);
	}

	public ComponentRenderer getContainerRenderer()
	{
		return theContainerRenderer;
	}

	public void setContainerRenderer(ComponentRenderer aRenderer)
	{
		theContainerRenderer= aRenderer;
	}
}
