package com.marvin.routing.strategies;

import org.xml.sax.Attributes;

public interface IRoutingStrategy {

	void register(Class<?> bundle, Attributes attributes);

}
