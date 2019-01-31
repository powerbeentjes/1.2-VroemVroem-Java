package parkeersimulator.controller;



import parkeersimulator.model.Simulator;
import javax.swing.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller extends AbstractController implements ActionListener {

    private JButton start, stop;
    private JButton plus, minus;
    private JLabel ticksText;

    
    public Controller(Simulator simulators) {
        //Roept de constructor aan van AbstractController
        super(simulators);
        simulators.setPlusMinButtonHandler(new Simulator.PlusMinButton() {
			
			@Override
			public void setButtons(boolean enabled) {
				// TODO Auto-generated method stub
				plus.setEnabled(true);
				minus.setEnabled(true);
			}
		});


        //Maakt een tekst veld om de ticks hoeveelheid te laten zien.
        ticksText = new JLabel(""+((simulator.tickPause > 9000) ? "Paused" : simulator.tickPause + "mp/s") );

        add(ticksText);

        //Maakt een - knop aan voor ticks hoeveelheid
        minus = new JButton("Slow down");
        minus.addActionListener(this);
        add(minus);

        //Maakt een + knop aan voor ticks hoeveelheid
        plus = new JButton("Speed up");
        plus.addActionListener(this);
        add(plus);

        //Maakt een Startbutton aan
        start = new JButton("Simulate");
        //Voeg de actionListener toe
        start.addActionListener(this);
        //Voeg de button toe aan de Panel
        add(start);

        stop = new JButton("Halt");
        stop.addActionListener(this);
        add(stop);
        
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==start){
                simulator.run();
        }

        if (e.getSource()==stop)
        {
            simulator.stop();
        }
        if (e.getSource()==minus)
        {
        	/*if(simulator.tickAmount<=9000)
            simulator.tickAmount+=100;
        	 ticksText.setText(""+simulator.tickAmount);*/

            simulator.stop();
            simulator.run();

            if (simulator.tickPause >= 250) {
                simulator.tickPause = 9999;
                ticksText.setText("Paused");
            } else if (simulator.tickPause >= 50) {
                simulator.tickPause += 50;
                ticksText.setText(""+simulator.tickPause + "mp/s");
            } else if (simulator.tickPause >= 10) {
                simulator.tickPause += 10;
                ticksText.setText(""+simulator.tickPause + "mp/s");
            } else {
                simulator.tickPause += 1;
                ticksText.setText(""+simulator.tickPause + "mp/s");
            }


        }
        if (e.getSource()==plus)
        {
        	/*if(simulator.tickAmount>=200)
            simulator.tickAmount-=100;
        	 ticksText.setText(""+simulator.tickAmount);*/

            simulator.stop();
            simulator.run();

            if (simulator.tickPause <= 1) {
                simulator.tickPause = 1;
            } else if (simulator.tickPause <= 10) {
                simulator.tickPause -= 1;
            } else if (simulator.tickPause <= 50) {
                simulator.tickPause -= 10;
            } else if (simulator.tickPause == 9999) {
                simulator.tickPause = 250;
            } else{
                simulator.tickPause -= 50;
            }

            ticksText.setText(""+simulator.tickPause + "mp/s");

        }
    }
}
