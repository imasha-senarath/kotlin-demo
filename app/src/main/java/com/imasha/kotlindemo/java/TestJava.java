package com.imasha.kotlindemo.java;

import com.imasha.kotlindemo.dagger.Engine;
import com.imasha.kotlindemo.dagger.Wheel;

import javax.inject.Inject;

public class TestJava {

    private Engine engine;
    private Wheel wheel;
    String x;

    @Inject
    public TestJava(Engine engine, Wheel wheel) {
        this.engine = engine;
        this.wheel = wheel;
    }
}
