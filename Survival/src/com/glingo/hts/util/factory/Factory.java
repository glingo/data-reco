package com.glingo.hts.util.factory;

public abstract class Factory implements IFactory {
	
	protected IFactory instance = null;
	
	public abstract IFactory getInstance();

}
