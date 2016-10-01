package net.ar.webonswing.petstore.persistence;

import java.util.*;

import net.ar.webonswing.*;
import net.ar.webonswing.petstore.model.*;
import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import net.sf.hibernate.exception.*;
import net.sf.hibernate.expression.*;

import org.springframework.orm.hibernate.*;

public class Dao
{
	public static final Object SESSION_FACTORY_KEY= "session-factory";

	public SessionFactory getSessionFactory()
	{
		try
		{
			SessionFactory sessionFactory= (SessionFactory)WosFramework.getApplicationContext().get(SESSION_FACTORY_KEY);

			if (sessionFactory == null)
			{
				Configuration cfg= new Configuration().configure("/hibernate/config/hibernate.cfg.xml");
				sessionFactory= cfg.buildSessionFactory();
				WosFramework.getApplicationContext().put(SESSION_FACTORY_KEY, sessionFactory);
			}

			return sessionFactory;
		}
		catch (HibernateException e)
		{
			throw new NestableRuntimeException(e);
		}
	}

	public List getCustomers(String aUserName, String aPassword)
	{
		return new HibernateTemplate(getSessionFactory()).find("from customer in class net.ar.webonswing.petstore.model.Customer where customer.username =  ? and customer.password = ?", new Object[] { aUserName, aPassword });
	}

	public List getCustomers(String aUserName)
	{
		return new HibernateTemplate(getSessionFactory()).find("from customer in class net.ar.webonswing.petstore.model.Customer where customer.username =  ?", aUserName);
	}

	public void saveOrUpdate(Object anObject)
	{
		new HibernateTemplate(getSessionFactory()).save(anObject);
	}

	public List getCategories()
	{
		return new HibernateTemplate(getSessionFactory()).find("from net.ar.webonswing.petstore.model.Category");
	}

	public List searchProducts(final String aKeyword)
	{
		return new HibernateTemplate(getSessionFactory()).executeFind(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException
			{
				String searchString= "%" + aKeyword + "%";
				return session.createCriteria(Product.class).add(Expression.disjunction().add(Expression.like("productId", searchString)).add(Expression.like("name", searchString)).add(Expression.like("description", searchString))).list();
			}
		});
	}
}