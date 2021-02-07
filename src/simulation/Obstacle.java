package simulation;

import app.App;
import util.Vector2;

import java.awt.*;

public class Obstacle {

    public final Vector2 position;

    public Obstacle(Vector2 position) {
        this.position = position;
    }

    //region Class Methods

    public void paint(Graphics2D g2d) {
        g2d.setColor(App.colorObstacles);
        g2d.fillOval((int)(position.x * App.pixelsPerCell), (int)(position.y * App.pixelsPerCell),
                (int)(App.obstacleSize * App.pixelsPerCell), (int)(App.obstacleSize * App.pixelsPerCell));
    }

    //endregion

    //region Default Java Methods

    @Override
    public Obstacle clone() {
        return new Obstacle(position);
    }

    //endregion

}
