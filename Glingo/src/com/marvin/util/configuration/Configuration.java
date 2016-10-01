package com.marvin.util.configuration;

import java.io.Serializable;

public interface Configuration<T> extends Serializable {
	
	void configurer(T o);
}
