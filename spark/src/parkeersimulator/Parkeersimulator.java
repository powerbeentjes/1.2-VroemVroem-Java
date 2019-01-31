package parkeersimulator;

import java.io.*;
import javax.sound.sampled.*;

import parkeersimulator.controller.Controller;
import parkeersimulator.model.Simulator;
import parkeersimulator.view.CarParkView;
import parkeersimulator.view.DetailedInformationView;
import parkeersimulator.view.PieView;
import parkeersimulator.view.QueueView;


import javax.swing.*;
import java.awt.*;

public class Parkeersimulator {

    public static void main(String[] args) {

        Simulator simulator = new Simulator();

        JFrame screen = new JFrame("Parkeer simulator - Groep Pieter, Dennis");
        screen.setSize(1024, 640);
        screen.setResizable(false);
        screen.setVisible(true);

        screen.getContentPane().add(new PieView(simulator), BorderLayout.WEST);
        screen.getContentPane().add(new QueueView(simulator), BorderLayout.CENTER);
        screen.getContentPane().add(new DetailedInformationView(simulator), BorderLayout.EAST);
        screen.getContentPane().add(new CarParkView(simulator), BorderLayout.NORTH);
        screen.getContentPane().add(new Controller(simulator), BorderLayout.SOUTH);
        screen.pack();

        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}