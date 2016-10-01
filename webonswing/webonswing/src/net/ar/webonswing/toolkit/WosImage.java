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

public class WosImage extends Image
{
	String theFilename;

	public WosImage ()
	{
	}
	
	public WosImage (ImageProducer aProducer)
	{
	}

	public WosImage (String aFilename)
	{
		theFilename= aFilename;
	}
	
	public int getWidth(ImageObserver observer)
	{
		return 0;
	}

	public int getHeight(ImageObserver observer)
	{
		return 0;
	}

	public ImageProducer getSource()
	{
		return null;
	}

	public Graphics getGraphics()
	{
		return null;
	}

	public Object getProperty(String name, ImageObserver observer)
	{
		return null;
	}

	public void flush()
	{
	}
}
