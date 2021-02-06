package simulation;

import app.App;
import util.Vector2;

import java.awt.*;
import java.util.List;

public class Boid {

    private final Vector2 position;
    private Vector2 direction;

    private Vector2 separationDir;

    public Boid(Vector2 position, float angle) {
        this.position = position;
        this.direction = Vector2.fromAngle(angle);

        separationDir = null;
    }

    //region Simulation Methods

    public void update(List<Boid> boids) {
        //rotate(Math.PI/2/App.framerate);

        separation(boids);

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
        paintArrow(g2d, direction, Color.WHITE);

        if (separationDir != null) paintArrow(g2d, separationDir.clone().normalize(), Color.RED);
    }

    //endregion

    //region Boid Rules

    private void separation(List<Boid> boids) {

        Vector2 separationDir = new Vector2();

        for (Boid b : boids) {
            if (b == this) continue;

            Vector2 newDir = position.clone().subtract(b.position);

            if (newDir.magnitude() < App.separationDangerDistance) {
                newDir = newDir.clone().normalize().subtract(newDir);
                separationDir.add(newDir);
            }
        }

        if (separationDir.sqrMagnitude() > 0) this.separationDir = separationDir;
        else this.separationDir = null;
    }

    //endregion

    //region Class Methods

    private void rotateManually(double angleDelta) {
        float angle = direction.angle() + (float)angleDelta;
        direction = Vector2.fromAngle(angle);
    }

    private void rotate() {
        float angle = 0;

        if (separationDir != null) {
            angle = separationDir.angle() - direction.angle();
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
        Vector2 p2 = p1.clone().add(direction.clone().multiply(0.2f));

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
