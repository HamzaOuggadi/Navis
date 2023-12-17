package com.hamzaouggadi;

import com.hamzaouggadi.utils.StartupHelper;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    private long window;

    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        new Main().run();
    }

    public void run() {
        System.out.println("Hello, LWJGL version : " + Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        // Set up an error callback, the default implementation
        // Will print the error message in System.err
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW, or all GLFW functions will not work before doing this
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Window will be hidden upon creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Window will be resizable

        // Create the window
        window = glfwCreateWindow(1920, 1080, "Navis", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create GLFW window");

        // Setting up a key callback. It will be called everytime a key is pressed, repeated or released
        // in this case, the escape key will close the window
        glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });


        // Get the thread stack and push a new frame
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2);
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable VSync
        glfwSwapInterval(1);

        // Make the window visible

        glfwShowWindow(window);


    }

    public void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Setting the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the framebuffer

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.s
            glfwPollEvents();
        }
    }

}