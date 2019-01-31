package parkeersimulator.view;

import parkeersimulator.model.Simulator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailedInformationView extends AbstractView {

    Map<String, String> elements = new HashMap<>();
    ArrayList<JPanel> elementPanels = new ArrayList<>();

    JPanel panel;

    public DetailedInformationView (Simulator simulator) {
        super(simulator);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.GRAY);
        createInfoTable(panel);

        add(panel);
    }

    private void createInfoTable (JPanel panel) {
        /*JLabel titleLabel = new JLabel("");
        titleLabel.setBounds(0, 0, 256, 24);
        panel.add(titleLabel);*/
    }

    public void UpdateElement(String name, String content) {
        if (elements.containsKey(name)) {
            elements.replace(name, content);
        } else {
            elements.put(name, content);
        }
    }

    /**
     * This method updates this view panel each tick.
     */
    @Override
    public void updateView() {
        UpdateElement("Weekly income €", Float.toString(simulator.financing.getCurrentWeekIncome()));
        UpdateElement("Income today €", Float.toString(simulator.financing.getCurrentDayIncome()));
        //UpdateElement("Predicted income €", Float.toString(simulator.financing.getUpcomingIncome()));

        int index = 0;
        for (Map.Entry<String, String> entry : elements.entrySet()) {
            if (elementPanels.size() <= index) {
                JPanel newPanel = CreateNewElement(entry.getKey(), entry.getValue(), Color.white);
                panel.add(newPanel);
                elementPanels.add(newPanel);
            } else {
                JLabel labelToSet = (JLabel)elementPanels.get(index).getComponent(1);
                labelToSet.setText(entry.getValue());
            }

            index++;
        }
    }

    JPanel CreateNewElement (String name, String value, Color color) {
        JPanel elementPanel = new JPanel();
        elementPanel.setBackground(color);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setBounds(10, 10, 100, 25);

        JLabel valueLabel = new JLabel(value);
        nameLabel.setBounds(150, 10, 100, 25);

        elementPanel.add(nameLabel);
        elementPanel.add(valueLabel);
        return elementPanel;
    }
}




