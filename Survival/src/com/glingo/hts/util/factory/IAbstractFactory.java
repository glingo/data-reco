package com.glingo.hts.util.factory;

import java.security.InvalidParameterException;

public interface IAbstractFactory {

	public IFactory getFactory(Object[] params) throws InvalidParameterException;
	
}
