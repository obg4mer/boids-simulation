package app.window;

import app.App;
import simulation.Boid;
import simulation.Simulation;
import util.Vector2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class AppMouseListener implements MouseListener {

    @Override
    public void mousePressed(MouseEvent e) {
        Vector2 position = new Vector2(((float)e.getX())/App.pixelsPerCell, ((float)e.getY())/App.pixelsPerCell);
        float angle = (float)(Math.random() * 2 * Math.PI);

        App.simulation.addBoid(new Boid(position, angle));
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
