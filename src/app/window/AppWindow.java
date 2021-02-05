package app.window;

import app.App;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {

    private AppCanvas appCanvas;

    public AppWindow(String title) {
        super(title);

        Dimension dim = new Dimension(App.width * App.pixelsPerCell, App.height * App.pixelsPerCell);
        getContentPane().setMinimumSize(dim);
        getContentPane().setMaximumSize(dim);
        getContentPane().setPreferredSize(dim);
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        appCanvas = new AppCanvas();
        add(appCanvas);

        setVisible(true);
        pack();
    }

    //region Class Methods

    public void paintCanvas() {
        appCanvas.repaint();
    }

    //endregion

}
