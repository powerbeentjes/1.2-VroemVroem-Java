
package parkeersimulator.model;


import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Financing implements LineListener {

    private float currentWeekIncome;
    private float currentDayIncome;
    private float upcomingIncome;
    boolean playCompleted;

    public float singleDayPrice = 5.50f;

    public void addToWeekIncome(float amountToAdd) {
        this.currentWeekIncome += amountToAdd;
    }

    public void addToDayIncome(float amountToAdd) {
        this.currentDayIncome += amountToAdd;
        this.upcomingIncome -= amountToAdd;

        addToWeekIncome(amountToAdd);

    }
    void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.start();

            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }

    }

    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }

    }

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
