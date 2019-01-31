package parkeersimulator.view;

import parkeersimulator.model.Car;
import parkeersimulator.model.Location;
import parkeersimulator.model.ParkeerGarage;
import parkeersimulator.model.Simulator;

import javax.swing.*;
import java.awt.*;

public class CarParkView extends AbstractView {

    private Dimension size;
    private Image carParkImage;

    JLabel timeLabel;

    public CarParkView(Simulator simulator)
    {
        super(simulator);
        size = new Dimension(0, 0);

        this.timeLabel = new JLabel("time");
        add(timeLabel);

    }



        /**
         * @Override
         * Tells car park how big it should be
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }

        /**
         * @Override .
         * Copy the internal image to screen.
         */
        public void paintComponent(Graphics g) {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }

            Graphics graphics = carParkImage.getGraphics();
            for(int i = 0; i < ParkeerGarage.locations.length; i++) {
                Car car = getModel().getParkeerGarage().getCarAt(ParkeerGarage.locations[i]);
                Color color = car == null ? Color.white : car.getColor();
                drawPlace(graphics, ParkeerGarage.locations[i], color);
            }

            if (carParkImage == null) {
                return;
            }

            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }


        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }

    @Override
    public void updateView() {
        this.timeLabel.setText(simulator.getTime());
        super.updateView();
    }
}




