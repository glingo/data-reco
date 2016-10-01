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
import java.awt.geom.*;
import java.awt.image.*;

public class WosGraphicsConfiguration extends GraphicsConfiguration
{
	private static final AffineTransform defaultTransform= new AffineTransform();
	protected DirectColorModel directColorModel= new DirectColorModel(25, 0xff0000, 0xff00, 0xff, 0x1000000);
	protected AffineTransform mormalizingTransform= new AffineTransform((75 / 72.0), 0.0, 0.0, (75 / 72.0), 0.0, 0.0);
	protected Rectangle bounds= new Rectangle(0, 0, 800, 600);
	private GraphicsDevice device;

	public WosGraphicsConfiguration(GraphicsDevice aDevice)
	{
		this.device= aDevice;
	}

	public GraphicsDevice getDevice()
	{
		return device;
	}

	public BufferedImage createCompatibleImage(int width, int height)
	{
		ColorModel model= getColorModel();
		WritableRaster raster= model.createCompatibleWritableRaster(width, height);
		return new WosBufferedImage(model, raster, model.isAlphaPremultiplied(), null);
	}

	public BufferedImage createCompatibleImage(int width, int height, int transparency)
	{
		switch (transparency)
		{
			case Transparency.OPAQUE :
				return createCompatibleImage(width, height);
			case Transparency.BITMASK :
			case Transparency.TRANSLUCENT :
				ColorModel cm= getColorModel(transparency);
				WritableRaster wr= cm.createCompatibleWritableRaster(width, height);
				return new WosBufferedImage(cm, wr, cm.isAlphaPremultiplied(), null);
			default :
				throw new IllegalArgumentException("Unknown transparency type " + transparency);
		}
	}

	public ColorModel getColorModel()
	{
		return new WosColorModel();
	}

	public ColorModel getColorModel(int transparency)
	{
		if (transparency == Transparency.OPAQUE)
			return getColorModel();
		else if (transparency == Transparency.BITMASK)
			return directColorModel;
		else if (transparency == Transparency.TRANSLUCENT)
			return ColorModel.getRGBdefault();
		else
			throw new IllegalArgumentException("Unknown transparency type " + transparency);
	}

	public AffineTransform getDefaultTransform()
	{
		return defaultTransform;
	}

	public AffineTransform getNormalizingTransform()
	{
		return mormalizingTransform;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}

	public VolatileImage createCompatibleVolatileImage(int width, int height)
	{
		return null;
	}
}
