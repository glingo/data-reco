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

package net.ar.webonswing.helpers;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.bcel.classfile.*;

public class WosClassLoader extends URLClassLoader
{
	protected static WosClassLoader classLoader;

	public static WosClassLoader getInstance()
	{
		if (classLoader == null)
			classLoader= new WosClassLoader();

		if (Thread.currentThread().getContextClassLoader() != classLoader)
			Thread.currentThread().setContextClassLoader(classLoader);

		return classLoader;
	}

	protected boolean isSearching;
	protected ClassLoader parentClassLoader;
	protected Hashtable transformedClasses;
	private String newPackage;

	protected WosClassLoader()
	{
		super(new URL[] { }, Thread.currentThread().getContextClassLoader());
		parentClassLoader= Thread.currentThread().getContextClassLoader();
		transformedClasses= new Hashtable();
	}

	public Class findClass(String name) throws ClassNotFoundException
	{
		URL resource= parentClassLoader.getResource(name.replace('.', '/').concat(".class"));
		if (resource == null)
			throw new ClassNotFoundException(name);

		try
		{
			return transform(name, resource);
		}
		catch (Exception e)
		{
			throw new ClassNotFoundException(name, e);
		}
	}

	public Class loadClass(String name) throws ClassNotFoundException
	{
		return loadClass(name, true);
	}

	private boolean skipClass(String name)
	{
		return (name.endsWith("SwingTransformer") || name.endsWith("WosClassLoader") || name.startsWith(WosSwingTransformer.MY_PACKAGE_PATH) || name.startsWith("sun.") || name.startsWith("java") || name.startsWith("org.apache"));
	}

	private Class transform(String name, URL resource) throws IOException, ClassFormatError
	{
		InputStream openStream= resource.openStream();

		ClassParser parser= new ClassParser(openStream, name);
		JavaClass javaClass= parser.parse();

		ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
		new WosSwingTransformer(newPackage).transform(javaClass).dump(byteArrayOutputStream);
		byte[] byteArray= byteArrayOutputStream.toByteArray();

		return defineClass(name, byteArray, 0, byteArray.length);
	}

	protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		Object definedClass= transformedClasses.get(name);

		if (skipClass(name))
			return parentClassLoader.loadClass(name);
		else if (definedClass != null)
			return (Class)definedClass;
		else
		{
			Class foundClass= findClass(name);
			transformedClasses.put(name, foundClass);
			return foundClass;
		}
	}
	public String getNewPackage()
	{
		return newPackage;
	}
	public ClassLoader setNewPackage(String newPackage)
	{
		this.newPackage= newPackage;
		return this;
	}
}