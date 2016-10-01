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

package net.ar.webonswing.helpers;

import java.awt.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.render.templates.*;
import net.ar.webonswing.swing.layouts.*;

import org.apache.commons.lang.*;

public class WosSwingHelper
{
	public static Template getTemplateForComponent(String theComponentName, JComponent aComponent)
	{
		Template theTemplate= null;

		if (aComponent.getLayout() instanceof TemplateLayout)
		{
			TemplateLayout theLayout= (TemplateLayout) aComponent.getLayout();
			theTemplate= theLayout.getTemplate();
		}
		else
			theTemplate= WosFramework.getInstance().getTemplateManager().getTemplateForName(theComponentName);

		return theTemplate;
	}
	
	public static String getHTMLColorString(Color aColor)
	{
		StringBuffer theResult= new StringBuffer();

		theResult.append(StringUtils.leftPad(Integer.toHexString(aColor.getRed()), 2, "0"));
		theResult.append(StringUtils.leftPad(Integer.toHexString(aColor.getGreen()), 2, "0"));
		theResult.append(StringUtils.leftPad(Integer.toHexString(aColor.getBlue()), 2, "0"));

		return theResult.toString();
	}
}