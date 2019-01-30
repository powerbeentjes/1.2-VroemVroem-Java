package Parkeersimulator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import java.awt.Color;

public class GUI extends JFrame {
    GUI() {
        setTitle("Controller");
        setSize(400, 400);
        setLayout(null);
        setLocation(800, 0);


        /*JButton button = new JButton("CRASH MIJ -> SPEED: 0x");
        button.setBounds(16, 16, 128, 64); /*Distance from left, Distance from top,length of button, height of button*/
        /*add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulator.tickPause = 1;
            }
        });*/
        JLabel label = new JLabel("Snelheid aanpassen:");
        // create a line border with the specified color and width
        label.setBounds(16, 24, 256, 64); /*Distance from left, Distance from top,length of button, height of button*/
        //Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
        // set the border of this component
        //label.setBorder(border);
        // add textfield to frame
        add(label);



        JButton button1 = new JButton("SPEED: 1x");
        button1.setBounds(16, 80, 128, 64); /*Distance from left, Distance from top,length of button, height of button*/
        add(button1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulator.tickPause = 100;
            }
        });

        JButton button2 = new JButton("SPEED: 3x");
        button2.setBounds(16, 144, 128, 64); /*Distance from left, Distance from top,length of button, height of button*/
        add(button2);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulator.tickPause = 33;
            }
        });

        JButton button3 = new JButton("SPEED: 5x");
        button3.setBounds(16, 208, 128, 64); /*Distance from left, Distance from top,length of button, height of button*/
        add(button3);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulator.tickPause = 20;
            }
        });





        setVisible(true);
    }
}
