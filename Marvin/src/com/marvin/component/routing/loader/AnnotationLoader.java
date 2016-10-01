package com.marvin.component.routing.loader;

import com.marvin.component.builder.BuilderInterface;
import com.marvin.component.resource.locator.FileLocator;

public class AnnotationLoader extends RouteLoader {

    public AnnotationLoader(FileLocator locator, BuilderInterface builder) {
        super(locator, builder);
    }
}
