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
import java.util.*;

public class WosBufferedImage extends BufferedImage
{ 
	private Hashtable properties;

	public WosBufferedImage(int width, int height, int imageType)
	{
		super(width, height, imageType);
	}

	public WosBufferedImage(int width, int height, int imageType, IndexColorModel cm)
	{
		super(width, height, imageType, cm);
	}

	public WosBufferedImage(ColorModel cm, WritableRaster raster, boolean isRasterPremultiplied, Hashtable aProperties)
	{
		super(cm, raster, isRasterPremultiplied, aProperties);
		this.properties= aProperties;
	}

	public Graphics2D createGraphics()
	{
		try
		{
			return super.createGraphics();
		}
		catch (Exception e)
		{
		}

		return new WosGraphicsEnvironment().createGraphics(this);
	}

	public BufferedImage getSubimage(int x, int y, int w, int h)
	{
		return new WosBufferedImage(getColorModel(), getRaster().createWritableChild(x, y, w, h, 0, 0, null), getColorModel().isAlphaPremultiplied(), properties);
	}
}