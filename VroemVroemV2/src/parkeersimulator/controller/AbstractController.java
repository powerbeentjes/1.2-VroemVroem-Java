package parkeersimulator.controller;


import parkeersimulator.model.Simulator;
import javax.swing.*;

public abstract class AbstractController extends JPanel {

    protected Simulator simulator;

    public AbstractController(Simulator simulator) {
        this.simulator = simulator;
    }
}
