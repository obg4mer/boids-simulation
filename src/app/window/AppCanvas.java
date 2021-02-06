package app.window;

import app.App;
import simulation.Boid;
import simulation.Simulation;
import util.Vector2;

import javax.swing.*;
import java.awt.*;

public class AppCanvas extends JPanel {

    public AppCanvas() {

        addMouseListener(new AppMouseListener());
        setBackground(App.colorBackground);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        App.simulation.paint(g2d);
    }
}
