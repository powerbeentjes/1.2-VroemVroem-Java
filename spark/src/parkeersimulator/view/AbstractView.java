package parkeersimulator.view;

import parkeersimulator.model.Simulator;

import javax.swing.*;

public abstract class AbstractView extends JPanel {

    protected Simulator simulator;

    public AbstractView(Simulator simulator)
    {
        this.simulator = simulator;
        simulator.addView(this);
    }

    public Simulator getModel() { return simulator; }
    public void updateView() { repaint(); }
}
