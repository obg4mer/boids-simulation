package app.window;

import app.App;
import simulation.Boid;
import simulation.Obstacle;
import util.Vector2;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AppMouseListener implements MouseListener {

    @Override
    public void mousePressed(MouseEvent e) {
        Vector2 position = new Vector2((e.getX())/(float)App.pixelsPerCell, (e.getY())/(float)App.pixelsPerCell);

        if (SwingUtilities.isLeftMouseButton(e)) {
            float angle = (float)(Math.random() * 2 * Math.PI);
            App.simulation.addBoid(new Boid(position, angle));
        } else if (SwingUtilities.isRightMouseButton(e)) {
            App.simulation.addObstacle(new Obstacle(position));
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
