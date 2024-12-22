package me.harry0198.infoheads.core.di.annotations;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface EditCommandExecutor {}
