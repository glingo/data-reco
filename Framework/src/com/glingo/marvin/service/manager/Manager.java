package com.glingo.marvin.service.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public abstract class Manager<T> {
	
	protected HashMap<String, T> cache = new HashMap<String, T>();

	protected abstract T load(String ressource);
	
	protected abstract void save(T managed) throws Exception;
	
	protected abstract T update(T updated) throws IOException;

	protected final boolean isCached(String ressource) {
		return cache.get(ressource) != null;
	}
	
	protected final T fromCache(String ressource) {
		System.out.println("From cache : " + ressource + " : " + isCached(ressource));
		return cache.get(ressource);
	}
	
	public void synchronize() throws IOException {
		for (Iterator<Entry<String, T>> iterator = cache.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, T> entry = (Entry<String, T>) iterator.next();
			update(entry.getValue());
		}
	}
	
	public final T get(String ressource) {
		System.out.println("Getting : " + ressource);
		
		if(isCached(ressource))
			return fromCache(ressource);
		
		return load(ressource);
	}
	
}
