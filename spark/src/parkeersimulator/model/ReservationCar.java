/**
 * @Project spark
 * @Class ReservationCar
 * @Auhor FJ Parengkuan
 * Beschrijving hier
 */


package parkeersimulator.model;

import java.awt.*;
import java.util.Random;

public class ReservationCar extends Car {

    private static final Color COLOR=Color.ORANGE;

    public ReservationCar() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }



    public Color getColor(){
        return COLOR;
    }
}
