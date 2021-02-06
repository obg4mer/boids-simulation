package app;

import app.window.AppWindow;
import simulation.Boid;
import simulation.Simulation;
import util.Vector2;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class App {

    public static AppWindow appWindow;
    public static Simulation simulation;

    public static void main(String[] args) {

        simulation = new Simulation();
        appWindow = new AppWindow("Boids Simulation");

        simulation.addBoid(new Boid(new Vector2(1.4f, 0.5f), (float)(3*Math.PI/2)));
        simulation.addBoid(new Boid(new Vector2(1.6f, 2.5f), (float)(Math.PI/2)));

        simulation.run();
    }

    //region Config

    //region Framerate

    public static final int framerate = 30;
    public static final int millisPerFrame = Math.round(1000/(float)framerate);

    //endregion

    //region Size

    public static final int height = 3;
    public static final int width = 3;
    public static final int pixelsPerCell = 100;

    public static final float boidSize = 0.1f;

    //endregion

    //region Simulation

    public static final float boidMovementSpeed = 1/(float)framerate;
    public static final float boidRotationCap = (1.5f*(float)Math.PI)/(float)framerate;

    //endregion

    //region Boid Rules Constraints

    public static final float separationRange = 0.5f;
    public static final float separationCap = 0.5f;
    public static final float separationPriority = 0.5f;

    public static final float alignmentRange = 1.5f;
    public static final float alignmentCap = 1f;
    public static final float alignmentPriority = 3f;

    public static final float cohesionRange = 1.5f;
    public static final float cohesionCap = 0.5f;
    public static final float cohesionPriority = 0.5f;

    //endregion

    //region Colors

    public static final Color colorBackground = new Color(45, 48, 71);
    public static final Color colorBoids = new Color(65, 157, 120);

    //endregion

    //endregion

}
