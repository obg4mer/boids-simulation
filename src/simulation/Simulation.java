package simulation;

import app.App;
import app.window.AppWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;

public class Simulation implements Runnable {

    private final List<Boid> boids;

    private final Lock lock;
    private final Condition condition;
    private boolean painting;

    public Simulation() {
        this.boids = new ArrayList<>();

        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
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
            lock.lock();
            List<Boid> boidsClone = getBoids();
            boids.forEach(b -> b.update(boidsClone));
        } finally {
            lock.unlock();
        }
    }

    private void callRepaint() {
        try {
            lock.lock();
            App.appWindow.paintCanvas();
            painting = true;
            while (painting) condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void paint(Graphics2D g2d) {
        try {
            lock.lock();
            boids.forEach(b -> b.paint(g2d));
            //boids.forEach(b -> b.paintGizmos(g2d));

            painting = false;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    //endregion

    //region Class Methods

    public void addBoid(Boid boid) {
        try {
            lock.lock();
            boids.add(boid);
        } finally {
            lock.unlock();
        }
    }

    public List<Boid> getBoids() {
        return boids.stream().map(Boid::clone).collect(Collectors.toList());
    }

    //endregion

}
