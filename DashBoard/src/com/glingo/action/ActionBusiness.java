package com.glingo.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class ActionBusiness {
	
	private static Map<String,Action> registered = new HashMap<String, Action>();
	
	private static Logger LOG = Logger.getLogger(ActionBusiness.class);
	
	public static void register(Action registing){
		LOG.info("register de "+registing.getClass().getSimpleName());
		registered.put(registing.getClass().getSimpleName(), registing);
		LOG.info("register done");
	}
	
	public static Action call(String called) {
		LOG.info("call de : "+ called);
		if (registered.containsKey(called)) {
			LOG.info(called+" est enregistrée");
		} else {
			LOG.info(called+" n'est pas enregistrée");
		}
		LOG.info(registered.get(called));
		return registered.get(called);
	}
}
