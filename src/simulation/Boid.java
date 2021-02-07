package simulation;

import app.App;
import util.Util;
import util.Vector2;

import java.awt.*;
import java.util.List;

public class Boid {

    private final Vector2 position;
    private Vector2 direction;

    public Boid(Vector2 position, float angle) {
        this.position = position;
        this.direction = Vector2.fromAngle(angle);

        separationDir = new Vector2();
        alignmentDir = new Vector2();
        cohesionDir = new Vector2();
        obstacleAvoidanceDir = new Vector2();
    }

    //region Simulation Methods

    public void update(List<Boid> boids, List<Obstacle> obstacles) {

        separation(boids);
        alignment(boids);
        cohesion(boids);
        obstacleAvoidance(obstacles);

        rotate();
        move();
    }

    public void paint(Graphics2D g2d) {
        float angle = direction.angle();

        Vector2 v1 = new Vector2(+ App.boidSize,0           ).rotate(angle).add(position).multiply(App.pixelsPerCell);
        Vector2 v2 = new Vector2(- App.boidSize, - App.boidSize).rotate(angle).add(position).multiply(App.pixelsPerCell);
        Vector2 v3 = new Vector2(- App.boidSize, + App.boidSize).rotate(angle).add(position).multiply(App.pixelsPerCell);

        int[] x = new int[] { Math.round(v1.x), Math.round(v2.x), Math.round(v3.x) };
        int[] y = new int[] { Math.round(v1.y), Math.round(v2.y), Math.round(v3.y) };

        g2d.setColor(App.colorBoids);
        g2d.fillPolygon(x, y, 3);
        g2d.setColor(Color.BLACK);
        g2d.fillPolygon(x, y, 3);
    }

    public void paintGizmos(Graphics2D g2d) {
        paintArrow(g2d, direction.clone().setMagnitude(0.2f), Color.WHITE);

        if (separationDir != null) paintArrow(g2d, separationDir.clone().setMagnitude(0.2f), Color.RED);
        if (alignmentDir != null) paintArrow(g2d, alignmentDir.clone().setMagnitude(0.2f), Color.BLUE);
        if (cohesionDir != null) paintArrow(g2d, cohesionDir, Color.GREEN);
    }

    private void paintArrow(Graphics2D g2d, Vector2 direction, Color color) {
        Vector2 p1 = new Vector2(App.boidSize,0).rotate(direction.angle()).add(position);
        Vector2 p2 = p1.clone().add(direction);

        p1.multiply(App.pixelsPerCell);
        p2.multiply(App.pixelsPerCell);

        g2d.setColor(color);
        g2d.drawLine(Math.round(p1.x), Math.round(p1.y), Math.round(p2.x), Math.round(p2.y));
    }

    //endregion

    //region Boid Rules

    private final Vector2 separationDir;
    private void separation(List<Boid> boids) {

        separationDir.multiply(0);

        for (Boid b : boids) {
            Vector2 dir = position.clone().subtract(b.position);
            float distance = dir.magnitude();
            if (distance > 0 && distance < App.getSeparationRange()) {
                dir.setMagnitude(App.getSeparationRange() - distance);
                separationDir.add(dir);
            }
        }

        float magnitude = separationDir.magnitude();
        if (magnitude > App.getSeparationCap())  {
            separationDir.setMagnitude(App.getSeparationCap());
        }
    }

    private final Vector2 alignmentDir;
    private void alignment(List<Boid> boids) {
        alignmentDir.multiply(0);

        for (Boid b : boids) {
            float distance = position.distance(b.position);
            if (distance > 0 && distance < App.getAlignmentRange()) {
                float newMagnitude = Util.remap(distance, 0, App.getAlignmentRange(), App.getAlignmentCap(), 0);
                alignmentDir.add(b.direction.clone().setMagnitude(newMagnitude));
            }
        }

        float magnitude = alignmentDir.magnitude();
        if (magnitude > App.getAlignmentCap()) {
            alignmentDir.setMagnitude(App.getAlignmentCap());
        }
    }

    private final Vector2 cohesionDir;
    private void cohesion(List<Boid> boids) {
        cohesionDir.multiply(0);
        int boidsInRange = 0;

        for (Boid b : boids) {
            float distance = position.distance(b.position);
            if (distance > 0 && distance < App.getCohesionRange()) {
                cohesionDir.add(b.position);
                boidsInRange++;
            }
        }

        if (boidsInRange > 0) {
            cohesionDir.subtract(position.clone().multiply(boidsInRange));
        }

        float magnitude = cohesionDir.magnitude();
        if (magnitude > App.getCohesionCap()) {
            cohesionDir.setMagnitude(App.getCohesionCap());
        }
    }

    private final Vector2 obstacleAvoidanceDir;
    private void obstacleAvoidance(List<Obstacle> obstacles) {
        obstacleAvoidanceDir.multiply(0);

        for (Obstacle o : obstacles) {
            Vector2 dir = position.clone().subtract(o.position);
            float distance = dir.magnitude();
            if (distance < App.obstacleAvoidanceRange){
                obstacleAvoidanceDir.add(dir);
            }
        }

        float magnitude = obstacleAvoidanceDir.magnitude();
        if (magnitude > App.obstacleAvoidanceCap) {
            obstacleAvoidanceDir.setMagnitude(App.obstacleAvoidanceCap);
        }
    }

    //endregion

    //region Class Methods

    private void rotateManually(double angleDelta) {
        float angle = direction.angle() + (float)angleDelta;
        direction = Vector2.fromAngle(angle);
    }

    private void rotate() {
        float angle = 0;

        Vector2 newDirection = new Vector2();
        newDirection.add(separationDir.clone().multiply(App.getSeparationPriority()));
        newDirection.add(alignmentDir.clone().multiply(App.getAlignmentPriority()));
        newDirection.add(cohesionDir.clone().multiply(App.getCohesionPriority()));
        newDirection.add(obstacleAvoidanceDir.clone().multiply(App.obstacleAvoidancePriority));

        if (newDirection.sqrMagnitude() > 0) {
            angle = newDirection.angle() - direction.angle();
            if (angle > Math.PI) angle -= 2 * Math.PI;
            else if (angle < - Math.PI) angle += 2 * Math.PI;

            if (Math.abs(angle) > App.boidRotationCap) angle = Math.signum(angle) * App.boidRotationCap;
        }

        if (angle != 0) direction.rotate(angle);
    }

    private void move() {
        position.add(direction.clone().multiply(App.boidMovementSpeed));

        if (position.x > App.width + 0.25f) position.x = -0.25f;
        else if (position.x < -0.25) position.x = App.width + 0.25f;

        if (position.y > App.height + 0.25f) position.y = -0.25f;
        else if (position.y < -0.25f) position.y = App.height + 0.25f;
    }

    //endregion

    //region Default Java Methods

    @Override
    public Boid clone() {
        return new Boid(position.clone(), direction.angle());
    }

    //endregion

}
