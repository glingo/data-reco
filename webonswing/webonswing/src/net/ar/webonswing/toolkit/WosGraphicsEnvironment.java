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

package net.ar.webonswing.toolkit;

import java.awt.*;
import java.awt.image.*;

import sun.awt.*;
import sun.java2d.*;

public class WosGraphicsEnvironment extends SunGraphicsEnvironment
{
	private GraphicsDevice[] defaultDevice= new GraphicsDevice[] { new WosGraphicsDevice(1)};

	protected int getNumScreens()
	{
		return 1;
	}

	protected GraphicsDevice makeScreenDevice(int screenNum)
	{
		return defaultDevice[0];
	}

	public Graphics2D createGraphics(BufferedImage image)
	{
		try
		{
			return super.createGraphics(image);
		}
		catch (Exception e)
		{
			throw new AWTError("Cannot create graphics with image: " + image);
		}
	}

	public GraphicsDevice[] getScreenDevices()
	{
		return defaultDevice;
	}

	public GraphicsDevice getDefaultScreenDevice()
	{
		return getScreenDevices()[0];
	}
	protected FontProperties createFontProperties()
	{
		return new FontProperties()
		{
			public String getFallbackFamilyName(String fontName, String defaultFallback)
			{
				String theCompatibilityFamilyName= getCompatibilityFamilyName(fontName);
				return theCompatibilityFamilyName != null ? theCompatibilityFamilyName : defaultFallback;
			}
		};
	}
}
