package simulation;

import app.App;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;

public class Simulation implements Runnable {

    private final List<Boid> boids;
    private final List<Obstacle> obstacles;

    private final Lock boidsLock;
    private final Lock obstaclesLock;
    private final Condition paintingCondition;
    private boolean painting;

    public Simulation() {
        this.boids = new ArrayList<>();
        this.obstacles = new ArrayList<>();

        this.boidsLock = new ReentrantLock();
        this.obstaclesLock = new ReentrantLock();
        this.paintingCondition = boidsLock.newCondition();
        painting = false;
    }

    //region Simulation Methods

    @Override
    public void run() {
        long t1 = System.nanoTime(), t2;
        long timeShift = 0;
        long time;

        try {

            while (true) {
                time = App.millisPerFrame + timeShift;
                if (time < 0) time = 0;
                Thread.sleep(time);

                update();
                callRepaint();

                t2 = System.nanoTime();
                timeShift = App.millisPerFrame - (t2 - t1)/1000000;
                t1 = t2;
            }

        } catch (InterruptedException ignored) { }
    }

    private void update() {
        try{
            boidsLock.lock();
            List<Boid> boidsClone = getBoids();
            List<Obstacle> obstaclesClone = getObstacles();
            boids.forEach(b -> b.update(boidsClone, obstaclesClone));
        } finally {
            boidsLock.unlock();
        }
    }

    private void callRepaint() {
        try {
            boidsLock.lock();
            App.appWindow.paintCanvas();
            painting = true;
            while (painting) paintingCondition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boidsLock.unlock();
        }
    }

    public void paint(Graphics2D g2d) {
        try {
            boidsLock.lock();
            boids.forEach(b -> b.paint(g2d));
            obstacles.forEach(o -> o.paint(g2d));
            //boids.forEach(b -> b.paintGizmos(g2d));

            painting = false;
            paintingCondition.signalAll();
        } finally {
            boidsLock.unlock();
        }
    }

    //endregion

    //region Class Methods

    public void addBoid(Boid boid) {
        try {
            boidsLock.lock();
            boids.add(boid);
        } finally {
            boidsLock.unlock();
        }
    }

    public List<Boid> getBoids() {
        try {
            boidsLock.lock();
            return boids.stream().map(Boid::clone).collect(Collectors.toList());
        } finally {
            boidsLock.unlock();
        }
    }

    public void addObstacle(Obstacle obstacle) {
        try{
            obstaclesLock.lock();
            obstacles.add(obstacle);
        } finally {
            obstaclesLock.unlock();
        }
    }

    public List<Obstacle> getObstacles() {
        try {
            obstaclesLock.lock();
            return obstacles.stream().map(Obstacle::clone).collect(Collectors.toList());
        } finally {
            obstaclesLock.unlock();
        }
    }

    //endregion

}
