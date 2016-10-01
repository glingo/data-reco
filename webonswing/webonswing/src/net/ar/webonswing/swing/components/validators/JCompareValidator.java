// WebOnSwing - Web Application Framework
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

package net.ar.webonswing.swing.components.validators;

import java.text.*;

import javax.swing.*;

import net.ar.webonswing.*;
import net.ar.webonswing.helpers.*;
import net.ar.webonswing.swing.components.validators.contributors.*;

public class JCompareValidator extends JValidator
{
	protected Operation operation;
	protected Type type;

	protected Object valueToCompare;
	protected JComponent componentToCompare;

	public JCompareValidator()
	{
		WosFramework.assignContributor(this, CompareValidatorUIContributor.class);
	}

	public JCompareValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation, Object aValueToCompare, JCompareValidator.Operation anOperation, JCompareValidator.Type aType)
	{
		this(aComponentToValidate, aMessage, aGroupMessage, hasRemoteValidation, anOperation, aType);
		valueToCompare= aValueToCompare;
	}

	public JCompareValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation, JComponent aComponentToCompare, JCompareValidator.Operation anOperation, JCompareValidator.Type aType)
	{
		this(aComponentToValidate, aMessage, aGroupMessage, hasRemoteValidation, anOperation, aType);
		componentToCompare= aComponentToCompare;
	}

	public JCompareValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation, JComponent aComponentToCompare)
	{
		this(aComponentToValidate, aMessage, aGroupMessage, hasRemoteValidation, JCompareValidator.Operation.equal, JCompareValidator.Type.STRING);
		componentToCompare= aComponentToCompare;
	}

	public JCompareValidator(JComponent aComponentToValidate, String aMessage, String aGroupMessage, boolean hasRemoteValidation, JCompareValidator.Operation anOperation, JCompareValidator.Type aType)
	{
		super(aComponentToValidate, aMessage, aGroupMessage, hasRemoteValidation);

		operation= anOperation;
		type= aType;

		WosFramework.assignContributor(this, CompareValidatorUIContributor.class);
	}

	protected boolean performValidation()
	{
		try
		{
			Comparable v1= type.getComparable(getComponentValue(componentToValidate));

			if (componentToCompare != null || valueToCompare != null)
			{
				Comparable v2= type.getComparable(componentToCompare != null ? getComponentValue(componentToCompare) : valueToCompare);
				return operation.compare(v1, v2);
			}
			else
				return true;

		}
		catch (RuntimeException e)
		{
			return false;
		}
	}

	public static class Operation
	{
		protected int[] matchValues;
		protected String name;

		public static Operation equal= new Operation(new int[] { 0, 0, 0}, "equal");
		public static Operation notEqual= new Operation(new int[] { -1, 1, 1}, "not equal");
		public static Operation greaterThan= new Operation(new int[] { 1, 1, 1}, "greater than");
		public static Operation greaterThanEqual= new Operation(new int[] { 1, 0, 0}, "greater than equal");
		public static Operation lessThan= new Operation(new int[] { -1, -1, -1}, "less than");
		public static Operation lessThanEqual= new Operation(new int[] { -1, 0, 0}, "less than equal");
		public static Operation dataTypeCheck= new Operation(new int[] { -1, 0, 1}, "data type check");

		public Operation(int[] match, String aName)
		{
			matchValues= match;
			name= aName;
		}

		public boolean compare(Comparable aValue, Comparable aCompareValue)
		{
			int comparison= aValue.compareTo(aCompareValue);

			if (comparison != 0)
				comparison= comparison / Math.abs(comparison);

			return comparison == matchValues[0] || comparison == matchValues[1] || comparison == matchValues[2];
		}

		public int[] getMatchValues()
		{
			return matchValues;
		}

		public void setMatchValues(int[] aIs)
		{
			matchValues= aIs;
		}
		
		public String toString()
		{
			return name;
		}
	}

	public static abstract class Type
	{
		public static Type STRING= new Type()
		{
			public Comparable createComparableFromString(String aValue)
			{
				return aValue;
			}

			public String toString()
			{
				return "string";
			}
		};

		public static Type INTEGER= new Type()
		{
			public Comparable createComparableFromString(String aValue)
			{
				return new Integer(aValue);
			}

			public String toString()
			{
				return "integer";
			}
		};

		public static Type DOUBLE= new Type()
		{
			public Comparable createComparableFromString(String aValue)
			{
				return new Double(aValue);
			}

			public String toString()
			{
				return "double";
			}
		};

		public static Type DATE= DATE("dd/MM/yyyy HH:mm:ss");

		public static Type DATE(final String aPattern)
		{
			return new Type()
			{
				protected SimpleDateFormat format= new SimpleDateFormat(aPattern);

				public Comparable createComparableFromString(String aValue)
				{
					try
					{
						return format.parse(aValue);
					}
					catch (ParseException e)
					{
						throw new WebOnSwingException(e);
					}
				}

				protected String createStringFromComparable(Object aValue)
				{
					return format.format(aValue);
				}

				public String getPattern()
				{
					return format.toPattern();
				}

				public String toString()
				{
					return "date:" + format.toPattern();
				}
			};
		}

		protected abstract Comparable createComparableFromString(String aValue);

		protected String createStringFromComparable(Object aValue)
		{
			return aValue.toString();
		}

		public Comparable getComparable(Object aValue)
		{
			if (aValue instanceof String)
				return createComparableFromString((String)aValue);
			else if (aValue instanceof Comparable)
				return (Comparable)aValue;
			else
				throw new WebOnSwingException("The value to compare does not implement Comparable interface");
		}

		public String getString(Object aValue)
		{
			if (aValue instanceof String)
				return (String)aValue;
			else if (aValue instanceof Comparable)
				return createStringFromComparable(aValue);
			else
				throw new WebOnSwingException("The value to compare does not implement Comparable interface");
		}
	}

	public JComponent getComponentToCompare()
	{
		return componentToCompare;
	}

	public Operation getOperation()
	{
		return operation;
	}

	public void setComponentToCompare(JComponent aComponent)
	{
		componentToCompare= aComponent;
	}

	public void setOperation(Operation aOperation)
	{
		operation= aOperation;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type aType)
	{
		type= aType;
	}

	public Object getValueToCompare()
	{
		return valueToCompare;
	}

	public void setValueToCompare(Object aObject)
	{
		valueToCompare= aObject;
	}
}