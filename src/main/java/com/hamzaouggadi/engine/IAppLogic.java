package com.hamzaouggadi.engine;

import com.hamzaouggadi.engine.graph.Render;
import com.hamzaouggadi.engine.scene.Scene;

public interface IAppLogic {

    void cleanup();
    void init(Window window, Scene scene, Render render);
    void input(Window window, Scene scene, long diffTimeMillis);
    void update(Window window, Scene scene, long diffTimeMillis);
}
