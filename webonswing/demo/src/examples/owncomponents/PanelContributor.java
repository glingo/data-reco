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

package examples.owncomponents;

import java.util.*;

import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.render.templates.html.*;
import net.ar.webonswing.ui.*;
import net.ar.webonswing.wrapping.*;

public class PanelContributor extends AbstractComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer aRenderingContribManager)
	{
		Template theTemplate= new HtmlDivsTemplate();
		StringBuffer theInitScript= new StringBuffer();

		for (Iterator i= theComponent.getChilds().iterator(); i.hasNext();)
		{
			VisualComponent theSubComponent= (VisualComponent) i.next();
			theTemplate.addElement(new IdTagTemplateElement(theSubComponent, aRenderingContribManager.getContainerRenderer().render(theSubComponent), aRenderingContribManager.getComponentIdTag(theSubComponent)));
			theInitScript.append(aRenderingContribManager.getComponentInitScripts(theSubComponent)[0]);
		}

		aRenderingContribManager.doContribution(theComponent, theTemplate, null, theInitScript.toString());
	}
}