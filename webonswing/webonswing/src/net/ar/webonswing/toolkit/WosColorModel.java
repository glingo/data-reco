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

import java.awt.image.*;

public class WosColorModel extends IndexColorModel
{
	private static int numberOfColors= 217;
	private static byte[] redArray= new byte[numberOfColors];
	private static byte[] greenArray= new byte[numberOfColors];
	private static byte[] blueArray= new byte[numberOfColors];

	private static int transparentColor= numberOfColors - 1;

	static {
		final int[] theLevels= { 0x00, 0x33, 0x66, 0x99, 0xCC, 0xFF };

		int k= 0;
		for (int r= 0; r < 6; ++r)
			for (int g= 0; g < 6; ++g)
				for (int b= 0; b < 6; ++b)
				{
					redArray[k]= (byte) theLevels[r];
					greenArray[k]= (byte) theLevels[g];
					blueArray[k]= (byte) theLevels[b];
					++k;
				}
	}

	public WosColorModel()
	{
		super(8, numberOfColors, redArray, greenArray, blueArray, transparentColor);
	}
}
