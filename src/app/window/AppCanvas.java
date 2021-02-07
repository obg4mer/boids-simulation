package app.window;

import app.App;

import javax.swing.*;
import java.awt.*;

public class AppCanvas extends JPanel {

    public AppCanvas() {
        Dimension dim = new Dimension(App.width * App.pixelsPerCell, App.height * App.pixelsPerCell);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);

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
