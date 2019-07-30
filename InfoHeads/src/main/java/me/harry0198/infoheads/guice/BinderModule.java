package me.harry0198.infoheads.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.harry0198.infoheads.InfoHeads;

public final class BinderModule extends AbstractModule {
    private final InfoHeads infoHeads;

    public BinderModule(InfoHeads infoHeads) {
        this.infoHeads = infoHeads;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        bind(InfoHeads.class).toInstance(infoHeads);
    }
}