package app;

import app.window.AppWindow;
import simulation.Boid;
import util.Vector2;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class App {

    private static AppWindow appWindow;

    public static final List<Boid> boids = new ArrayList<>();

    public static void main(String[] args) {
        boids.add(new Boid(new Vector2(1.5f, 1.5f), 0));

        appWindow = new AppWindow("Boids Simulation");

        run();
    }

    //region App Methods

    public static void run() {

        long t1 = System.nanoTime(), t2;
        long timeShift = 0;

        try {

            while (true) {
                Thread.sleep(millisPerFrame + timeShift);

                update();
                paint();

                t2 = System.nanoTime();
                timeShift = millisPerFrame - (t2 - t1)/1000000;
                t1 = t2;
            }

        } catch (InterruptedException ignored) { }

    }

    private static void update() {
        boids.forEach(Boid::update);
    }

    private static void paint() {
        appWindow.paintCanvas();
    }

    //endregion

    //region Config

    //region Size

    public static final int height = 3;
    public static final int width = 3;
    public static final int pixelsPerCell = 100;

    public static final float boidSize = 0.1f;

    //endregion

    //region Simulation

    public static final int framerate = 30;
    public static final int millisPerFrame = Math.round(1000/(float)framerate);

    public static final float boidSpeed = 1/(float)framerate;

    //endregion

    //region Colors

    public static final Color colorBackground = new Color(45, 48, 71);
    public static final Color colorBoids = new Color(65, 157, 120);

    //endregion

    //endregion

}
