package com.glingo.marvin.router.strategies;

import org.xml.sax.Attributes;

import com.glingo.marvin.router.Router;
import com.glingo.marvin.router.RoutingException;

public interface IRoutingStrategy {

	void register(Router routeur, String prefix, Attributes attributes) throws RoutingException;

}
