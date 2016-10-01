package com.marvin.component.util.properties.strategy;

import com.marvin.component.util.properties.PropertieLoader;

public interface LoaderStrategy {

    void load(String name, ClassLoader loader, PropertieLoader builder);

}
