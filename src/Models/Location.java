package Models;

public class Location {
    private int floor;
    private int row;
    private int place;
    private String type;

    //Location model constructor
    public Location() {

    }

    //floor get/set
    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }

    //row get/set
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }

    //place get/set
    public int getPlace() {
        return place;
    }
    public void setPlace(int place) {
        this.place = place;
    }

    //type get/set
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
