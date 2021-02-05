package app.window;

import app.App;
import simulation.Boid;
import util.Vector2;

import javax.swing.*;
import java.awt.*;

public class AppCanvas extends JPanel {

    public AppCanvas() {
        setBackground(App.colorBackground);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        App.boids.forEach(b -> b.paint(g2d));
    }
}
