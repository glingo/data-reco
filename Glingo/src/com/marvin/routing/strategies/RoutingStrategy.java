package com.marvin.routing.strategies;

import org.xml.sax.Attributes;

import com.marvin.routing.Routeur;

public abstract class RoutingStrategy implements IRoutingStrategy {

    protected Routeur routeur;

    public RoutingStrategy(Routeur routeur) {
        this.routeur = routeur;
    }

    @Override
    public abstract void register(Class<?> bundle, Attributes attributes);

}
