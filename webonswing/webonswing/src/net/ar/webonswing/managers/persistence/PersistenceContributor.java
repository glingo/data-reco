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

package net.ar.webonswing.managers.persistence;

import net.ar.webonswing.managers.contributors.*;

/**
 * Contribuidor de persistencia y despersistencia para un componente
 * 
 * @author Fernando Damian Petrola
 */
public interface PersistenceContributor extends ComponentContributor
{
	/**
	 * Realiza la contribucion de persistencia, o sea que de alguna forma va a colaborar con el PersistenceManager
	 * para lograr que el estado que importa del componente asociado quede persistido en un medio.
	 * 
	 * @param aPersistenceManager
	 */
	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager);

	/**
	 * Y este metodo va a recuperar el valor persistido por "doPersistenceContribution".
	 * 
	 * @param aPersistenceManager
	 */
	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager);
	
	public boolean isPersistedValueEqualToModel(PersistenceContributionContainer aPersistenceManager);
}
