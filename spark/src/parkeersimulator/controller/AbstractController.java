package parkeersimulator.controller;


import parkeersimulator.model.Simulator;
import javax.swing.*;

/**
 * Abstracte klasse voor de Controllers
 *
 * @author Tim Pater, Jim Parengkuam, Wiebren van der Vaart, Kyran Oostra, Lars Volkertsma
 * @since 25-01-2017
 * @version 1.0
 *
 */
public abstract class AbstractController extends JPanel {

    protected Simulator simulator;

    /**
     * Constructor voor de Controller
     * @param simulator simulator
     */
    public AbstractController(Simulator simulator) {
        this.simulator = simulator;
    }
}
