package com.glingo.hts.util.factory;

import java.security.InvalidParameterException;

public interface IFactory {
	
	public <T> T creer(Object param) throws InvalidParameterException;
	
}
