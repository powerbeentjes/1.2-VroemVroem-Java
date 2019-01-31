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
        ticksText = new JLabel(""+simulator.tickAmount);
        add(ticksText);
        
        //Maakt een + knop aan voor ticks hoeveelheid
        plus = new JButton("+ Minuten");
        plus.addActionListener(this);
        add(plus);
        //Maakt een - knop aan voor ticks hoeveelheid
        minus = new JButton("- Minuten");
        minus.addActionListener(this);
        add(minus);
        
        //Maakt een Startbutton aan
        start = new JButton("Start");
        //Voeg de actionListener toe
        start.addActionListener(this);
        //Voeg de button toe aan de Panel
        add(start);
        
        stop = new JButton("Stop");
        stop.addActionListener(this);
        add(stop);
        
        
       
        
    }


    /**


     general


     general
     Zoeken

     LAAD MEER BERICHTEN

     Zoeken

     LAAD MEER BERICHTEN

     * actionPerformed
     * Deze methode luisterd naar de actions van de besturingselementen
     * @param e ActionEvent


    general
    Zoeken

    LAAD MEER BERICHTEN

     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==start){
                simulator.run(simulator.tickAmount);
                plus.setEnabled(false);
                minus.setEnabled(false);
        }

        if (e.getSource()==stop)
        {
            simulator.stop();
            plus.setEnabled(true);
            minus.setEnabled(true);
        }
        if (e.getSource()==plus)
        {
        	if(simulator.tickAmount<=900)
            simulator.tickAmount+=100;
        	 ticksText.setText(""+simulator.tickAmount);
        }
        if (e.getSource()==minus)
        {
        	if(simulator.tickAmount>=200)
            simulator.tickAmount-=100;
        	 ticksText.setText(""+simulator.tickAmount);
        }
    }
}
