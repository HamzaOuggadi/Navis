package com.hamzaouggadi;

import com.hamzaouggadi.engine.Engine;
import com.hamzaouggadi.engine.IAppLogic;
import com.hamzaouggadi.engine.Window;
import com.hamzaouggadi.engine.graph.*;
import com.hamzaouggadi.engine.input.MouseInput;
import com.hamzaouggadi.engine.scene.Camera;
import com.hamzaouggadi.engine.scene.Entity;
import com.hamzaouggadi.engine.scene.ModelLoader;
import com.hamzaouggadi.engine.scene.Scene;
import com.hamzaouggadi.utils.StartupHelper;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IAppLogic {

    private Entity cubeEntity;
    private Vector4f displInc = new Vector4f();
    private float rotation;
    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;

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
        Model cubeModel = ModelLoader.loadModel("cube-model",
                "/Users/hamzaouggadi/IdeaProjects/Navis/src/main/resources/models/cube/cube.obj",
                scene.getTextureCache());
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0, 0, -2);

        scene.addEntity(cubeEntity);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();

        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }

        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveUp(move);
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(move);
        }

        MouseInput mouseInput = window.getMouseInput();
        if (mouseInput.isRightButtonPressed()) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation(Math.toRadians(-displVec.x * MOUSE_SENSITIVITY),
                    Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
/*        rotation += 1.5F;
        if (rotation > 360) {
            rotation = 0;
        }
        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));*/
        cubeEntity.updateModelMatrix();
    }
}