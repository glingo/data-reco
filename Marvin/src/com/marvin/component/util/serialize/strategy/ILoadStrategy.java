package com.marvin.component.util.serialize.strategy;

import java.io.IOException;
import java.io.InputStream;

import com.marvin.component.util.serialize.Loadable;

public interface ILoadStrategy {

    void load(InputStream str, Loadable loadable);

    void load(String s, boolean formBundle, Loadable loadable) throws IOException;

    void load(Class<?> c, String s, Loadable loadable) throws IOException;

}
