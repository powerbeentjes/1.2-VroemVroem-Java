package parkeersimulator.model;


import java.io.*;
import sun.audio.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Financing {

    private float currentWeekIncome;
    private float currentDayIncome;
    private float upcomingIncome;

    public float singleDayPrice = 5.50f;

    public void addToWeekIncome(float amountToAdd) {
        this.currentWeekIncome += amountToAdd;
    }

    public void addToDayIncome(float amountToAdd) {
        this.currentDayIncome += amountToAdd;
        this.upcomingIncome -= amountToAdd;

        addToWeekIncome(amountToAdd);

        //playSound("C:/assets/kaching.wav");


    }
    /*
    private void playSound(final String url) {
        try
        {
            // get the sound file as a resource out of my jar file;
            // the sound file must be in the same directory as this class file.
            // the input stream portion of this recipe comes from a javaworld.com article.
            InputStream inputStream = getClass().getResourceAsStream(url);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        }
        catch (Exception e)
        {
            // a special way i'm handling logging in this application
            System.out.println( e );
        }
    }*/

    public void addToUpcoming(float amountToAdd) {
        this.upcomingIncome += amountToAdd;
    }

    public float getCurrentWeekIncome() { return this.currentWeekIncome; }

    public float getCurrentDayIncome() { return this.currentDayIncome; }

    public float getUpcomingIncome() {
        return this.upcomingIncome;
    }

    public void resetCurrentDay() {
        this.currentDayIncome = 0;
    }

    public void resetCurrentWeek() {
        this.currentWeekIncome = 0;
    }
}
