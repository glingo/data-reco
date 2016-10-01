//WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
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

package net.ar.webonswing.managers.skins;

import java.util.*;

public class SimpleSkinManager
{
	protected Vector skins;
	protected Skin currentSkin;

	public SimpleSkinManager()
	{
		skins= new Vector();
	}

	public void addSkin(Skin aSkin)
	{
		skins.add(aSkin);
		currentSkin= aSkin;
	}

	public Skin getCurrentSkin()
	{
		return currentSkin;
	}

	public void setCurrentSkin(Skin aCurrentSkin)
	{
		currentSkin= aCurrentSkin;
	}

	public boolean setCurrentSkin(String aSkinName)
	{
		int skinIndex= skins.indexOf(new Skin(aSkinName, "", "", ""));
		boolean found= skinIndex != -1;
		
		if (found)
			currentSkin= (Skin) skins.get(skinIndex);

		return found;
	}
	
	public Vector getSkins()
	{
		return skins;
	}
	public void setSkins(Vector aSkins)
	{
		skins= aSkins;
	}
}