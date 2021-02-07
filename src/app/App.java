package app;

import app.window.AppWindow;
import simulation.Simulation;

import java.awt.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class App {

    public static AppWindow appWindow;
    public static Simulation simulation;

    public static void main(String[] args) {
        simulation = new Simulation();
        appWindow = new AppWindow("Boids Simulation");
        simulation.run();
    }

    //region Config

    //region Framerate

    public static final int framerate = 60;
    public static final int millisPerFrame = Math.round(1000/(float)framerate);

    //endregion

    //region Size

    public static final int height = 7;
    public static final int width = 7;
    public static final int pixelsPerCell = 100;

    public static final float boidSize = 0.1f;
    public static final float obstacleSize = 0.2f;

    //endregion

    //region Simulation

    public static final float boidMovementSpeed = 1.5f/(float)framerate;
    public static final float boidRotationCap = (float)Math.PI/(float)framerate;

    //endregion

    //region Boid Rules Constraints

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static float separationRange = 0.5f;
    private static float separationCap = 1f;
    private static float separationPriority = 1f;

    private static float alignmentRange = 0.5f;
    private static float alignmentCap = 1f;
    private static float alignmentPriority = 3f;

    private static float cohesionRange = 0.5f;
    private static float cohesionCap = 0.5f;
    private static float cohesionPriority = 0.5f;

    public static final float obstacleAvoidanceRange = 1f;
    public static final float obstacleAvoidanceCap = 1f;
    public static final float obstacleAvoidancePriority = 5 * (separationPriority + alignmentPriority + cohesionPriority);

    //region Getters

    public static float getSeparationRange() {
        try {
            lock.readLock().lock();
            return separationRange;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static float getSeparationCap() {
        try {
            lock.readLock().lock();
            return separationCap;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static float getSeparationPriority() {
        try {
            lock.readLock().lock();
            return separationPriority;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static float getAlignmentRange() {
        try {
            lock.readLock().lock();
            return alignmentRange;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static float getAlignmentCap() {
        try {
            lock.readLock().lock();
            return alignmentCap;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static float getAlignmentPriority() {
        try {
            lock.readLock().lock();
            return alignmentPriority;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static float getCohesionRange() {
        try {
            lock.readLock().lock();
            return cohesionRange;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static float getCohesionCap() {
        try {
            lock.readLock().lock();
            return cohesionCap;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static float getCohesionPriority() {
        try {
            lock.readLock().lock();
            return cohesionPriority;
        } finally {
            lock.readLock().unlock();
        }
    }


    //endregion

    //region Setters

    public static void setSeparationRange(float separationRange) {
        try {
            lock.writeLock().lock();
            App.separationRange = separationRange;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void setSeparationCap(float separationCap) {
        try {
            lock.writeLock().lock();
            App.separationCap = separationCap;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void setSeparationPriority(float separationPriority) {
        try {
            lock.writeLock().lock();
            App.separationPriority = separationPriority;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void setAlignmentRange(float alignmentRange) {
        try {
            lock.writeLock().lock();
            App.alignmentRange = alignmentRange;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void setAlignmentCap(float alignmentCap) {
        try {
            lock.writeLock().lock();
            App.alignmentCap = alignmentCap;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void setAlignmentPriority(float alignmentPriority) {
        try {
            lock.writeLock().lock();
            App.alignmentPriority = alignmentPriority;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void setCohesionRange(float cohesionRange) {
        try {
            lock.writeLock().lock();
            App.cohesionRange = cohesionRange;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void setCohesionCap(float cohesionCap) {
        try {
            lock.writeLock().lock();
            App.cohesionCap = cohesionCap;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void setCohesionPriority(float cohesionPriority) {
        try {
            lock.writeLock().lock();
            App.cohesionPriority = cohesionPriority;
        } finally {
            lock.writeLock().unlock();
        }
    }


    //endregion

    //endregion

    //region Colors

    public static final Color colorBackground = new Color(45, 48, 71);
    public static final Color colorBoids = new Color(65, 157, 120);
    public static final Color colorObstacles = new Color(158, 29, 29);

    //endregion

    //endregion

}
