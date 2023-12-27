package com.hamzaouggadi;

import com.hamzaouggadi.engine.Engine;
import com.hamzaouggadi.engine.IAppLogic;
import com.hamzaouggadi.engine.Window;
import com.hamzaouggadi.engine.graph.Mesh;
import com.hamzaouggadi.engine.graph.Render;
import com.hamzaouggadi.engine.scene.Scene;
import com.hamzaouggadi.utils.StartupHelper;

public class Main implements IAppLogic {

    private long window;

    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        Main main = new Main();

        Engine gameEngine = new Engine("Navis", new Window.WindowOptions(), main);

        gameEngine.start();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        float[] positions = new float[]{
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f
        };

        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0,0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f
        };

        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2
        };

        Mesh mesh = new Mesh(positions, colors, indices);
        scene.addMesh("quad", mesh);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {

    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {

    }
}