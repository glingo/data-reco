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

import net.ar.webonswing.visitor.*;
import net.ar.webonswing.wrapping.*;

public class RootPaneUIContributor extends AbstractSwingComponentUIContributor
{
	public void doRenderingContribution(RenderingContributionContainer theContribManager)
	{
		VisualComponent theContentPane= theComponent.getChildAt(1).getChildAt(0);
		Visitable theRendering= theContribManager.getContainerRenderer().render(theContentPane);
		theContribManager.doContribution(theComponent, theRendering, theContribManager.getComponentIdTag(theContentPane), theContribManager.getComponentInitScripts(theContentPane)[0]);
	}
}