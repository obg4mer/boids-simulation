package app.window;

import app.App;
import util.Getter;
import util.Setter;

import javax.swing.*;

public class AppMainPanel {
    private JPanel mainPanel;

    private JPanel canvasPanel;

    private JSlider separationRangeSlider;
    private JLabel separationRangeLabel;

    private JSlider separationCapSlider;
    private JLabel separationCapLabel;

    private JSlider separationPrioritySlider;
    private JLabel separationPriorityLabel;

    private JSlider alignmentRangeSlider;
    private JLabel alignmentRangeLabel;

    private JSlider alignmentCapSlider;
    private JLabel alignmentCapLabel;

    private JSlider alignmentPrioritySlider;
    private JLabel alignmentPriorityLabel;

    private JSlider cohesionRangeSlider;
    private JLabel cohesionRangeLabel;

    private JSlider cohesionCapSlider;
    private JLabel cohesionCapLabel;

    private JSlider cohesionPrioritySlider;
    private JLabel cohesionPriorityLabel;

    public AppMainPanel() {
        setupSlider(separationRangeSlider, separationRangeLabel, App::setSeparationRange, App::getSeparationRange);
        setupSlider(separationCapSlider, separationCapLabel, App::setSeparationCap, App::getSeparationCap);
        setupSlider(separationPrioritySlider, separationPriorityLabel, App::setSeparationPriority, App::getSeparationPriority);

        setupSlider(alignmentRangeSlider, alignmentRangeLabel, App::setAlignmentRange, App::getAlignmentRange);
        setupSlider(alignmentCapSlider, alignmentCapLabel, App::setAlignmentCap, App::getAlignmentCap);
        setupSlider(alignmentPrioritySlider, alignmentPriorityLabel, App::setAlignmentPriority, App::getAlignmentPriority);

        setupSlider(cohesionRangeSlider, cohesionRangeLabel, App::setCohesionRange, App::getCohesionRange);
        setupSlider(cohesionCapSlider, cohesionCapLabel, App::setCohesionCap, App::getCohesionCap);
        setupSlider(cohesionPrioritySlider, cohesionPriorityLabel, App::setCohesionPriority, App::getCohesionPriority);
    }

    //region Setup Methods

    private void setupSlider(JSlider slider, JLabel label, Setter<Float> setter, Getter<Float> getter) {
        slider.addChangeListener(e -> {
            float value = slider.getValue() / 10f;
            label.setText(String.format("%.1f", value));
            setter.set(value);
        });
        slider.setValue(Math.round(getter.get() * 10));
    }

    //endregion

    //region Class Methods

    public JPanel getPanel() {
        return mainPanel;
    }

    public void repaint() {
        canvasPanel.repaint();
    }

    //endregion

}
