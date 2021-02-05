package simulation;

import app.App;
import util.Vector2;

import java.awt.*;

public class Boid {

    private Vector2 position;

    private double angle;

    public Boid(Vector2 position, double angle) {
        this.position = position;
        this.angle = angle;
    }

    //region App Methods

    public void update() {
        rotate(Math.PI/2/App.framerate);
        move();
    }

    public void paint(Graphics2D g2d) {
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

    //endregion

    //region Class Methods

    // For some reason angles are inverted.
    private void rotate(double angleDelta) {
        angle -= angleDelta;
    }

    private void move() {
        position.add(Vector2.fromAngle(angle).multiply(App.boidSpeed));
    }

    //endregion

}
