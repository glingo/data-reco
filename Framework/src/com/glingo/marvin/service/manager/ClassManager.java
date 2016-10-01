package com.glingo.marvin.service.manager;

import java.io.IOException;

public class ClassManager extends Manager<Class<?>> {

	private Class<?> container;
	
	public ClassManager() {
		super();
		this.container = this.getClass();
	}

	public ClassManager(Class<?> container) {
		super();
		this.container = container;
	}

	@Override
	protected Class<?> load(String ressource) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class<?> clazz = null;
		try {
			clazz = loader.loadClass(ressource);
		} catch (ClassNotFoundException e) {
			try {
				clazz = Class.forName(ressource);
			} catch (ClassNotFoundException ex) {
				try {
					loader = getContainer().getClassLoader();
					clazz = loader.loadClass(ressource);
				} catch (Exception other) { } // on ne peut plus rien faire.
			}
		}
		
		if(clazz != null) {
			System.out.println(clazz.getCanonicalName());
		} else {
			System.out.println("clazz non trouvée");
		}
		
		return clazz;
	}

	public Class<?> getContainer() {
		return container;
	}

	public void setContainer(Class<?> container) {
		this.container = container;
	}

	public static void main(String[] args) {
		ClassManager loader = new ClassManager();
		loader.load("Manager");
		loader.load("com/glingo/manager/Manager.java");
		loader.load("com.glingo.manager.Manager");
		try {
			loader.synchronize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void save(Class<?> managed) {
		
	}

	@Override
	protected Class<?> update(Class<?> updated) {
		return null;
	}

}
