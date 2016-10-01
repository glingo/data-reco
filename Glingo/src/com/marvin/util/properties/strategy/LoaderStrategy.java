package com.marvin.util.properties.strategy;

import com.marvin.util.properties.PropertieLoader;

public interface LoaderStrategy {
	
	void load(String name, ClassLoader loader, PropertieLoader builder);
	
}
