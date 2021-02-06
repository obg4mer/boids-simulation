package app.window;

import app.App;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {

    private final AppMainPanel panel;

    public AppWindow(String title) {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        panel = new AppMainPanel();
        setContentPane(panel.getPanel());

        setVisible(true);
        pack();
    }

    //region Class Methods

    public void paintCanvas() {
        panel.repaint();
    }

    //endregion

}
