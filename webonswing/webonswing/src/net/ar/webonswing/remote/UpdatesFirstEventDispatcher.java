// WebOnSwing - Web Application Framework
//Copyright (C) 2003 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package net.ar.webonswing.remote;

import java.util.*;

import net.ar.webonswing.wrapping.*;

/**
 * Genera RemoteEvents a partir de una url y de un mapa de parametros. Estos
 * eventos no tienen un orden deterministico solo se asegura que todos los de
 * tipo "update" queden primeros y el evento principal (el que genero el
 * "request") quede ultimo. A futuro: guardar el orden de los eventos generados
 * del lado del cliente y procesarlos en el orden exacto que sucedieron.
 * 
 * @author Fernando Damian Petrola
 */
public class UpdatesFirstEventDispatcher extends RemoteEventDispatcher
{
	protected VisualComponent theRootComponent;

	public UpdatesFirstEventDispatcher(VisualComponent aRootComponent)
	{
		theRootComponent= aRootComponent;
	}

	public void generateAndDispatchEvents(String anUrl, Map aParameters)
	{
		Map theResult= new HashMap();
		List theListResult= new Vector();

		if (anUrl.endsWith(".event"))
		{
			int theTypePos= anUrl.substring(0, anUrl.length() - 6).lastIndexOf('.');
			String theMainEventType= anUrl.substring(theTypePos + 1, anUrl.length() - 6);
			String theMainEventSource= anUrl.substring(anUrl.indexOf('.') + 1, theTypePos);
			List theMainComponentEvents= new Vector();
			VisualComponent theMainEventComponent= theRootComponent.findComponent(theMainEventSource);

			for (Iterator i= aParameters.entrySet().iterator(); i.hasNext(); )
			{
				Map.Entry theEntry= (Map.Entry) i.next();

				VisualComponent theComponent= theRootComponent.findComponent(theEntry.getKey().toString());

				if (theComponent != null)
				{
					RemoteEvent theComponentEvent= new RemoteEvent(theComponent, "update", theEntry.getKey().toString(), (String[]) theEntry.getValue());

					if (theEntry.getKey().toString().startsWith(theMainEventSource))
						theMainComponentEvents.add(theComponentEvent);
					else
					{
						Vector theComponentEvents= (Vector) theResult.get(theComponent);

						if (theComponentEvents == null)
							theResult.put(theComponent, theComponentEvents= new Vector());

						theComponentEvents.add(theComponentEvent);
					}
				}

			}

			if (theMainEventComponent != null)
				theMainComponentEvents.add(new RemoteEvent(theMainEventComponent, theMainEventType, theMainEventType, new Object[0]));

			for (Iterator i= theResult.entrySet().iterator(); i.hasNext(); )
			{
				Map.Entry theEntry= (Map.Entry) i.next();
				theListResult.add(new Object[]{theEntry.getKey(), theEntry.getValue()});
			}
			if (theMainEventComponent != null)
				theListResult.add(new Object[]{theMainEventComponent, theMainComponentEvents});
		}

		dispatchEvents(theListResult);
	}
}