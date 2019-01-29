package Models;

import java.awt.*;

public class Car {
    private String type;
    private Color color;
    private Location location;
    private int minutesLeft;
    private String paymentStatus;

    //Car model constructor
    public Car() {

    }

    //type get/set
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //location get/set
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    //minutesLeft get/set
    public int getMinutesLeft() {
        return minutesLeft;
    }
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    //paymentStatus get/set
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    //color get/set
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
}
