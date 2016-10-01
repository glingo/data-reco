package net.ar.webonswing.petstore.helpers;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

import org.apache.commons.lang.*;
import org.apache.commons.lang.exception.*;

public class Binder
{
	protected List bindings= new Vector();
	private Object model;

	public Binder()
	{
	}

	public Binder(Object aCurrentObject)
	{
		setModel(aCurrentObject);
	}

	public void add(Object aComponent, String aComponentProperty, String anObjectProperty)
	{

		bindings.add(new Object[] { aComponent, aComponentProperty, anObjectProperty});
	}

	public void add(Object aComponent, String anObjectProperty)
	{
		add(aComponent, "text", anObjectProperty);
	}

	public void addViewsToContainer(Container aContainer)
	{
		for (Iterator i= bindings.iterator(); i.hasNext();)
		{
			Object[] values= (Object[])i.next();
			Component component= (Component)values[0];
			aContainer.add(component).setName((String)values[2]);
		}
	}

	public void modelToView()
	{
		if (model != null)
		{
			for (Iterator i= bindings.iterator(); i.hasNext();)
			{
				Object[] values= (Object[])i.next();
				setValue(values[0], (String)values[1], getValue(model, (String)values[2]));
			}
		}
	}

	public void viewToModel()
	{
		if (model != null)
		{
			for (Iterator i= bindings.iterator(); i.hasNext();)
			{
				Object[] values= (Object[])i.next();
				setValue(model, (String)values[2], getValue(values[0], (String)values[1]));
			}
		}
	}

	public Object getModel()
	{
		return model;
	}

	public void setModel(Object aModel)
	{
		this.model= aModel;
	}

	public static Object getValue(Object anObject, String aProperty)
	{
		try
		{
			Method method= anObject.getClass().getMethod("get" + StringUtils.capitalise(aProperty), null);
			return method.invoke(anObject, null);
		}
		catch (Exception e)
		{
			throw new NestableRuntimeException(e);
		}
	}

	public static Object setValue(Object anObject, String aProperty, Object aValue)
	{
		try
		{
			Method setMethod= anObject.getClass().getMethod("set" + StringUtils.capitalise(aProperty), new Class[] { anObject.getClass().getMethod("get" + StringUtils.capitalise(aProperty), null).getReturnType()});
			return setMethod.invoke(anObject, new Object[] { aValue});
		}
		catch (Exception e)
		{
			throw new NestableRuntimeException(e);
		}
	}
}