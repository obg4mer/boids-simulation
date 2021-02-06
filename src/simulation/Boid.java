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

        separationDir = null;
        alignmentDir = null;
        cohesionDir = null;
    }

    //region Simulation Methods

    public void update(List<Boid> boids) {
        //rotate(Math.PI/2/App.framerate);

        separation(boids);
        alignment(boids);
        cohesion(boids);

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

    //endregion

    //region Boid Rules

    // Resulting vector's magnitude must be lower that it's cap.

    private Vector2 separationDir;
    private void separation(List<Boid> boids) {

        Vector2 separationDir = new Vector2();

        for (Boid b : boids) {
            if (position.equals(b.position)) continue;

            Vector2 newDir = position.clone().subtract(b.position);

            if (newDir.magnitude() < App.separationRange) {
                newDir.setMagnitude(App.separationRange - newDir.magnitude());
                separationDir.add(newDir);
            }
        }

        float magnitude = separationDir.magnitude();

        if (magnitude > 0)  {
            if (magnitude > App.separationCap) {
                float newMagnitude = Util.remap(magnitude, 0, App.separationRange, 0, App.separationCap);
                separationDir.setMagnitude(newMagnitude);
            }
            this.separationDir = separationDir;
        }
        else this.separationDir = null;
    }

    private Vector2 alignmentDir;
    private void alignment(List<Boid> boids) {

        Vector2 alignmentDir = new Vector2();

        for (Boid b : boids) {
            if (position.equals(b.position)) continue;
            if (position.distance(b.position) >= App.alignmentRange) continue;

            float distance = position.distance(b.position);
            float newMagnitude = Util.remap(distance, 0, App.alignmentRange, App.alignmentCap, 0);
            alignmentDir.add(b.direction.clone().setMagnitude(newMagnitude));
        }

        float magnitude = alignmentDir.magnitude();

        if (magnitude > 0) {
            if (magnitude > App.alignmentCap) {
                float newMagnitude = Util.remap(magnitude, 0, App.alignmentRange, 0, App.alignmentCap);
                alignmentDir.setMagnitude(newMagnitude);
            }
            this.alignmentDir = alignmentDir;
        }
        else this.alignmentDir = null;
    }

    private Vector2 cohesionDir;
    private void cohesion(List<Boid> boids) {

        Vector2 cohesionDir = new Vector2();
        int boidsInRange = 0;

        for (Boid b : boids) {
            if (position.equals(b.position)) continue;
            if (position.distance(b.position) >= App.cohesionRange) continue;

            cohesionDir.add(b.position);
            cohesionDir.subtract(position);
            boidsInRange++;
        }

        float magnitude = cohesionDir.magnitude();

        if (boidsInRange > 0) {
            if (magnitude > App.cohesionCap) {
                float newMagnitude = Util.remap(magnitude, 0, App.cohesionRange, 0, App.cohesionCap);
                cohesionDir.setMagnitude(newMagnitude);
            }
            this.cohesionDir = cohesionDir;
        }
        else this.cohesionDir = null;
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
        if (separationDir != null) newDirection.add(separationDir.clone().multiply(App.separationPriority));
        if (alignmentDir != null) newDirection.add(alignmentDir.clone().multiply(App.alignmentPriority));
        if (cohesionDir != null) newDirection.add(cohesionDir.clone().multiply(App.cohesionPriority));

        if (newDirection.sqrMagnitude() > 0) {
            angle = newDirection.angle() - direction.angle();
            if (angle > Math.PI) angle -= 2 * Math.PI;
            else if (angle < - Math.PI) angle += 2 * Math.PI;

            if (Math.abs(angle) > App.boidRotationCap) angle = Math.signum(angle) * App.boidRotationCap;
        }

        if (angle != 0) direction.rotate(angle);
    }

    private void move() {
        //if (separationDir != null) direction = separationDir;
        position.add(direction.clone().multiply(App.boidMovementSpeed));
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

    //region Default Java Methods

    @Override
    public Boid clone() {
        return new Boid(position.clone(), direction.angle());
    }

    //endregion

}
