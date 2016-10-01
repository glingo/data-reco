package net.ar.webonswing.petstore.contributors;

import javax.swing.*;

import net.ar.webonswing.managers.persistence.*;
import net.ar.webonswing.petstore.*;
import net.ar.webonswing.petstore.helpers.*;
import net.ar.webonswing.ui.*;
import net.sf.hibernate.exception.*;

import org.springframework.orm.hibernate.*;

public class HibernateObjectPersistenceContributor extends RootPaneUIContributor
{
	protected String propertyName;

	public HibernateObjectPersistenceContributor(String aPropertyName)
	{
		propertyName= aPropertyName;
	}

	public void doPersistenceContribution(PersistenceContributionContainer aPersistenceManager)
	{
		ApplicationFrame frame= (ApplicationFrame)((JRootPane)getJComponent()).getTopLevelAncestor();

		Object property= Binder.getValue(frame, propertyName);
		Integer id= (Integer)Binder.getValue(property, "id");
		String className= property.getClass().getName();

		aPersistenceManager.persistValue(theComponent, new Object[] { id, className });
	}

	public void restorePersistedValue(PersistenceContributionContainer aPersistenceManager)
	{
		ApplicationFrame frame= (ApplicationFrame)((JRootPane)getJComponent()).getTopLevelAncestor();

		try
		{
			Object[] values= (Object[])aPersistenceManager.restoreValue(theComponent);
			Integer id= (Integer)values[0];

			if (id != null)
			{
				Class propertyClass= Class.forName(values[1].toString());
				Object propertyValue= new HibernateTemplate(frame.getDao().getSessionFactory()).load(propertyClass, id);
				Binder.setValue(frame, propertyName, propertyValue);
			}

			frame.init();
		}
		catch (Exception e)
		{
			throw new NestableRuntimeException(e);
		}
	}
}